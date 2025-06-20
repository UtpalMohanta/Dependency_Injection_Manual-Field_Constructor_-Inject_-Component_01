package com.example.dependency_injection

import javax.inject.Inject

class UserRegistrationService @Inject constructor(private val userRepository: UserRepository,
                                                  private val emailService: EmailService) /*Constructor injection*/ {

    fun registerUser(email:String,password:String)
    {
        userRepository.SaveUser(email,password)
        emailService.send(email,"utpalmohanta517@gmail.com","User Registerd")
    }
}