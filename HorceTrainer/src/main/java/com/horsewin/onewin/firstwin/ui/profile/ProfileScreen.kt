package com.horsewin.onewin.firstwin.ui.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter

import com.horsewin.onewin.firstwin.presentation.profile.ProfileUiState
import com.horsewin.onewin.firstwin.ui.theme.Blue
import com.horsewin.onewin.firstwin.ui.theme.DarkBlue
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onAddDataClick: () -> Unit = {},
    onAddHorseClick: () -> Unit = {},
    onHorseSelected: (Long) -> Unit = {},
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "HORSE PROFILE",
            color = Blue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Horse selection if multiple horses exist
        if (uiState.horses.size > 1) {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                TextField(
                    value = uiState.horse?.name ?: "Select a horse",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        disabledTextColor = Color.White,
                        focusedTrailingIconColor = Color.White,
                        unfocusedTrailingIconColor = Color.White,
                        disabledTrailingIconColor = Color.White
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    uiState.horses.forEach { horse ->
                        DropdownMenuItem(
                            text = { Text(horse.name) },
                            onClick = {
                                onHorseSelected(horse.id)
                                expanded = false
                            }
                        ) 
                    }
                }
            }
        }

        // Horse profile card
        if (uiState.horse != null) {
            HorseProfileCard(
                horse = uiState.horse,
                onPhotoClick = {
                    Toast.makeText(context, "Photo clicked", Toast.LENGTH_SHORT).show()
                },
                onPhotoLongClick = {
                    Toast.makeText(context, "Photo long-pressed", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Add Data button
            Button(
                onClick = onAddDataClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "+ ADD DATA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            // Empty state - no horse selected
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, Blue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No Horse Selected",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Add a horse to get started",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onAddHorseClick, // Use the callback here
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Add Horse")
                    }
                }
            }
        }
    }
}

@Composable
fun HorseProfileCard(
    horse: com.horsewin.onewin.firstwin.domain.model.Horse,
    onPhotoClick: () -> Unit,
    onPhotoLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Horse photo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .combinedClickable(
                        onClick = onPhotoClick,
                        onLongClick = onPhotoLongClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (horse.photoPath != null) {
                    Image(
                        painter = rememberAsyncImagePainter(horse.photoPath),
                        contentDescription = "Horse photo",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp)), // Applied rounding here
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder for horse photo
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(8.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(16.dp)) // Applied rounding here
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Horse photo placeholder",
                            tint = Blue,
                            modifier = Modifier
                                .size(64.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Horse name
            Text(
                text = horse.name.uppercase(),
                color = Blue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Horse details
            DetailRow("Gender:", horse.gender ?: "Not specified")
            DetailRow("Breed:", horse.breed ?: "Not specified")

            if (horse.dateOfBirth != null) {
                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                DetailRow("Date of Birth:", dateFormat.format(horse.dateOfBirth))
            } else {
                DetailRow("Date of Birth:", "Not specified")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Metrics section
            Text(
                text = "METRICS",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            MetricRow("Weight", horse.weight?.toString() ?: "N/A", "kg")
            MetricRow("Weight Goal", horse.weightGoal?.toString() ?: "N/A", "kg")
            MetricRow("Body Temperature", horse.bodyTemperature?.toString() ?: "N/A", "°C")
            MetricRow("Pulse", horse.pulse?.toString() ?: "N/A", "bpm")
            MetricRow("Respiration", horse.respiration?.toString() ?: "N/A", "rpm")
            MetricRow("Height", horse.height?.toString() ?: "N/A", "cm")
            MetricRow("ACTH Level (PPID)", horse.acthLevel?.toString() ?: "N/A", "pg/ml")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MetricRow(label: String, value: String, unit: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFF333333), RoundedCornerShape(8.dp)) // Gray background
            .padding(horizontal = 12.dp, vertical = 8.dp), // Inner padding
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        ) 
        Text(
            text = "$value $unit",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.End, // Right alignment
            modifier = Modifier.weight(1f) // Take remaining space
        )
    }
}
