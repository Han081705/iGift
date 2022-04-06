package com.mistershorr.birthdaytracker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

// backendless and other online Baas (backend as a service)
// apis often require your model class to have a
// default, no parameter constructor
// val blah = Person()
// in kotlin, you give each field a default value
// and then you can us a no parameter constructor
@Parcelize
data class Person(
    var name : String = "Your mom",
    var birthday : Date = Date(1646932056741),
    var budget : Int = 1,
    var desiredGift : String = "String",
    var previousGifts : List<String> = listOf(),
    var previousGiftsToMe : List<String> = listOf(),
    var isPurchased : Boolean = false,
    var ownerId : String? = null,
    var objectId : String? = null
) : Parcelable {
    // TODO: have methods to return the calculated values of age, days until birthday
}
