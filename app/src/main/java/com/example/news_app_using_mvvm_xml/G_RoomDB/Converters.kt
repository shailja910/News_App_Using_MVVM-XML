package com.example.news_app_using_mvvm_xml.G_RoomDB
import androidx.room.TypeConverter
import com.example.news_app_using_mvvm_xml.B_model.Source

class Converters {
    @TypeConverter
    fun toSource(name:String):Source
    {
        return Source(name,name)
    }
    @TypeConverter
    fun fromSource(src: Source):String
    {
        return src.name
    }
}