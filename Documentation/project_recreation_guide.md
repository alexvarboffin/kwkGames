# Руководство по воссозданию проекта: TacticField

## Обзор проекта

Этот проект представляет собой тактическую доску для футбольного менеджера под названием "PXB Football". Приложение позволяет пользователям управлять расстановкой игроков на поле, изменять тактические схемы и стратегии, прокачивать характеристики игроков и сохранять свой прогресс. Приложение разработано на Kotlin с использованием Jetpack Compose для пользовательского интерфейса.

**Ключевые функции:**
- Анимированный стартовый экран.
- Главное меню с навигацией.
- Экран выбора уровней с механикой разблокировки и звезд.
- Тактическое поле с сеткой 9x20.
- Перемещение игроков с ограничением по дальности (5 клеток).
- 4 хода на раунд для расстановки.
- Выбор формаций (4-4-2, 4-3-3, 5-3-2) с привязкой к сетке.
- Выбор стратегических пресетов ("Высокий прессинг", "Автобус").
- Сохранение и загрузка расстановки и прогресса игроков.
- Кастомизация внешнего вида (цвета, иконки).

**Технологии:**
- **Язык:** Kotlin
- **UI:** Jetpack Compose
- **Навигация:** Navigation Compose
- **Сборка:** Gradle

## Начальная настройка

Проект создается как стандартное Android-приложение с поддержкой Jetpack Compose.

## Конфигурационные файлы

### `TacticField/build.gradle.kts`

```kotlin
plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mostbet.cricmost.tacticfield"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
```

### `TacticField/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" />
```

## Структура проекта

```
TacticField/
├── build.gradle.kts
├── proguard-rules.pro
└── src/
    └── main/
        ├── AndroidManifest.xml
        ├── java/
        │   └── com/
        │       └── mostbet/
        │           └── cricmost/
        │               ├── GameActivity.kt
        │               ├── GameScreen.kt
        │               ├── MainActivity.kt
        │               ├── SplashScreenActivity.kt
        │               └── ui/
        │                   └── theme/
        │                       ├── Color.kt
        │                       ├── Theme.kt
        │                       └── Typography.kt
        └── res/
            └── values/
                ├── colors.xml
                ├── strings.xml
                └── themes.xml
```

## Полный исходный код

### `TacticField/src/main/res/values/strings.xml`

```xml
<resources>
    <string name="app_name">``</string>
</resources>
```

### `TacticField/src/main/res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="orange_500">#FFA500</color>
    <color name="orange_700">#FF8C00</color>
    <color name="blue_500">#03A9F4</color>
    <color name="blue_700">#0288D1</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
</resources>
```

### `TacticField/src/main/res/values/themes.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.PXBFootball" parent="android:Theme.Material.Light.NoActionBar">
        <item name="android:colorPrimary">@color/orange_500</item>
        <item name="android:colorPrimaryDark">@color/orange_700</item>
        <item name="android:colorAccent">@color/blue_500</item>
    </style>
</resources>
```

### `TacticField/src/main/java/com/mostbet/cricmost/ui/theme/Color.kt`

```kotlin
package com.mostbet.cricmost.ui.theme

import androidx.compose.ui.graphics.Color

val Orange = Color(0xFFFFA500)
val DarkOrange = Color(0xFFFF8C00)
val Blue = Color(0xFF03A9F4)
val DarkBlue = Color(0xFF0288D1)
```

### `TacticField/src/main/java/com/mostbet/cricmost/ui/theme/Typography.kt`

```kotlin
package com.mostbet.cricmost.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
```

### `TacticField/src/main/java/com/mostbet/cricmost/ui/theme/Theme.kt`

```kotlin
package com.mostbet.cricmost.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkOrange,
    secondary = DarkBlue,
    background = Color(0xFF1A222C),
    surface = Color(0xFF1A222C),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    secondary = Blue,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun PXBFootballTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

### `TacticField/src/main/java/com/mostbet/cricmost/SplashScreenActivity.kt`

