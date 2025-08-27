package com.horsewin.onewin.firstwin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.horsewin.onewin.firstwin.di.AppContainer
import com.horsewin.onewin.firstwin.presentation.addhorse.AddHorseViewModel
import com.horsewin.onewin.firstwin.presentation.addhealthdata.AddHealthDataViewModel
import com.horsewin.onewin.firstwin.presentation.health.HealthViewModel
import com.horsewin.onewin.firstwin.presentation.home.HomeViewModel
import com.horsewin.onewin.firstwin.presentation.newtraining.NewTrainingViewModel
import com.horsewin.onewin.firstwin.presentation.profile.ProfileViewModel
import com.horsewin.onewin.firstwin.presentation.schedule.ScheduleViewModel
import com.horsewin.onewin.firstwin.presentation.trainings.TrainingsViewModel

class ViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel() as T
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
                ProfileViewModel(appContainer.getHorseByIdUseCase, appContainer.getAllHorsesUseCase) as T
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
