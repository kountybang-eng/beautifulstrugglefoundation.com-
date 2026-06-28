package com.example.beautifulstrugglefoundation.ui.donation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DonationWidget(
    onDismiss: () -> Unit,
    onComplete: (Map<String, String>) -> Unit
) {
    var step by remember { mutableStateOf(1) }
    val formData = remember { mutableStateMapOf<String, String>() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Donate Your Vehicle",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            LinearProgressIndicator(
                progress = { step / 3f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(8.dp),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            Crossfade(targetState = step) { currentStep ->
                when (currentStep) {
                    1 -> VehicleInfoStep(formData) { step = 2 }
                    2 -> ContactInfoStep(formData) { step = 3 }
                    3 -> SuccessStep { onComplete(formData) }
                }
            }
        }
    }
}

@Composable
fun VehicleInfoStep(
    data: MutableMap<String, String>,
    onNext: () -> Unit
) {
    var make by remember { mutableStateOf(data["make"] ?: "") }
    var model by remember { mutableStateOf(data["model"] ?: "") }
    var year by remember { mutableStateOf(data["year"] ?: "") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Tell us about the vehicle", style = MaterialTheme.typography.bodyLarge)
        
        OutlinedTextField(
            value = year,
            onValueChange = { year = it; data["year"] = it },
            label = { Text("Year") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = make,
            onValueChange = { make = it; data["make"] = it },
            label = { Text("Make (e.g. Toyota)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = model,
            onValueChange = { model = it; data["model"] = it },
            label = { Text("Model (e.g. Camry)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            enabled = make.isNotBlank() && model.isNotBlank(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Text("Next: Contact Details")
        }
    }
}

@Composable
fun ContactInfoStep(
    data: MutableMap<String, String>,
    onNext: () -> Unit
) {
    var name by remember { mutableStateOf(data["name"] ?: "") }
    var email by remember { mutableStateOf(data["email"] ?: "") }
    var phone by remember { mutableStateOf(data["phone"] ?: "") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("How can we reach you?", style = MaterialTheme.typography.bodyLarge)
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it; data["name"] = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it; data["email"] = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it; data["phone"] = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            enabled = name.isNotBlank() && (email.isNotBlank() || phone.isNotBlank()),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Text("Complete Submission")
        }
    }
}

@Composable
fun SuccessStep(onFinish: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Thank You!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Your donation request has been received. A team member will reach out soon to schedule a free pickup.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) {
            Text("Done")
        }
    }
}

@Preview
@Composable
fun DonationWidgetPreview() {
    MaterialTheme {
        DonationWidget(onDismiss = {}, onComplete = {})
    }
}
