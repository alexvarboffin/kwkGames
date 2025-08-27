package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.model.Training
import com.horsewin.onewin.firstwin.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow

class GetTrainingsForHorseUseCase(private val trainingRepository: TrainingRepository) {
    operator fun invoke(horseId: Long): Flow<List<Training>> {
        return trainingRepository.getTrainingsForHorse(horseId)
    }
}
