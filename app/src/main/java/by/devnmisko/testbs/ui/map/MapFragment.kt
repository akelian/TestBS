package by.devnmisko.testbs.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.R
import by.devnmisko.testbs.databinding.FragmentMapBinding
import by.devnmisko.testbs.ui.base.BaseFragment
import by.devnmisko.testbs.utils.collectLatestWhenStarted
import by.devnmisko.testbs.utils.getDate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import javax.inject.Inject

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapsSdkInitializedCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MapViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.allImages) { apiList ->
            for (place in apiList){
                val marker = LatLng(place.lat, place.lng)
                googleMap.addMarker(MarkerOptions().position(marker).title(getDate(place.date)))
                if(place.id == apiList.last().id){
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 10F));
                }
            }
        }
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMapBinding
        get() = FragmentMapBinding::inflate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapsInitializer.initialize(requireContext(), MapsInitializer.Renderer.LATEST, this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupUI() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Timber.d("The latest version of the renderer is used.")
            MapsInitializer.Renderer.LEGACY -> Timber.d("The legacy version of the renderer is used.")
        }
    }
}