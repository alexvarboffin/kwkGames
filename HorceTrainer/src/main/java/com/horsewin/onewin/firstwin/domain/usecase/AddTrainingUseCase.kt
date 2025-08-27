package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.model.Training
import com.horsewin.onewin.firstwin.domain.repository.TrainingRepository

class AddTrainingUseCase(private val trainingRepository: TrainingRepository) {
    suspend operator fun invoke(training: Training): Long {
        return trainingRepository.addTraining(training)
    }
}
