package org.wit.placemark.models

interface Store {

    // Each variable will be assigned to a certain user, so we will need to pass in the user as a parameter for each function
    fun findAll(user : UserModel): List<PlacemarkModel>
    fun create(user : UserModel, placemark: PlacemarkModel)
    fun update(user : UserModel, placemark: PlacemarkModel)
    fun delete(user : UserModel, placemark: PlacemarkModel)

    // Functions for users //
    fun findAllUsers() : List<UserModel>
    fun findUser(id : Long) : UserModel?
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    // Find user by email to be use during authentication //
    fun findByEmail(email : String) : UserModel?
}