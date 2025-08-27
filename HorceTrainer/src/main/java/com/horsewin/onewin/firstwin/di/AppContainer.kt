package com.horsewin.onewin.firstwin.di

import android.content.Context
import com.horsewin.onewin.firstwin.data.local.database.HorseDatabase
import com.horsewin.onewin.firstwin.data.repository.HealthRepositoryImpl
import com.horsewin.onewin.firstwin.data.repository.HorseRepositoryImpl
import com.horsewin.onewin.firstwin.data.repository.TrainingRepositoryImpl
import com.horsewin.onewin.firstwin.domain.repository.HealthRepository
import com.horsewin.onewin.firstwin.domain.repository.HorseRepository
import com.horsewin.onewin.firstwin.domain.repository.TrainingRepository
import com.horsewin.onewin.firstwin.domain.usecase.AddHealthRecordUseCase
import com.horsewin.onewin.firstwin.domain.usecase.AddHorseUseCase
import com.horsewin.onewin.firstwin.domain.usecase.AddTrainingUseCase
import com.horsewin.onewin.firstwin.domain.usecase.DeleteHorseUseCase
import com.horsewin.onewin.firstwin.domain.usecase.GetAllHorsesUseCase
import com.horsewin.onewin.firstwin.domain.usecase.GetHealthRecordsForHorseUseCase
import com.horsewin.onewin.firstwin.domain.usecase.GetHorseByIdUseCase
import com.horsewin.onewin.firstwin.domain.usecase.GetTrainingsForHorseUseCase
import com.horsewin.onewin.firstwin.domain.usecase.UpdateHorseUseCase

interface AppContainer {
    val horseRepository: HorseRepository
    val trainingRepository: TrainingRepository
    val healthRepository: HealthRepository

    val addHorseUseCase: AddHorseUseCase
    val getHorseByIdUseCase: GetHorseByIdUseCase
    val getAllHorsesUseCase: GetAllHorsesUseCase
    val updateHorseUseCase: UpdateHorseUseCase
    val deleteHorseUseCase: DeleteHorseUseCase

    val addTrainingUseCase: AddTrainingUseCase
    val getTrainingsForHorseUseCase: GetTrainingsForHorseUseCase

    val addHealthRecordUseCase: AddHealthRecordUseCase
    val getHealthRecordsForHorseUseCase: GetHealthRecordsForHorseUseCase
}

class AppContainerImpl(private val context: Context) : AppContainer {

    private val database: HorseDatabase by lazy {
        HorseDatabase.getDatabase(context)
    }

    override val horseRepository: HorseRepository by lazy {
        HorseRepositoryImpl(database.horseDao())
    }

    override val trainingRepository: TrainingRepository by lazy {
        TrainingRepositoryImpl(database.trainingDao())
    }

    override val healthRepository: HealthRepository by lazy {
        HealthRepositoryImpl(database.healthRecordDao())
    }

    override val addHorseUseCase: AddHorseUseCase by lazy {
        AddHorseUseCase(horseRepository)
    }

    override val getHorseByIdUseCase: GetHorseByIdUseCase by lazy {
        GetHorseByIdUseCase(horseRepository)
    }

    override val getAllHorsesUseCase: GetAllHorsesUseCase by lazy {
        GetAllHorsesUseCase(horseRepository)
    }

    override val updateHorseUseCase: UpdateHorseUseCase by lazy {
        UpdateHorseUseCase(horseRepository)
    }

    override val deleteHorseUseCase: DeleteHorseUseCase by lazy {
        DeleteHorseUseCase(horseRepository)
    }

    override val addTrainingUseCase: AddTrainingUseCase by lazy {
        AddTrainingUseCase(trainingRepository)
    }

    override val getTrainingsForHorseUseCase: GetTrainingsForHorseUseCase by lazy {
        GetTrainingsForHorseUseCase(trainingRepository)
    }

    override val addHealthRecordUseCase: AddHealthRecordUseCase by lazy {
        AddHealthRecordUseCase(healthRepository)
    }

    override val getHealthRecordsForHorseUseCase: GetHealthRecordsForHorseUseCase by lazy {
        GetHealthRecordsForHorseUseCase(healthRepository)
    }
}
