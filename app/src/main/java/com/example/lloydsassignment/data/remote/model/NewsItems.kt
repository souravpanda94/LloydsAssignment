package com.example.lloydsassignment.data.remote.model

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class NewsItems(

    @field:SerializedName("publishedAt")
	val publishedAt: String? = null,

    @field:SerializedName("author")
	val author: String? = null,

    @field:SerializedName("urlToImage")
	val urlToImage: Any? = null,

    @field:SerializedName("description")
	val description: Any? = null,

    @field:SerializedName("source")
	val source: Source? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("url")
	val url: String? = null,

    @field:SerializedName("content")
	val content: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        TODO("urlToImage"),
        TODO("description"),
        TODO("source"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(publishedAt)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItems> {
        override fun createFromParcel(parcel: Parcel): NewsItems {
            return NewsItems(parcel)
        }

        override fun newArray(size: Int): Array<NewsItems?> {
            return arrayOfNulls(size)
        }
    }
}