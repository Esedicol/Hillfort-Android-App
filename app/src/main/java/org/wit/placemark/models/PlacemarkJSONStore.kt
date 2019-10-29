package org.wit.placemark.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.*
import java.util.*

val JSON_FILE = "placemarks.json"
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



    override fun findAllUsers(): List<UserModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findUser(id: Long): UserModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(user: UserModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(user: UserModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(user: UserModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


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
                    x.image = placemark.image
                    x.lat = placemark.lat
                    x.lng = placemark.lng
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

