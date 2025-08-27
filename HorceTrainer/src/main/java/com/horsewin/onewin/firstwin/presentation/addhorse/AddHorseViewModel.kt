package com.horsewin.onewin.firstwin.presentation.addhorse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horsewin.onewin.firstwin.domain.model.Horse
import com.horsewin.onewin.firstwin.domain.usecase.AddHorseUseCase
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
