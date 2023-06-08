package com.example.chatapplication.database.models

import android.os.Parcelable
import com.example.chatapplication.ui.add_room.RoomCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var categoryId: String? = null,
    var createdBy: String? = null
) : Parcelable {

    fun getImageId(): Int {
        return RoomCategory.getCategoryById(categoryId)
            .imageId
    }


}


