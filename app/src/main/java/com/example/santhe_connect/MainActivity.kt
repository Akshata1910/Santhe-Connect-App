package com.example.santhe_connect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SantheConnectApp()
        }
    }
}

@Composable
fun SantheConnectApp() {
    var currentScreen by remember { mutableStateOf("HOME") }
    var isKannada by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = currentScreen,
                onTabSelected = { currentScreen = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { currentScreen = "ADD_REVIEW" },
                containerColor = Color(0xFFD94A00),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8E7))
                .padding(paddingValues)
        ) {
            TopHeader(
                isKannada = isKannada,
                onLanguageToggle = {
                    isKannada = !isKannada
                }
            )

            when (currentScreen) {
                "HOME" -> HomeScreen(
                    isKannada = isKannada,
                    onNavigate = { currentScreen = it }
                )

                "FOOD" -> NearbyScreen(
                    screenTitle = if (isKannada) "ಹತ್ತಿರದ ಸ್ಥಳೀಯ ಆಹಾರ" else "Nearby Local Food",
                    keyword = "local food tiffin mess street food restaurant"
                )

                "MARKETS" -> NearbyScreen(
                    screenTitle = if (isKannada) "ಹತ್ತಿರದ ಮಾರುಕಟ್ಟೆಗಳು" else "Nearby Markets",
                    keyword = "market santhe bazaar local market"
                )

                "STAYS" -> NearbyScreen(
                    screenTitle = if (isKannada) "ಹತ್ತಿರದ ವಸತಿಗಳು" else "Nearby Homestays",
                    keyword = "homestay lodge guest house"
                )

                "CRAFTS" -> NearbyScreen(
                    screenTitle = if (isKannada) "ಹತ್ತಿರದ ಕೈಗಾರಿಕೆಗಳು" else "Nearby Crafts",
                    keyword = "handicraft craft store handmade products"
                )

                "CALENDAR" -> NearbyScreen(
                    screenTitle = if (isKannada) "ಹತ್ತಿರದ ಸಂತೆ ಕ್ಯಾಲೆಂಡರ್" else "Santhe Calendar Near You",
                    keyword = "weekly market santhe local market bazaar near me"
                )

                "NEARBY" -> NearbyScreen(
                    screenTitle = "Nearby Authentic Places",
                    keyword = "local food market homestay handicraft"
                )

                "WALL" -> WallScreen(isKannada = isKannada)
                "ADD_REVIEW" -> AddReviewScreen(isKannada = isKannada)
                else -> {
                    if (currentScreen.startsWith("SEARCH_")) {
                        NearbyScreen(
                            screenTitle = if (isKannada) "ಹುಡುಕಾಟ ಫಲಿತಾಂಶಗಳು" else "Search Results",
                            keyword = currentScreen.removePrefix("SEARCH_")
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopHeader(
    isKannada: Boolean,
    onLanguageToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .background(Color(0xFFD94A00))
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(44.dp),
            shape = CircleShape,
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "S",
                    color = Color(0xFFD94A00),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = if (isKannada) "ಸಂತೆ-ಕನೆಕ್ಟ್" else "Santhe-Connect",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            modifier = Modifier.clickable {
                onLanguageToggle()
            },
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFE97A32)
        ) {
            Text(
                text = if (isKannada) "English" else "ಕನ್ನಡ",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
            )
        }
    }
}

@Composable
fun HomeScreen(
    isKannada: Boolean,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        SearchBar(
            isKannada = isKannada,
            onSearch = { query ->
                onNavigate("SEARCH_$query")
            }
        )

        Spacer(modifier = Modifier.height(28.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CategoryCard(
                title = if (isKannada) "ಆಹಾರ" else "FOOD",
                icon = Icons.Default.Restaurant,
                colors = listOf(Color(0xFFFFA000), Color(0xFFFF9800)),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate("FOOD") }
            )

            Spacer(modifier = Modifier.width(16.dp))

            CategoryCard(
                title = if (isKannada) "ಮಾರುಕಟ್ಟೆ" else "MARKETS",
                icon = Icons.Default.ShoppingBasket,
                colors = listOf(Color(0xFFF57C00), Color(0xFFEF6C00)),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate("MARKETS") }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CategoryCard(
                title = if (isKannada) "ವಸತಿ" else "STAYS",
                icon = Icons.Default.Home,
                colors = listOf(Color(0xFFE65100), Color(0xFFD84315)),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate("STAYS") }
            )

            Spacer(modifier = Modifier.width(16.dp))

            CategoryCard(
                title = if (isKannada) "ಕೈಗಾರಿಕೆ" else "CRAFTS",
                icon = Icons.Default.Palette,
                colors = listOf(Color(0xFFC0392B), Color(0xFFB83227)),
                modifier = Modifier.weight(1f),
                onClick = { onNavigate("CRAFTS") }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        TodaySantheCard(isKannada)
    }
}

@Composable
fun SearchBar(
    isKannada: Boolean,
    onSearch: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                if (isKannada)
                    "ಆಹಾರ ಮತ್ತು ಮಾರುಕಟ್ಟೆ ಹುಡುಕಿ..."
                else
                    "Search local food, markets..."
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Search",
                modifier = Modifier.clickable {
                    if (searchText.isNotBlank()) {
                        onSearch(searchText)
                    }
                }
            )
        },
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
            onSearch = {
                if (searchText.isNotBlank()) {
                    onSearch(searchText)
                }
            }
        ),
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Search
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        shape = RoundedCornerShape(18.dp),
        singleLine = true
    )
}

