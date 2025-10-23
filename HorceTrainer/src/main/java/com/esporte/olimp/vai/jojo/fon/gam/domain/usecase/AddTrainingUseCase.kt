package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Training
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.TrainingRepository

class AddTrainingUseCase(private val trainingRepository: TrainingRepository) {
    suspend operator fun invoke(training: Training): Long {
        return trainingRepository.addTraining(training)
    }
}
