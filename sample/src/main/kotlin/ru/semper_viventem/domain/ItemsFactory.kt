package ru.semper_viventem.domain


object ItemsFactory {

    fun getDefault(): List<ItemData> {
        return (1..20).map {
            ItemData(
                    title = "Item $it",
                    description = "Description of item $it",
                    image = when (0) {
                        it % 2 -> "http://pixelfans.ru/wp-content/uploads/2016/11/Oboi-Google-Pixel-4-682x1024.jpg"
                        it % 3 -> "https://phandroid.s3.amazonaws.com/wp-content/uploads/2015/02/wallpapers_04.jpg"
                        else -> "https://img.gadgethacks.com/img/original/44/37/63498383131752/0/634983831317524437.jpg"
                    }
            )
        }
    }
}