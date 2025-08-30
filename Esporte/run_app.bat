@echo off
cd /d "C:\Users\combo\Desktop\PenaltiKicks\app"
echo Installing and running Penalty Kicks on connected device/emulator...
echo.

if not exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo APK not found. Building the app first...
    call gradlew assembleDebug
    if %errorlevel% neq 0 (
        echo Error during build!
        pause
        exit /b 1
    )
)

echo Installing APK...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %errorlevel% neq 0 (
    echo Error during installation!
    pause
    exit /b 1
)

echo.
echo Starting the app...
adb shell am start -n com.esportedasorte.esportefootbal/.MainActivity
if %errorlevel% neq 0 (
    echo Error starting the app!
    pause
    exit /b 1
)

echo.
echo App installed and started successfully!
echo.
pause