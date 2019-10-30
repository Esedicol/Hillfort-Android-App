package org.wit.placemark.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.*
import java.util.*

val JSON_FILE = "test.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PlacemarkJSONStore : Store, AnkoLogger {

    val context: Context
    var placemarks = mutableListOf<PlacemarkModel>()
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(placemarks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        placemarks = Gson().fromJson(jsonString, listType)
    }


    //////////////////////////// USER CRUD ////////////////////////////
    override fun findAllUsers(): List<UserModel> {
        return users
    }

    override fun findUser(id: Long): UserModel? {
        val foundUser: UserModel? = users.find { p ->
            p.id == id
        }
        return foundUser
    }

    override fun createUser(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        val foundUser: UserModel? = users.find { p ->
            p.id == user.id
        }

        if (foundUser != null) {
            foundUser.id = user.id
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            foundUser.placemark = user.placemark
        }
        serialize()
    }


    override fun deleteUser(user: UserModel) {
        val foundUser: UserModel? = users.find { p ->
            p.id == user.id
        }

        if (foundUser != null) {
            users.remove(foundUser)
            serialize()
        }
    }


    //////////////////////////// PLACEMARK CRUD ////////////////////////////
    override fun findByEmail(email: String): UserModel? {
        // 1) iterate through list of user
        for (user in users) {
            // 2) Comapare
            if (user.email == email) {
                return user
            }
        }
        return null
    }

    override fun findAll(user: UserModel): List<PlacemarkModel> {
        return user.placemark
    }

    override fun create(user: UserModel, placemark: PlacemarkModel) {
        user.id = generateRandomId()
        user.placemark.add(placemark)
        serialize()
    }

    override fun update(user: UserModel, placemark: PlacemarkModel) {
        val foundUser: UserModel? = users.find {
                p -> p.id == user.id
        }

        if (foundUser != null) {
            // iterate through the List of placemarks in User Model
            for(x in foundUser.placemark) {
                if(x.id.equals(placemark.id)) {
                    x.title = placemark.title
                    x.description = placemark.description
                    x.image_list= placemark.image_list
                    x.location = placemark.location
                    x.note = placemark.note
                    x.check_box = placemark.check_box
                    x.dateVisited = placemark.dateVisited
                    serialize()
                }
            }
        }
    }

    override fun delete(placemark: PlacemarkModel) {
        placemarks.remove(placemark)
        serialize()
    }

}

