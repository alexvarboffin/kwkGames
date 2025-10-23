package com.esporte.olimp.yay.ui.trainings

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
import androidx.compose.material.icons.filled.Star
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
import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Training
import com.esporte.olimp.vai.jojo.fon.gam.presentation.trainings.TrainingsUiState
import com.esporte.olimp.vai.jojo.fon.gam.presentation.trainings.TrainingsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsScreen(uiState: TrainingsUiState, onNavigateToNewTraining: () -> Unit) {
    val viewModel: TrainingsViewModel = viewModel()
    val trainings by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TRAININGS",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(trainings.trainings) { training ->
                TrainingItem(training = training, onClick = { /* TODO: View/Edit Training */ })
            }
        }

        Button(
            onClick = onNavigateToNewTraining,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "New Training")
            Spacer(modifier = Modifier.size(8.dp))
            Text("New Training")
        }
    }
}

@Composable
fun TrainingItem(training: Training, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = "Training", modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(text = training.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Date: ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(training.date)}")
                Text(text = "Distance: ${training.distanceMeters} meters")
                Text(text = "Duration: ${training.durationMinutes} minutes")
                Text(text = "Avg Speed: ${training.averageSpeedKmh} km/h")
            }
        }
    }
}
