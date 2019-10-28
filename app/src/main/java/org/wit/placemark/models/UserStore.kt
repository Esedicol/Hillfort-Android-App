package org.wit.placemark.models

interface UserStore {

    // Hill Fort Methods for individual users //
    fun findAll (userID: UserModel): List<PlacemarkModel>
    fun create (userID: UserModel, fort: PlacemarkModel)
    fun update (userID: UserModel, fort: PlacemarkModel)
    fun delete (userID: UserModel, fort: PlacemarkModel)

    // User methods //
    fun findUser (userName: String) : UserModel
    fun createUser (user: UserModel)
    fun deleteAccount (user: UserModel)
    fun editAccount (user: UserModel)

}