package com.example.chatapplication.ui.add_room

import com.example.chatapplication.R

data class RoomCategory(
    val id: String,
    val name: Int,
    val imageId: Int,
) {
    companion object {
        fun getCategoryById(id: String?): RoomCategory {
            return when (id) {
                "sports" -> {
                    RoomCategory("sports", (R.string.sport), R.drawable.sports)
                }

                "music" -> {
                    RoomCategory("music", (R.string.music), R.drawable.music)
                }

                "movies" -> {
                    RoomCategory("movies", (R.string.movies), R.drawable.movies)
                }

                else -> {
                    RoomCategory("sports", (R.string.sport), R.drawable.sports)
                }
            }


        }

        fun getCategories() = listOf(
            RoomCategory("sports", (R.string.sport), R.drawable.sports),
            RoomCategory("music", (R.string.music), R.drawable.music),
            RoomCategory("sports", (R.string.sport), R.drawable.sports)
        )
    }


}


