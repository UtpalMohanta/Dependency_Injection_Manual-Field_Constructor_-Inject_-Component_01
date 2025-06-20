package com.example.dependency_injection

import android.util.Log
import javax.inject.Inject

class UserRepository @Inject constructor(){
    fun SaveUser(email:String,password:String)
    {
        Log.d("utpal","User saved in Database")
    }
}