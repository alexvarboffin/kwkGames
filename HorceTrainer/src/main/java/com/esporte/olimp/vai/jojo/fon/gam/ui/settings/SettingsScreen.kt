package com.esporte.olimp.vai.jojo.fon.gam.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esporte.olimp.vai.jojo.fon.gam.presentation.ViewModelFactory
import com.esporte.olimp.vai.jojo.fon.gam.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(factory: ViewModelFactory) {
    val viewModel: SettingsViewModel = viewModel(factory = factory)
    val theme by viewModel.theme.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Theme", modifier = Modifier.padding(bottom = 16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(selected = theme == "Light", onClick = { viewModel.setTheme("Light") })
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = theme == "Light", onClick = { viewModel.setTheme("Light") })
            Text(text = "Light", modifier = Modifier.padding(start = 8.dp))
        }
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(selected = theme == "Dark", onClick = { viewModel.setTheme("Dark") })
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = theme == "Dark", onClick = { viewModel.setTheme("Dark") })
            Text(text = "Dark", modifier = Modifier.padding(start = 8.dp))
        }
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(selected = theme == "System", onClick = { viewModel.setTheme("System") })
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = theme == "System", onClick = { viewModel.setTheme("System") })
            Text(text = "System", modifier = Modifier.padding(start = 8.dp))
        }
    }
}
