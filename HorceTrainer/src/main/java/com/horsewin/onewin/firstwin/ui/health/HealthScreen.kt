package com.horsewin.onewin.firstwin.ui.health

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.horsewin.onewin.firstwin.domain.model.HealthRecord
import com.horsewin.onewin.firstwin.presentation.health.HealthUiState
import com.horsewin.onewin.firstwin.presentation.health.HealthViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(uiState: HealthUiState, onNavigateToAddHealthData: () -> Unit) {
    val viewModel: HealthViewModel = viewModel()
    val healthRecords by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HEALTH",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(healthRecords.healthRecords) { record ->
                HealthRecordItem(record = record, onClick = { /* TODO: View/Edit Health Record */ })
            }
        }

        Button(
            onClick = onNavigateToAddHealthData,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Health Data")
            Spacer(modifier = Modifier.size(8.dp))
            Text("Add Health Data")
        }
    }
}

@Composable
fun HealthRecordItem(record: HealthRecord, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Favorite, contentDescription = "Health Record", modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(text = "Date: ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(record.date)}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                record.weight?.let { Text(text = "Weight: $it kg") }
                record.bodyTemperature?.let { Text(text = "Body Temp: $it °C") }
                record.pulse?.let { Text(text = "Pulse: $it bpm") }
                record.respiration?.let { Text(text = "Respiration: $it breaths/min") }
                record.acthLevel?.let { Text(text = "ACTH Level: $it pg/ml") }
                record.comment?.let { Text(text = "Comment: $it") }
            }
        }
    }
}
