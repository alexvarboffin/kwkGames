package com.esporte.olimp.vai.jojo.fon.gam.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esporte.olimp.vai.jojo.fon.gam.di.AppContainer
import com.esporte.olimp.vai.jojo.fon.gam.presentation.addhealthdata.AddHealthDataViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.addhorse.AddHorseViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.health.HealthViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.home.HomeViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.newtraining.NewTrainingViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.profile.ProfileViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.schedule.ScheduleViewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.trainings.TrainingsViewModel

class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(appContainer.getTrainingsForHorseUseCase, appContainer.getHealthRecordsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> {
                ScheduleViewModel(
                    appContainer.getTrainingsForHorseUseCase,
                    appContainer.getHealthRecordsForHorseUseCase
                ) as T
            }
            modelClass.isAssignableFrom(TrainingsViewModel::class.java) -> {
                TrainingsViewModel(appContainer.getTrainingsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(HealthViewModel::class.java) -> {
                HealthViewModel(appContainer.getHealthRecordsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(
                    appContainer.getHorseByIdUseCase,
                    appContainer.getAllHorsesUseCase,
                    appContainer.settingsDataStore
                ) as T
            }
            modelClass.isAssignableFrom(AddHorseViewModel::class.java) -> {
                AddHorseViewModel(appContainer.addHorseUseCase) as T
            }
            modelClass.isAssignableFrom(NewTrainingViewModel::class.java) -> {
                NewTrainingViewModel(appContainer.addTrainingUseCase) as T
            }
            modelClass.isAssignableFrom(AddHealthDataViewModel::class.java) -> {
                AddHealthDataViewModel(appContainer.addHealthRecordUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
