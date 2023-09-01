package by.devnmisko.testbs.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun hasCameraPermission(context: Context) = ContextCompat.checkSelfPermission(
    context,
    Manifest.permission.CAMERA
) == PackageManager.PERMISSION_GRANTED

fun hasLocationPermission(context: Context) = ContextCompat.checkSelfPermission(
    context,
    Manifest.permission.ACCESS_COARSE_LOCATION
) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
    context,
    Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED

fun Fragment.requestPermissionForResult(
    onSuccess: () -> Unit,
    onFail: (() -> Unit)? = null
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { entry ->

        var isAllGranted = true

        entry.values.forEach {
            isAllGranted = isAllGranted && it
        }

        if (isAllGranted) onSuccess() else if (onFail != null) onFail()
    }
}
