package com.esporte.olimp.yay.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esporte.olimp.yay.di.AppContainer
import com.esporte.olimp.yay.presentation.addhorse.AddHorseViewModel
import com.esporte.olimp.yay.presentation.addhealthdata.AddHealthDataViewModel
import com.esporte.olimp.yay.presentation.health.HealthViewModel
import com.esporte.olimp.yay.presentation.home.HomeViewModel
import com.esporte.olimp.yay.presentation.newtraining.NewTrainingViewModel
import com.esporte.olimp.yay.presentation.profile.ProfileViewModel
import com.esporte.olimp.yay.presentation.schedule.ScheduleViewModel
import com.esporte.olimp.yay.presentation.trainings.TrainingsViewModel

class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(appContainer.getTrainingsForHorseUseCase, appContainer.getHealthRecordsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> {
                ScheduleViewModel(appContainer.getTrainingsForHorseUseCase, appContainer.getHealthRecordsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(TrainingsViewModel::class.java) -> {
                TrainingsViewModel(appContainer.getTrainingsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(HealthViewModel::class.java) -> {
                HealthViewModel(appContainer.getHealthRecordsForHorseUseCase) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(appContainer.getHorseByIdUseCase, appContainer.getAllHorsesUseCase, appContainer.settingsDataStore) as T
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
