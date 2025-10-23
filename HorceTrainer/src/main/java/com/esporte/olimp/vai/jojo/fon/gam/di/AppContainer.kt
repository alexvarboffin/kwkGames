package com.esporte.olimp.vai.jojo.fon.gam.di

import android.content.Context
import com.esporte.olimp.vai.jojo.fon.gam.data.SettingsDataStore
import com.esporte.olimp.vai.jojo.fon.gam.data.local.database.HorseDatabase
import com.esporte.olimp.vai.jojo.fon.gam.data.repository.HealthRepositoryImpl
import com.esporte.olimp.vai.jojo.fon.gam.data.repository.HorseRepositoryImpl
import com.esporte.olimp.vai.jojo.fon.gam.data.repository.TrainingRepositoryImpl
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HealthRepository
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HorseRepository
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.TrainingRepository
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.AddHealthRecordUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.AddHorseUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.AddTrainingUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.DeleteHorseUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetAllHorsesUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetHealthRecordsForHorseUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetHorseByIdUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetTrainingsForHorseUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.UpdateHorseUseCase

interface AppContainer {
    val horseRepository: HorseRepository
    val trainingRepository: TrainingRepository
    val healthRepository: HealthRepository
    val settingsDataStore: SettingsDataStore

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
        HorseDatabase.Companion.getDatabase(context)
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

    override val settingsDataStore: SettingsDataStore by lazy {
        SettingsDataStore(context)
    }
}
