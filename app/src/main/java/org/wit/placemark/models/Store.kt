package org.wit.placemark.models

interface Store {
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
    fun delete(placemark: PlacemarkModel)

    // Functions for users //
    fun findAllUsers() : List<UserModel>
    fun findUser(id : Long) : UserModel?
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    // Find user by email to be use during authentication //
    fun findByEmail(email : String) : UserModel?
}