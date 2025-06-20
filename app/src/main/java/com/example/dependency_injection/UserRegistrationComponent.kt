package com.example.dependency_injection

import dagger.Component

@Component
interface UserRegistrationComponent {
    fun getUserRegistrationService(): UserRegistrationService

    fun getEmailService(): EmailService
}