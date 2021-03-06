package org.wit.placemark.helpers

import org.wit.placemark.models.HillfortModel

fun constructEmailTemplate(hillfort: HillfortModel): String {
    var stringifyImages = ""

    if (hillfort.images.size > 0) {
        hillfort.images.forEach {
            stringifyImages += "${it.uri}\n"
        }
    }

    val location =
        "https://www.google.com/maps/place//@${hillfort.location.lat},${hillfort.location.lng},10z"

    return "Check out this Hillfort from Hillforty!\n\n" +
            "Name: ${hillfort.name}\nDescription: ${hillfort.description}\n" +
            "My Rating: ${hillfort.rating}/5 Stars\n\n" +
            "I also took some images, you can check them out here:\n $stringifyImages\n\n" +
            "You can find the hillfort here: $location"
}
