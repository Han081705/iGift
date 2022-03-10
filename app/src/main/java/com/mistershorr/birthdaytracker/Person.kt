package com.mistershorr.birthdaytracker

import java.util.*

// backendless and other online Baas (backend as a service)
// apis often require your model class to have a
// default, no parameter constructor
// val blah = Person()
// in kotlin, you give each field a default value
// and then you can us a no parameter constructor
data class Person(
    var name : String = "Your mom",
    var birthday : Date = Date(1646932056741),
    var budget : Double = .99,
    var desiredGift : String = "String",
    var previousGifts : List<String> = listOf(),
    var previousGiftsToMe : List<String> = listOf(),
    var giftPurchased : Boolean = false
) {
    // TODO: have methods to return the calculated values of age, days until birthday
}