@Composable
fun CategoryCard(
    title: String,
    icon: ImageVector,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(38.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun TodaySantheCard(isKannada: Boolean) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isKannada) "ಇಂದಿನ ಸಂತೆ" else "Today's Santhe",

                    color = Color(0xFFD94A00),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.weight(1f))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFF27AE60)
                ) {
                    Text(
                        text = "OPEN NOW",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isKannada)
                    "ನಿಮ್ಮ ಹತ್ತಿರದ ಸ್ಥಳೀಯ ಮಾರುಕಟ್ಟೆ, ಆಹಾರ ಮಳಿಗೆ ಮತ್ತು ಸಂಸ್ಕೃತಿಯನ್ನು ಕಂಡುಹಿಡಿಯಿರಿ."
                else
                    "Discover local markets, food stalls, crafts and cultural experiences near you.",
                color = Color.DarkGray,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = selectedTab == "HOME",
            onClick = { onTabSelected("HOME") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("HOME") }
        )

        NavigationBarItem(
            selected = selectedTab == "NEARBY",
            onClick = { onTabSelected("NEARBY") },
            icon = { Icon(Icons.Default.Map, contentDescription = "Nearby") },
            label = { Text("NEARBY") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Spacer(modifier = Modifier.size(24.dp)) },
            label = { Text("") },
            enabled = false
        )

        NavigationBarItem(
            selected = selectedTab == "CALENDAR",
            onClick = { onTabSelected("CALENDAR") },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar") },
            label = { Text("CALENDAR") }
        )

        NavigationBarItem(
            selected = selectedTab == "WALL",
            onClick = { onTabSelected("WALL") },
            icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Wall") },
            label = { Text("WALL") }
        )
    }
}

@Composable
fun PlaceholderScreen(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD94A00)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = subtitle,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}

    data class LocalPlace(
        val name: String,
        val category: String,
        val specialty: String,
        val latitude: Double,
        val longitude: Double,
        val timing: String,
        val rating: String
    )

