@echo off
cd /d "C:\Users\combo\Desktop\PenaltiKicks\app"
echo Building Penalty Kicks Android App...
echo.

if not exist "gradlew.bat" (
    echo Error: gradlew.bat not found!
    echo Please make sure you're in the correct directory.
    pause
    exit /b 1
)

echo Cleaning project...
call gradlew clean
if %errorlevel% neq 0 (
    echo Error during clean!
    pause
    exit /b 1
)

echo Building debug APK...
call gradlew assembleDebug
if %errorlevel% neq 0 (
    echo Error during build!
    pause
    exit /b 1
)

echo.
echo Build successful!
echo APK location: app\build\outputs\apk\debug\app-debug.apk
echo.
echo To install on a connected device, run:
echo adb install app\build\outputs\apk\debug\app-debug.apk
echo.
pause