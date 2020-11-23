package com.example.firebase_notification

class User {

    var Name : String? = null
    var message : String? = null

    constructor()


    constructor(name : String, Message : String){

        this.Name = name
        this.message = Message
    }
}