```kotlin
package com.mostbet.cricmost

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostbet.cricmost.ui.theme.PXBFootballTheme

class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PXBFootballTheme {
                SplashScreen(
                    onScreenClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreen(onScreenClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "pulseAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0288D1)) // Blue background
            .clickable { onScreenClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PXB Football",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.scale(scale)
            )
            Text(
                text = "Click to start",
                fontSize = 24.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}
```

### `TacticField/src/main/java/com/mostbet/cricmost/MainActivity.kt`

```kotlin
package com.mostbet.cricmost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mostbet.cricmost.ui.theme.PXBFootballTheme

const val PREFS_NAME_GAME = "PXBGamePrefs"
const val KEY_UNLOCKED_LEVEL = "unlockedLevel"
const val KEY_STARS = "stars"

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object LevelSelect : Screen("level_select")
    object TacticField : Screen("tactic_field/{level}") {
        fun createRoute(level: Int) = "tactic_field/$level"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PXBFootballTheme {
                AppNavigation()
            }
        }
    }
}

fun openUrlInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController = navController)
        }
        composable(Screen.LevelSelect.route) {
            LevelSelectScreen(onLevelClick = { level ->
                navController.navigate(Screen.TacticField.createRoute(level))
            })
        }
        composable(
            route = Screen.TacticField.route,
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1
            GameScreen(
                isEndlessMode = false,
                level = level,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0288D1)) // Blue background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PXB Football",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(64.dp))

        PremiumButton(text = "Go to Game") { navController.navigate(Screen.TacticField.createRoute(1)) }
        Spacer(modifier = Modifier.height(16.dp))
        PremiumButton(text = "Select Level") { navController.navigate(Screen.LevelSelect.route) }
        Spacer(modifier = Modifier.height(16.dp))
        PremiumButton(text = "Privacy Policy") { openUrlInCustomTab(context, "https://www.google.com") }
        Spacer(modifier = Modifier.height(16.dp))
        PremiumButton(text = "FAQ") { openUrlInCustomTab(context, "https://www.google.com") }
    }
}

@Composable
fun PremiumButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(0.8f),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFA500), Color(0xFFFF8C00))
                    )
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun LevelSelectScreen(onLevelClick: (Int) -> Unit) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences(PREFS_NAME_GAME, Context.MODE_PRIVATE)
    val unlockedLevel by remember { mutableStateOf(sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 1)) }
    val starsData = sharedPrefs.getString(KEY_STARS, "") ?: ""
    val starsMap = remember {
        starsData.split(",").filter { it.isNotEmpty() }.associate {
            val (level, stars) = it.split(":")
            level.toInt() to stars.toInt()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0288D1), Color(0xFF00008B))))
            .padding(16.dp)
    ) {
        Text(
            text = "Select Level",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 32.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(30) { levelIndex ->
                val level = levelIndex + 1
                val isLocked = level > unlockedLevel
                val stars = starsMap[level] ?: 0
                LevelButton(
                    level = level,
                    isLocked = isLocked,
                    stars = stars,
                    onClick = { onLevelClick(level) }
                )
            }
        }
    }
}

@Composable
fun LevelButton(level: Int, isLocked: Boolean, stars: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.alpha(if (isLocked) 0.5f else 1f)
    ) {
        Button(
            onClick = onClick,
            enabled = !isLocked,
            shape = CircleShape,
            modifier = Modifier.size(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isLocked) {
                    Text(text = "🔒", fontSize = 32.sp)
                } else {
                    Text(
                        text = level.toString(),
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(top = 4.dp)) {
            repeat(3) { index ->
                Text(text = if (index < stars) "⭐" else "☆", fontSize = 24.sp)
            }
        }
    }
}
```

### `TacticField/src/main/java/com/mostbet/cricmost/GameActivity.kt`

```kotlin
package com.mostbet.cricmost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mostbet.cricmost.ui.theme.PXBFootballTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val level = intent.getIntExtra("level", 1)
        setContent {
            PXBFootballTheme {
                GameScreen(isEndlessMode = false, level = level, onBack = { finish() })
            }
        }
    }
}
```

