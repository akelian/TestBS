package by.devnmisko.testbs.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.devnmisko.domain.model.ImageDomainRequestModel
import by.devnmisko.domain.model.Output
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentCameraBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.bitmapToString
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.hasCameraPermission
import by.devnmisko.testbs.utils.hasLocationPermission
import by.devnmisko.testbs.utils.imageProxyToBitmap
import by.devnmisko.testbs.utils.requestPermissionForResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class CameraFragment : BaseFragment<FragmentCameraBinding>() {

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CameraViewModel by viewModels { viewModelFactory }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCameraBinding
        get() = FragmentCameraBinding::inflate

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Pair<Double, Double> = 0.0 to 0.0
    private var preview: Preview? = null

    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null
    private lateinit var cameraExecutor: ExecutorService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity as MainActivity)
    }

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        subscribeUI()
        if (hasCameraPermission(appContext) && hasLocationPermission(appContext)) {
            startCamera()
            requestCurrentLocation()
        } else {
            requestPermission()
        }

        binding.cameraCaptureButton.setOnClickListener { takePhoto() }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun subscribeUI() {
        val dialog = buildProgressDialog()
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.loadingState) { isLoading ->
            if (isLoading) {
                dialog.show()
            } else {
                dialog.hide()
            }
        }

        viewLifecycleOwner.collectLatestWhenStarted(viewModel.postImageResponse) { output ->
            output?.let {
                when (output.status) {
                    Output.Status.SUCCESS -> {
                        Toast.makeText(
                            appContext,
                            getString(R.string.photo_loaded_successfully), Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    Output.Status.ERROR -> {
                        buildErrorDialog(output.message).show()
                    }

                    Output.Status.LOADING -> {}
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        with((activity as MainActivity)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            supportActionBar?.hide()
            lockDrawer(true)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(appContext)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            } catch (exc: Exception) {
                Timber.e("Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(appContext))

    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val onImageCaptureCallback = object :
            ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                //get bitmap from image
                val bitmap = image.imageProxyToBitmap()
                val outPutImage = ImageDomainRequestModel(
                    base64Image = bitmap.bitmapToString(),
                    date = (System.currentTimeMillis() / 1000).toInt().toLong(),
                    lat = location.first,
                    lng = location.second
                )

                viewModel.postImage(outPutImage)
                super.onCaptureSuccess(image)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Timber.e(exception.message)
            }
        }

        imageCapture.takePicture(ContextCompat.getMainExecutor(appContext), onImageCaptureCallback)
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {

        if (hasLocationPermission(appContext)) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location == null) {
                        Toast.makeText(appContext,
                            getString(R.string.cannot_get_location), Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        this.location = location.latitude to location.longitude

                    }
                }
                .addOnFailureListener {
                    Timber.e("Getting location failed")
                }
        }
    }

    private fun requestPermission() {
        val permissionsStr = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (!hasLocationPermission(requireContext()) || !hasCameraPermission(requireContext())) {
            requestPermissionForResult(onSuccess = {
                Toast.makeText(
                    appContext,
                    getString(R.string.permission_granted),
                    Toast.LENGTH_SHORT
                ).show()
                startCamera()
                requestCurrentLocation()
            }, onFail = {
                buildErrorDialog(getString(R.string.grant_permission_warning)).show()
                findNavController().popBackStack()
            }).launch(permissionsStr)
        }
    }

    override fun onDestroyView() {
        cameraExecutor.shutdown()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        (activity as MainActivity).supportActionBar?.show()
        (activity as MainActivity).lockDrawer(false)
        super.onDestroyView()
    }


}