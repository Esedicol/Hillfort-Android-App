package org.wit.placemark.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.*
import java.util.*
import kotlin.collections.ArrayList


val JSON_FILE = "esedicol.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PlacemarkJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = arrayListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }


    // ------------- Override Functions for Hill Forts ------------- //
    override fun findAll(): ArrayList<PlacemarkModel> {
        val total: ArrayList<PlacemarkModel> = arrayListOf()

        for (user in users) {
            if (user.placemarks.isNotEmpty()) {
                for (forts in user.placemarks) {
                    total.add(forts)
                }
            }
        }
        return total
    }

    override fun findAllForts(user: UserModel): List<PlacemarkModel> {
        return user.placemarks
    }

    override fun createFort(user: UserModel, placemark: PlacemarkModel) {
        placemark.id = generateRandomId().toInt()
        user.placemarks.add(placemark)
        serialize()
    }

    override fun updateFort(user: UserModel, placemark: PlacemarkModel) {
//        val fort = findOneFort(user, placemark.id)
//
//        if (fort != null) {
//            user.placemarks[fort.id ] = placemark
//        }
//        serialize()

        var fort: PlacemarkModel? = user.placemarks.find { p -> p.id == placemark.id }

        // If fort is found //
        if (fort != null) {
            fort.title = placemark.title
            fort.description = placemark.description
            fort.location.lat = placemark.location.lat.toString().toDouble()
            fort.location.lng = placemark.location.lng.toString().toDouble()
            fort.note = placemark.note
            fort.image_list = placemark.image_list
            fort.check_box = placemark.check_box
            fort.date = placemark.date
            serialize()
        }
    }

    override fun deleteFort(user: UserModel, placemark: PlacemarkModel) {
        val fort: PlacemarkModel? = user.placemarks.find { x -> x.id == placemark.id }

        user.placemarks.remove(fort)
        serialize()
    }

    override fun findOneFort(user: UserModel, id: Int): PlacemarkModel? {
        return user.placemarks.find { p -> p.id == id }
    }

    // ------------- Override Function for Users ------------- //
    override fun findAllUsers(): ArrayList<UserModel> {
        return users
    }

    override fun createUser(user: UserModel) {
        user.id = generateRandomId().toInt()
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        val newUser: UserModel? = users.find { x -> x.id == user.id }

        if (newUser != null) {
            newUser.name = user.name
            newUser.email = user.email
            newUser.password = user.password
            newUser.placemarks = user.placemarks
        }
        serialize()
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }

    override fun findUserByEmail(email: String): UserModel? {
        return users.find { x -> x.email == email }
    }
}