### `TacticField/src/main/java/com/mostbet/cricmost/GameScreen.kt`

```kotlin
package com.mostbet.cricmost

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class Player(
    val id: Int,
    var name: String,
    var level: Int,
    var stats: MutableState<PlayerStats>,
    var position: MutableState<Pair<Float, Float>>
)

data class PlayerStats(
    var strength: Int,
    var dexterity: Int,
    var stamina: Int
)

object Grid {
    const val PADDING_HORIZONTAL = 0.05f
    const val PADDING_VERTICAL = 0.05f
    const val GRID_WIDTH = 9
    const val GRID_HEIGHT = 20

    val points: List<Pair<Float, Float>> = (0 until GRID_WIDTH).flatMap { x ->
        (0 until GRID_HEIGHT).map { y ->
            val gridX = (1 - PADDING_HORIZONTAL * 2) / (GRID_WIDTH - 1) * x + PADDING_HORIZONTAL
            val gridY = (1 - PADDING_VERTICAL * 2) / (GRID_HEIGHT - 1) * y + PADDING_VERTICAL
            gridX to gridY
        }
    }

    fun positionToGridCoords(position: Pair<Float, Float>): Pair<Int, Int> {
        val x = ((position.first - PADDING_HORIZONTAL) / (1 - PADDING_HORIZONTAL * 2) * (GRID_WIDTH - 1)).roundToInt()
        val y = ((position.second - PADDING_VERTICAL) / (1 - PADDING_VERTICAL * 2) * (GRID_HEIGHT - 1)).roundToInt()
        return x to y
    }

    fun gridDistance(start: Pair<Float, Float>, end: Pair<Float, Float>): Int {
        val startCoords = positionToGridCoords(start)
        val endCoords = positionToGridCoords(end)
        return max(abs(startCoords.first - endCoords.first), abs(startCoords.second - endCoords.second))
    }
}

object Formations {
    // Idealized positions, will be snapped to the nearest grid point
    val formations = mapOf(
        "4-4-2" to listOf(
            0.5f to 0.1f, // GK
            0.2f to 0.25f, 0.4f to 0.25f, 0.6f to 0.25f, 0.8f to 0.25f, // Def
            0.2f to 0.5f, 0.4f to 0.5f, 0.6f to 0.5f, 0.8f to 0.5f, // Mid
            0.4f to 0.75f, 0.6f to 0.75f // Fwd
        ),
        "4-3-3" to listOf(
            0.5f to 0.1f, // GK
            0.2f to 0.25f, 0.4f to 0.25f, 0.6f to 0.25f, 0.8f to 0.25f, // Def
            0.3f to 0.5f, 0.5f to 0.5f, 0.7f to 0.5f, // Mid
            0.3f to 0.75f, 0.5f to 0.75f, 0.7f to 0.75f // Fwd
        ),
        "5-3-2" to listOf(
            0.5f to 0.1f, // GK
            0.15f to 0.25f, 0.3f to 0.25f, 0.5f to 0.25f, 0.7f to 0.25f, 0.85f to 0.25f, // Def
            0.3f to 0.5f, 0.5f to 0.5f, 0.7f to 0.5f, // Mid
            0.4f to 0.75f, 0.6f to 0.75f // Fwd
        )
    )
}

object Strategies {
    val presets = mapOf(
        "High Press" to listOf(
            0.5f to 0.1f, // GK
            0.2f to 0.4f, 0.4f to 0.4f, 0.6f to 0.4f, 0.8f to 0.4f, // High Def
            0.2f to 0.6f, 0.4f to 0.6f, 0.6f to 0.6f, 0.8f to 0.6f, // High Mid
            0.4f to 0.8f, 0.6f to 0.8f // High Fwd
        ),
        "Park the Bus" to listOf(
            0.5f to 0.1f, // GK
            0.15f to 0.2f, 0.3f to 0.2f, 0.5f to 0.2f, 0.7f to 0.2f, 0.85f to 0.2f, // Deep Def
            0.3f to 0.35f, 0.5f to 0.35f, 0.7f to 0.35f, // Deep Mid
            0.4f to 0.5f, 0.6f to 0.5f // Deep Fwd
        )
    )
}

fun findNearestGridPoint(point: Pair<Float, Float>, grid: List<Pair<Float, Float>>, occupied: Set<Pair<Float, Float>>): Pair<Float, Float> {
    return grid.filterNot { occupied.contains(it) }
        .minByOrNull { (gridX, gridY) ->
            sqrt((gridX - point.first).pow(2) + (gridY - point.second).pow(2))
        } ?: point // Fallback to original point if grid is full
}

fun getInitialPlayers(formationName: String = "4-4-2"): List<Player> {
    val idealPositions = Formations.formations[formationName]!!
    val occupied = mutableSetOf<Pair<Float, Float>>()
    val snappedPositions = idealPositions.map { idealPos ->
        val snapped = findNearestGridPoint(idealPos, Grid.points, occupied)
        occupied.add(snapped)
        snapped
    }

    return (1..11).map { i ->
        Player(
            id = i,
            name = "Player $i",
            level = 1,
            stats = mutableStateOf(PlayerStats(10, 10, 10)),
            position = mutableStateOf(snappedPositions[i - 1])
        )
    }
}

const val PREFS_NAME = "TacticFieldPrefs"
const val PLAYERS_DATA_KEY = "playersData"

fun savePlayersData(context: Context, players: List<Player>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
    val dataString = players.joinToString(";") { p ->
        "${p.id},${p.name},${p.level},${p.stats.value.strength},${p.stats.value.dexterity},${p.stats.value.stamina},${p.position.value.first},${p.position.value.second}"
    }
    prefs.putString(PLAYERS_DATA_KEY, dataString)
    prefs.apply()
}

fun loadPlayersData(context: Context): List<Player> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val dataString = prefs.getString(PLAYERS_DATA_KEY, null)
    if (dataString.isNullOrEmpty()) {
        return getInitialPlayers()
    }
    return try {
        dataString.split(";").map { playerDataString ->
            val parts = playerDataString.split(",")
            Player(
                id = parts[0].toInt(),
                name = parts[1],
                level = parts[2].toInt(),
                stats = mutableStateOf(
                    PlayerStats(
                        strength = parts[3].toInt(),
                        dexterity = parts[4].toInt(),
                        stamina = parts[5].toInt()
                    )
                ),
                position = mutableStateOf(parts[6].toFloat() to parts[7].toFloat())
            )
        }
    } catch (e: Exception) {
        getInitialPlayers()
    }
}

@Composable
fun GameScreen(isEndlessMode: Boolean, level: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    val players = remember { mutableStateListOf(*loadPlayersData(context).toTypedArray()) }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    var formationDropdownExpanded by remember { mutableStateOf(false) }
    var strategyDropdownExpanded by remember { mutableStateOf(false) }
    var fieldSize by remember { mutableStateOf(IntSize.Zero) }
    var movesRemaining by remember { mutableStateOf(4) }
    var turnStartPosition by remember { mutableStateOf<List<Pair<Float, Float>>>(emptyList()) }

    LaunchedEffect(players.size) {
        turnStartPosition = players.map { it.position.value }
    }

    fun resetTurn() {
        players.forEachIndexed { index, player ->
            player.position.value = turnStartPosition[index]
        }
        movesRemaining = 4
    }

    fun applyPositions(idealPositions: List<Pair<Float, Float>>) {
        val occupied = mutableSetOf<Pair<Float, Float>>()
        val snappedPositions = idealPositions.map { idealPos ->
            val snapped = findNearestGridPoint(idealPos, Grid.points, occupied)
            occupied.add(snapped)
            snapped
        }
        players.forEachIndexed { index, player ->
            player.position.value = snappedPositions.getOrElse(index) { 0.5f to 0.5f }
        }
        turnStartPosition = players.map { it.position.value }
        movesRemaining = 4
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Buttons
        Column(modifier = Modifier.background(Color(0xFF1A222C))) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63))
                Button(onClick = { savePlayersData(context, players) }, colors = buttonColors) {
                    Text("Save")
                }
                Button(onClick = { resetTurn() }, colors = buttonColors) {
                    Text("Reset Turn")
                }
                Button(onClick = {
                    val prefs = context.getSharedPreferences(PREFS_NAME_GAME, Context.MODE_PRIVATE)
                    val unlockedLevel = prefs.getInt(KEY_UNLOCKED_LEVEL, 1)
                    if (level == unlockedLevel) {
                        prefs.edit().putInt(KEY_UNLOCKED_LEVEL, unlockedLevel + 1).apply()
                    }
                    val starsData = prefs.getString(KEY_STARS, "") ?: ""
                    val newStars = starsData.split(",").filter { it.isNotEmpty() }.toMutableList()
                    newStars.removeAll { it.startsWith("$level:") }
                    newStars.add("$level:3")
                    prefs.edit().putString(KEY_STARS, newStars.joinToString(",")).apply()
                    onBack()
                }, colors = buttonColors) {
                    Text("Win Level")
                }
            }
            Row(
                 modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63))
                Box {
                    Button(onClick = { formationDropdownExpanded = true }, colors = buttonColors) {
                        Text("Formation")
                    }
                    DropdownMenu(
                        expanded = formationDropdownExpanded,
                        onDismissRequest = { formationDropdownExpanded = false }
                    ) {
                        Formations.formations.keys.forEach { formationName ->
                            DropdownMenuItem(text = { Text(formationName) }, onClick = {
                                applyPositions(Formations.formations[formationName]!!)
                                formationDropdownExpanded = false
                            })
                        }
                    }
                }
                Box {
                    Button(onClick = { strategyDropdownExpanded = true }, colors = buttonColors) {
                        Text("Strategy")
                    }
                    DropdownMenu(
                        expanded = strategyDropdownExpanded,
                        onDismissRequest = { strategyDropdownExpanded = false }
                    ) {
                        Strategies.presets.keys.forEach { strategyName ->
                            DropdownMenuItem(text = { Text(strategyName) }, onClick = {
                                applyPositions(Strategies.presets[strategyName]!!)
                                strategyDropdownExpanded = false
                            })
                        }
                    }
                }
            }
            Text(
                text = "Moves Remaining: $movesRemaining",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
            )
        }

        // Field
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { fieldSize = it }
        ) {
            val fieldWidth = fieldSize.width.toFloat()
            val fieldHeight = fieldSize.height.toFloat()

            // Striped background
            Row(modifier = Modifier.fillMaxSize()) {
                val stripeColor1 = Color(0xFF3A8A53)
                val stripeColor2 = Color(0xFF36814E)
                repeat(10) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(if (it % 2 == 0) stripeColor1 else stripeColor2))
                }
            }

            // Field Markings
            FieldMarkings()

            if (fieldSize != IntSize.Zero) {
                FormationMarkers(Grid.points, fieldWidth, fieldHeight)

                players.forEach { player ->
                    PlayerDraggable(
                        player = player,
                        allPlayers = players,
                        fieldWidth = fieldWidth,
                        fieldHeight = fieldHeight,
                        onPlayerClick = { selectedPlayer = it },
                        movesRemaining = movesRemaining,
                        onMove = { movesRemaining-- }
                    )
                }
            }

            if (selectedPlayer != null) {
                PlayerDetailsDialog(
                    player = selectedPlayer!!,
                    onDismiss = { selectedPlayer = null },
                    onStatIncrease = { stat ->
                        val currentStats = selectedPlayer!!.stats.value
                        when (stat) {
                            "Strength" -> selectedPlayer!!.stats.value = currentStats.copy(strength = currentStats.strength + 1)
                            "Dexterity" -> selectedPlayer!!.stats.value = currentStats.copy(dexterity = currentStats.dexterity + 1)
                            "Stamina" -> selectedPlayer!!.stats.value = currentStats.copy(stamina = currentStats.stamina + 1)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FormationMarkers(positions: List<Pair<Float, Float>>, fieldWidth: Float, fieldHeight: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        positions.forEach { position ->
            val x = position.first * fieldWidth
            val y = position.second * fieldHeight
            val markerSize = 10.dp.toPx()

            drawLine(
                color = Color.White.copy(alpha = 0.3f),
                start = Offset(x - markerSize, y - markerSize),
                end = Offset(x + markerSize, y + markerSize),
                strokeWidth = 2.dp.toPx()
            )
            drawLine(
                color = Color.White.copy(alpha = 0.3f),
                start = Offset(x - markerSize, y + markerSize),
                end = Offset(x + markerSize, y - markerSize),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

@Composable
fun FieldMarkings() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Center Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.White.copy(alpha = 0.5f))
                .align(Alignment.Center)
        )
        // Center Circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)), CircleShape)
                .align(Alignment.Center)
        )
        // Penalty Box Top
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
                .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)))
                .align(Alignment.TopCenter)
        )
        // Penalty Box Bottom
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
                .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)))
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PlayerDraggable(
    player: Player,
    allPlayers: List<Player>,
    fieldWidth: Float,
    fieldHeight: Float,
    onPlayerClick: (Player) -> Unit,
    movesRemaining: Int,
    onMove: () -> Unit
) {
    val playerSizeDp = 60.dp
    val playerRadiusPx = with(LocalDensity.current) { playerSizeDp.toPx() / 2 }
    var initialPosition by remember { mutableStateOf(player.position.value) }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    (player.position.value.first * fieldWidth - playerRadiusPx).roundToInt(),
                    (player.position.value.second * fieldHeight - playerRadiusPx).roundToInt()
                )
            }
            .size(playerSizeDp)
            .clip(CircleShape)
            .background(Brush.radialGradient(listOf(Color(0xFFD75A23), Color(0xFFB04A1A))))
            .border(BorderStroke(2.dp, Color(0xFFF08A5A)), CircleShape)
            .pointerInput(movesRemaining) {
                if (movesRemaining > 0) {
                    detectDragGestures(
                        onDragStart = { initialPosition = player.position.value },
                        onDragEnd = {
                            val currentPositions = allPlayers.map { it.position.value }.toSet()
                            val nearestPoint = findNearestGridPoint(player.position.value, Grid.points, currentPositions - initialPosition)
                            val distance = Grid.gridDistance(initialPosition, nearestPoint)

                            if (distance <= 5 && nearestPoint != initialPosition) {
                                player.position.value = nearestPoint
                                onMove()
                            } else {
                                player.position.value = initialPosition // Snap back if invalid move
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            player.position.value = Pair(
                                (player.position.value.first * fieldWidth + dragAmount.x) / fieldWidth,
                                (player.position.value.second * fieldHeight + dragAmount.y) / fieldHeight
                            )
                        }
                    )
                }
            }
            .clickable { onPlayerClick(player) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Player Icon",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(text = player.name, color = Color.White, fontSize = 10.sp)
        }
    }
}

@Composable
fun PlayerDetailsDialog(player: Player, onDismiss: () -> Unit, onStatIncrease: (String) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A222C))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = player.name, color = Color.White, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
                Text(text = "Level: ${player.level}", color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Stats", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                StatRow("Strength", player.stats.value.strength) { onStatIncrease("Strength") }
                StatRow("Dexterity", player.stats.value.dexterity) { onStatIncrease("Dexterity") }
                StatRow("Stamina", player.stats.value.stamina) { onStatIncrease("Stamina") }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63))
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun StatRow(name: String, value: Int, onIncrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$name: $value", color = Color.White)
        Button(
            onClick = onIncrease,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63)),
            shape = CircleShape,
            modifier = Modifier.size(32.dp)
        ) {
            Text(text = "+")
        }
    }
}
