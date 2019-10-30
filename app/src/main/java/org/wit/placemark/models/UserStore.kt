package org.wit.placemark.models

interface UserStore {

    // Each variable will be assigned to a certain currentUser, so we will need to pass in the currentUser as a parameter for each function
    fun findAll(user : UserModel): List<PlacemarkModel>
    fun create(user : UserModel, placemark: PlacemarkModel)
    fun update(user : UserModel, placemark: PlacemarkModel)
    fun delete(user : UserModel, placemark: PlacemarkModel)

    // Functions for users //
    fun findAllUsers() : ArrayList<UserModel>
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    // Find user by email  //
    fun findUserByEmail(email : String) : UserModel?
}