package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.Training
import com.esporte.olimp.yay.domain.repository.TrainingRepository

class AddTrainingUseCase(private val trainingRepository: TrainingRepository) {
    suspend operator fun invoke(training: Training): Long {
        return trainingRepository.addTraining(training)
    }
}
