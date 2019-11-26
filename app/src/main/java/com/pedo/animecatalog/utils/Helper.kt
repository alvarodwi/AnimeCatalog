package com.pedo.animecatalog.utils

import com.pedo.animecatalog.network.NetworkGenre
import com.pedo.animecatalog.network.NetworkStudio

fun convertListStringToString(values : List<String>?): String{
    var stringList = ""
    values?.let{
        for(value in it){
            stringList += value + "¶"
        }
    }
    return stringList
}

fun convertStringToListString(value : String?):  List<String>{
    val stringList = ArrayList<String>()
    value?.let{
        stringList.addAll(it.split("¶"))
    }
    return stringList
}

fun convertStudiosToString(studios : List<NetworkStudio>?) : String{
    var stringList = ""
    studios?.let{
        for(studio in it){
            stringList += studio.name + "¶"
        }
    }
    return stringList
}

fun convertStudiosToListString(studios : List<NetworkStudio>?) : List<String>{
    val stringList = ArrayList<String>()
    studios?.let{
        for(studio in it){
            stringList.add(studio.name)
        }
    }
    return stringList
}

fun convertGenresToListString(genres : List<NetworkGenre>?) : List<String>{
    val stringList = ArrayList<String>()
    genres?.let{
        for(genre in it){
            stringList.add(genre.name)
        }
    }
    return stringList
}
fun convertGenresToString(genres : List<NetworkGenre>?) : String{
    var stringList = ""
    genres?.let{
        for(genre in it){
            stringList += genre.name + "¶"
        }
    }
    return stringList
}

fun Double.format(digits : Int) = String.format("%.${digits}f",this)

fun formatAired(start : String,end : String?) : String{
    if(end.isNullOrEmpty()){
        return start
    }else{
        return "$start - $end"
    }
}