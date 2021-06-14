package com.example.lesson13

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput

@Parcelize
data class Element(
    var title: String,
    var currentCount: Int
) : Parcelable, Externalizable {
    override fun writeExternal(out: ObjectOutput?) {
        out?.writeUTF(title)
        out?.writeInt(currentCount)
    }

    override fun readExternal(`in`: ObjectInput?) {
        title = `in`!!.readUTF()
        currentCount = `in`.readInt()
    }
}
