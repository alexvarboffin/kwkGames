package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.Training
import com.esporte.olimp.yay.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow

class GetTrainingsForHorseUseCase(private val trainingRepository: TrainingRepository) {
    operator fun invoke(horseId: Long): Flow<List<Training>> {
        return trainingRepository.getTrainingsForHorse(horseId)
    }
}
