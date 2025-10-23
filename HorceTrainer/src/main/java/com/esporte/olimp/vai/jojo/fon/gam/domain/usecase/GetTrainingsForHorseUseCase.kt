package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Training
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow

class GetTrainingsForHorseUseCase(private val trainingRepository: TrainingRepository) {
    operator fun invoke(horseId: Long): Flow<List<Training>> {
        return trainingRepository.getTrainingsForHorse(horseId)
    }
}
