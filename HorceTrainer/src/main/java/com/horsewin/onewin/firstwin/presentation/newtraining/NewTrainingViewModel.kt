package com.horsewin.onewin.firstwin.presentation.newtraining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horsewin.onewin.firstwin.domain.model.Training
import com.horsewin.onewin.firstwin.domain.usecase.AddTrainingUseCase
import kotlinx.coroutines.launch
import java.util.Date

class NewTrainingViewModel(private val addTrainingUseCase: AddTrainingUseCase) : ViewModel() {

    fun addTraining(name: String, date: Date, duration: Int, surfaceType: String, distance: Int, avgSpeed: Double, transitionsWalk: Int?, transitionsTrot: Int?, notes: String?) {
        viewModelScope.launch {
            val training = Training(
                name = name,
                date = date,
                durationMinutes = duration,
                surfaceType = surfaceType,
                distanceMeters = distance,
                averageSpeedKmh = avgSpeed,
                transitionsWalk = transitionsWalk,
                transitionsTrot = transitionsTrot,
                notes = notes,
                horseId = 1L // TODO: Replace with actual selected horse ID
            )
            addTrainingUseCase(training)
        }
    }
}
