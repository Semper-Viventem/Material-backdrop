package ru.semper_viventem.domain


object ItemsFactory {

    fun getDefault(): List<ItemData> {
        return (1..20).map {
            ItemData(
                    title = "Item $it",
                    description = "Description of item $it",
                    image = when (0) {
                        it % 2 -> "https://phandroid.s3.amazonaws.com/wp-content/uploads/2016/08/nexus-2016-android-wallpaper_image.jpg"
                        it % 3 -> "https://phandroid.s3.amazonaws.com/wp-content/uploads/2015/02/wallpapers_04.jpg"
                        else -> "https://lh3.googleusercontent.com/-KhxeQPX1Yu0/Vg_43pFC4EI/AAAAAAAAINM/VJM5x_lPh1A/w1800-h1200/1006-ConvertImage%2B%25281%2529.jpg"
                    }
            )
        }
    }
}