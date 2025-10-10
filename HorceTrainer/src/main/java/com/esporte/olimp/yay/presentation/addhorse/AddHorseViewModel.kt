package com.esporte.olimp.yay.presentation.addhorse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esporte.olimp.yay.domain.model.Horse
import com.esporte.olimp.yay.domain.usecase.AddHorseUseCase
import kotlinx.coroutines.launch
import java.util.Date

class AddHorseViewModel(private val addHorseUseCase: AddHorseUseCase) : ViewModel() {

    fun addHorse(
        name: String,
        breed: String?,
        dateOfBirth: Date?,
        gender: String?,
        photoPath: String?,
        weight: Int?,
        weightGoal: Int?,
        bodyTemperature: Double?,
        pulse: Int?,
        respiration: Int?,
        height: Double?,
        acthLevel: Double?
    ) {
        viewModelScope.launch {
            val horse = Horse(
                name = name,
                breed = breed,
                dateOfBirth = dateOfBirth,
                gender = gender,
                photoPath = photoPath,
                weight = weight,
                weightGoal = weightGoal,
                bodyTemperature = bodyTemperature,
                pulse = pulse,
                respiration = respiration,
                height = height,
                acthLevel = acthLevel
            )
            addHorseUseCase(horse)
        }
    }
}
