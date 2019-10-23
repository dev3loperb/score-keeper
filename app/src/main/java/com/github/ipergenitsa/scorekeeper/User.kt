package com.github.ipergenitsa.scorekeeper

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class User(val name: String, var score: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        Optional.ofNullable(parcel.readString()).orElse(""),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}