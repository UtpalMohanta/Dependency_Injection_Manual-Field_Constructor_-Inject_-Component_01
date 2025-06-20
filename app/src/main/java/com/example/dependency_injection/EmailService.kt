package com.example.dependency_injection

import android.util.Log
import javax.inject.Inject

class EmailService @Inject constructor(){
    fun send(to:String,from:String,body:String)
    {
        Log.d("Utpal1","Email sent")
    }
}

/*
class EmailService(){
    fun send(to:String,from:String,body:String)
    {
        Log.d("Utpal1","Email sent")
    }
}*/ /*worng scince not include  @Inject constructor Dagger not find how to create object VideoC:03*/
