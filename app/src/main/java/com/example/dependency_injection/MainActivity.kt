package com.example.dependency_injection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val userRepository=UserRepository()
        val emailService=EmailService()
        val userRegistrationService=UserRegistrationService(userRepository,emailService) // Manual injection vedioC:02*/

        val component=DaggerUserRegistrationComponent.builder().build()
        val userRegistrationService=component.getUserRegistrationService() /*VideoC:03*/
        val emailService=component. getEmailService()
        userRegistrationService.registerUser("utpalmohanta517@gmail.com","11111")

    }


}