@Composable
fun NearbyScreen(
    screenTitle: String,
    keyword: String
) {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var permissionGranted by remember { mutableStateOf(false) }
    var places by remember { mutableStateOf<List<GooglePlace>>(emptyList()) }
    var statusText by remember { mutableStateOf("Scanning your location...") }

    val apiKey = "AIzaSyDJ63muc3imR538JWgeCbnYtr4-nahD5Lg"

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            permissionGranted = true
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { location ->

                if (location == null) {
                    statusText = "Unable to get location. Set emulator location and try again."
                    return@addOnSuccessListener
                }

                statusText = "Finding real nearby places..."

                val retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build()

                val api = retrofit.create(PlacesApi::class.java)

                api.getNearbyPlaces(
                    location = "${location.latitude},${location.longitude}",
                    radius = 5000,
                    keyword = keyword,
                    apiKey = apiKey
                ).enqueue(object : retrofit2.Callback<PlacesResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<PlacesResponse>,
                        response: retrofit2.Response<PlacesResponse>
                    ) {
                        places = response.body()?.results ?: emptyList()
                        statusText = if (places.isEmpty()) {
                            "No nearby places found."
                        } else {
                            "Recommended places near you"
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<PlacesResponse>,
                        t: Throwable
                    ) {
                        statusText = "Failed to load places. Check internet/API key."
                    }
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = screenTitle,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD94A00)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = statusText,
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(18.dp))

        places.forEach { place ->
            NearbyPlaceCard(
                name = place.name,
                category = "Nearby Place",
                specialty = place.vicinity ?: "Local place near you",
                distance = "Based on your current location",
                timing = "Open details in Maps",
                rating = place.rating?.toString() ?: "N/A",
                onClick = {
                    val uri = Uri.parse("geo:0,0?q=${Uri.encode(place.name + " " + (place.vicinity ?: ""))}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
fun NearbyPlaceCard(
    name: String,
    category: String,
    specialty: String,
    distance: String,
    timing: String,
    rating: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = category.uppercase(),
                    color = Color(0xFFD94A00),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "★ $rating",
                    color = Color(0xFFFF9800),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = specialty,
                color = Color.DarkGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$distance • $timing",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}
@Composable
fun CalendarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = "Santhe Calendar",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD94A00)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Day-wise local markets and food spots.",
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(18.dp))

        MarketDayCard("Monday", "Local Vegetable Santhe", "6:00 AM - 12:00 PM", "Fresh vegetables and fruits")
        MarketDayCard("Tuesday", "Village Food Street", "7:00 AM - 11:00 AM", "Idli, dosa, jolada rotti")
        MarketDayCard("Wednesday", "Flower Market", "5:30 AM - 10:30 AM", "Fresh flowers and garlands")
        MarketDayCard("Friday", "Craft Santhe", "10:00 AM - 5:00 PM", "Handmade products and toys")
        MarketDayCard("Sunday", "Weekly Santhe", "6:00 AM - 1:00 PM", "Local snacks, vegetables and crafts")
    }
}

@Composable
fun MarketDayCard(day: String, name: String, time: String, speciality: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = day.uppercase(),
                color = Color(0xFFD94A00),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = time, fontSize = 14.sp, color = Color.DarkGray)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = speciality, fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Composable
fun WallScreen(isKannada: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        Text(
            text = if (isKannada) "ಪ್ರಯಾಣಿಕರ ಗೋಡೆ" else "Traveler's Wall",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD94A00)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Real experiences shared by visitors.",
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(18.dp))

        ReviewCard(
            "Aditi V.",
            "Local Food Street",
            "Found a small tiffin place serving amazing idli and chutney.",
            "5.0"
        )
        ReviewCard(
            "Rahul K.",
            "Nearby Market",
            "The local market had fresh vegetables and snacks.",
            "4.8"
        )
        ReviewCard(
            "Meghana S.",
            "Craft Store",
            "Loved the handmade products and local items.",
            "4.7"
        )
    }
}

@Composable
fun ReviewCard(user: String, place: String, review: String, rating: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "★ $rating",
                    fontSize = 14.sp,
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = place.uppercase(),
                fontSize = 12.sp,
                color = Color(0xFFD94A00),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
@Composable
fun AddReviewScreen(isKannada: Boolean) {
    var name by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var review by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        Text(
            text = if (isKannada) "ಪ್ರತಿಕ್ರಿಯೆ ಸಲ್ಲಿಸಿ" else "Submit Feedback",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD94A00)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Place Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = review,
            onValueChange = { review = it },
            label = { Text("Your Review") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD94A00)
            )
        ) {
            Text("Submit Feedback")
        }
    }
}