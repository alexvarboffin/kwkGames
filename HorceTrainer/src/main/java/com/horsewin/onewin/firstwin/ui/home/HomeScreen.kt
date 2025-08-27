package com.horsewin.onewin.firstwin.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horsewin.onewin.firstwin.presentation.home.HomeUiState
import com.horsewin.onewin.firstwin.ui.theme.Blue
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAddHorseClick: () -> Unit = {},
    onNewTrainingClick: () -> Unit = {},
    onAddHealthDataClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "WELCOME",
            color = Blue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(onClick = onAddHorseClick) { Text("Add Horse") }
        // Quick Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Button(onClick = onNewTrainingClick) { Text("New Training") }
            Button(onClick = onAddHealthDataClick) { Text("Add Health Data") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error, color = Color.Red)
            }
        } else {
            // Latest Training Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Latest Training", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    if (uiState.latestTraining != null) {
                        val training = uiState.latestTraining
                        Text("Name: ${training.name}")
                        Text("Date: ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(training.date)}")
                        Text("Duration: ${training.durationMinutes} min")
                    } else {
                        Text("No recent training data.", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Latest Health Metrics Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Latest Health Metrics", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    if (uiState.latestHealthRecord != null) {
                        val healthRecord = uiState.latestHealthRecord
                        Text("Date: ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(healthRecord.date)}")
                        healthRecord.weight?.let { Text("Weight: $it kg") }
                        healthRecord.bodyTemperature?.let { Text("Temperature: $it °C") }
                        healthRecord.pulse?.let { Text("Pulse: $it bpm") }
                    } else {
                        Text("No recent health data.", color = Color.Gray)
                    }
                }
            }
        }
    }
}
