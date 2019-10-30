package org.wit.placemark.models

interface UserStore {

    // Hill Forts Function //
    fun findAll() : ArrayList<PlacemarkModel>
    fun findAllForts(user : UserModel): List<PlacemarkModel>
    fun createFort(user : UserModel, placemark: PlacemarkModel)
    fun updateFort(user : UserModel, placemark: PlacemarkModel)
    fun deleteFort(user : UserModel, placemark: PlacemarkModel)

    // Functions for users //
    fun findAllUsers() : ArrayList<UserModel>
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    // Find user by email  //
    fun findUserByEmail(email : String) : UserModel?

    // Note Methods //
    fun createNote(user: UserModel, placemark: PlacemarkModel, note: Note)
    fun deleteNote(user : UserModel, placemark: PlacemarkModel, note: Note)
}