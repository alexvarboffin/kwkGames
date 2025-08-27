package com.horsewin.onewin.firstwin.presentation.addhealthdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horsewin.onewin.firstwin.domain.model.HealthRecord
import com.horsewin.onewin.firstwin.domain.usecase.AddHealthRecordUseCase
import kotlinx.coroutines.launch
import java.util.Date

class AddHealthDataViewModel(private val addHealthRecordUseCase: AddHealthRecordUseCase) : ViewModel() {

    fun addHealthRecord(date: Date, weight: Int?, bodyTemperature: Double?, pulse: Int?, respiration: Int?, acthLevel: Double?, comment: String?) {
        viewModelScope.launch {
            val record = HealthRecord(
                date = date,
                weight = weight,
                bodyTemperature = bodyTemperature,
                pulse = pulse,
                respiration = respiration,
                acthLevel = acthLevel,
                comment = comment,
                horseId = 1L // TODO: Replace with actual selected horse ID
            )
            addHealthRecordUseCase(record)
        }
    }
}
