package com.pb.kemchoaudio.core.database.audio

import androidx.room.TypeConverter

class FloatListTypeConverter {

    @TypeConverter
    fun fromFloatList(floatList: List<Float>): String {
        return floatList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toFloatList(data: String): List<Float> {
        return data.split(",").map { it.toFloat() }
    }
}