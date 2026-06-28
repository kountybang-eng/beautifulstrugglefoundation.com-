package com.example.beautifulstrugglefoundation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beautifulstrugglefoundation.navigation.Route
import com.example.beautifulstrugglefoundation.navigation.NavDisplayWrapper
import com.example.beautifulstrugglefoundation.ui.auth.LoginScreen
import com.example.beautifulstrugglefoundation.ui.auth.SignupScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.beautifulstrugglefoundation.ui.theme.*
import com.example.beautifulstrugglefoundation.ui.HomeViewModel
import com.example.beautifulstrugglefoundation.ui.spark.DailySparkScreen
import com.example.beautifulstrugglefoundation.ui.donation.DonationWidget
import androidx.navigation3.runtime.NavKey

@Composable
fun App() {
    BeautifulStruggleFoundationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF050505)
        ) {
            val backStack = remember { mutableStateListOf<NavKey>(Route.Home) }

            NavDisplayWrapper<Route>(
                backstack = backStack,
                onBack = { if (backStack.size > 1) backStack.removeAt(backStack.size - 1) }
            ) { key ->
                when (key) {
                    Route.Login -> {
                        LoginScreen(
                            onNavigateToSignup = { backStack.add(Route.Signup) },
                            onLoginSuccess = { 
                                backStack.clear()
                                backStack.add(Route.Home) 
                            }
                        )
                    }
                    Route.Signup -> {
                        SignupScreen(
                            onNavigateToLogin = { backStack.removeAt(backStack.size - 1) },
                            onSignupSuccess = { 
                                backStack.clear()
                                backStack.add(Route.Home) 
                            }
                        )
                    }
                    Route.Home -> {
                        HomeScreen(
                            onLogout = { 
                                backStack.clear()
                                backStack.add(Route.Login) 
                            },
                            onNavigateToSparks = { backStack.add(Route.SparkBoard) }
                        )
                    }
                    Route.SparkBoard -> {
                        DailySparkScreen(
                            onBack = { backStack.removeAt(backStack.size - 1) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onNavigateToSparks: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val impactPoints by viewModel.impactPoints.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var showDonationWidget by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0A))) {
        // Graffiti Background Elements
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "BSF",
                style = TextStyle(
                    fontFamily = FontFamily.Cursive,
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White.copy(alpha = 0.05f)
                ),
                modifier = Modifier.align(Alignment.TopEnd).rotate(-25f).offset(y = 50.dp, x = (-20).dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Flyer Header Section
            HeaderSection()

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Impact Section
                ImpactCounter(impactPoints, loading)

                Spacer(modifier = Modifier.height(32.dp))

                // Foundation Hub - integrated flyer look
                FlyerBox(
                    title = "FOUNDATION HUB",
                    subtitle = "WWW.BEAUTIFULSTRUGGLEFOUNDATION.COM",
                    borderColor = FlyerNeonGreen,
                    headerIcon = Icons.Default.Handshake
                ) {
                    BulletPoint("Dedicated to low-income families.")
                    BulletPoint("Reliable Vehicles at dramatically reduced prices.")
                    BulletPoint("Small, Affordable Monthly Payments.")
                    BulletPoint("Breaking the cycle of predatory dealerships and loans.")
                    BulletPoint("Real Student Partnerships: BTC & Skagit Valley College.", isBold = true)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scouters App Section
                FlyerBox(
                    title = "THE SCOUTERS APP",
                    subtitle = "LAUNCHING SOON",
                    borderColor = FlyerGold,
                    headerIcon = Icons.Default.Search
                ) {
                    Text(
                        "FIND VEHICLES. STACK POINTS. CLEAN UP OUR STREETS.",
                        color = FlyerGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    BulletPoint("Scout abandoned cars to clean neighborhoods.")
                    BulletPoint("Compete and Win: Merchandise & Choose Listed Charities to DONATE TO!")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Community Testimony Section
                Text(
                    text = "COMMUNITY VOICES",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = FlyerGold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
                )
                
                MockComment("Brenda L.", "BSF didn't just give me a car, they gave me my independence back.")
                MockComment("Tyrell J.", "The Scouters app is gonna be a game changer for the block.")
                MockComment("Alex T.", "Proud to be part of a mission that actually helps people.")

                Spacer(modifier = Modifier.height(40.dp))

                // Action Buttons
                Button(
                    onClick = { showDonationWidget = true },
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FlyerGold, contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.DirectionsCar, null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("DONATE A VEHICLE", fontWeight = FontWeight.Black, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNavigateToSparks,
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, FlyerNeonGreen)
                ) {
                    Icon(Icons.Default.WbSunny, null, tint = FlyerNeonGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("COMMUNITY SPARK BOARD", color = FlyerNeonGreen, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Partners Bar
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PartnerTag("BTC")
                    PartnerTag("SCOUTERS")
                    PartnerTag("SKAGIT VALLEY")
                }

                TextButton(onClick = onLogout) {
                    Text("LOGOUT", color = Color.Gray.copy(alpha = 0.5f))
                }
            }
        }
    }

    if (showDonationWidget) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { showDonationWidget = false }) {
            DonationWidget(
                onDismiss = { showDonationWidget = false },
                onComplete = { showDonationWidget = false }
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("THE BEAUTIFUL", color = FlyerGold, fontWeight = FontWeight.Black, fontSize = 24.sp)
        Text(
            "STRUGGLE",
            style = TextStyle(
                fontSize = 68.sp,
                fontWeight = FontWeight.Black,
                color = FlyerGold
            ),
            textAlign = TextAlign.Center
        )
        Text("FOUNDATION", color = FlyerGold, fontWeight = FontWeight.Black, fontSize = 28.sp, letterSpacing = 4.sp)
        
        Text(
            "Presents",
            fontFamily = FontFamily.Cursive,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("GET READY!", color = Color.White, fontWeight = FontWeight.Black, fontSize = 36.sp)
        Text("WE ARE CHANGING THE GAME", color = FlyerNeonGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun BulletPoint(text: String, isBold: Boolean = false) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("• ", color = FlyerNeonGreen, fontWeight = FontWeight.Bold)
        Text(text, color = Color.White, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun FlyerBox(
    title: String,
    subtitle: String,
    borderColor: Color,
    headerIcon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, borderColor), RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(headerIcon, null, tint = borderColor, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, color = borderColor, fontWeight = FontWeight.Black, fontSize = 20.sp)
                Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
fun ImpactCounter(points: Int, loading: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().border(BorderStroke(1.dp, FlyerGold.copy(alpha = 0.4f)), RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
    ) {
        Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("COMMUNITY IMPACT SCORE", color = FlyerGold, fontWeight = FontWeight.Bold)
            if (loading) {
                CircularProgressIndicator(color = FlyerGold)
            } else {
                Text(
                    text = "$points",
                    style = TextStyle(
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        shadow = Shadow(color = FlyerGold, blurRadius = 20f)
                    )
                )
            }
            Text("10 FAMILIES • 10 CARS", color = FlyerNeonGreen, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MockComment(user: String, comment: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF181818))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(user, color = FlyerGold, fontWeight = FontWeight.Bold)
            Text("\"$comment\"", color = Color.White.copy(alpha = 0.8f), style = TextStyle(fontStyle = FontStyle.Italic))
        }
    }
}

@Composable
fun PartnerTag(name: String) {
    Text(
        name,
        color = Color.White.copy(alpha = 0.7f),
        fontWeight = FontWeight.Black,
        fontSize = 11.sp,
        modifier = Modifier.border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(4.dp)).padding(8.dp)
    )
}
git init
git add .
git commit -m "Initial commit with GitHub/GitLab support"
git branch -M main
git remote add origin https://github.com/kountybang-eng/beautifulstrugglefoundation.com-.git
git push -u origin main
