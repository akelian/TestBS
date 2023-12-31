object AppDependencies {

    object UI {
        private const val material_version = "1.9.0"
        private const val recycler_view_version = "1.3.1"
        private const val activity_version = "1.7.2"
        private const val nav_version = "2.6.0"
        private const val fragment_version = "1.6.1"
        private const val lifecycle_version = "2.2.0"
        private const val constraint_layout_version = "2.1.4"
        private const val maps_version = "18.1.0"
        private const val location_version = "21.0.1"
        private const val camera_version = "1.2.3"
        private const val paging_version = "3.1.1"
        private const val glide_version = "4.16.0"

        const val material = "com.google.android.material:material:$material_version"
        const val recycler_view = "androidx.recyclerview:recyclerview:$recycler_view_version"
        const val activity_ktx = "androidx.activity:activity-ktx:$activity_version"
        const val nav_fragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
        const val nav_ui = "androidx.navigation:navigation-ui-ktx:$nav_version"
        const val fragment = "androidx.fragment:fragment-ktx:$fragment_version"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
        const val constraint_layout = "androidx.constraintlayout:constraintlayout:$constraint_layout_version"
        const val maps = "com.google.android.gms:play-services-maps:$maps_version"
        const val location = "com.google.android.gms:play-services-location:$location_version"
        const val camera_core = "androidx.camera:camera-camera2:$camera_version"
        const val camera_lifecycle = "androidx.camera:camera-lifecycle:$camera_version"
        const val camera_view = "androidx.camera:camera-view:$camera_version"
        const val paging = "androidx.paging:paging-runtime:$paging_version"
        const val glide = "com.github.bumptech.glide:glide:$glide_version"
    }
}