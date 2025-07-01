@echo off
echo Starting Android development watcher...
echo Press Ctrl+C to stop

:loop
echo Building and installing app...
call ./gradlew assembleDebug
if %ERRORLEVEL% NEQ 0 (
    echo Build failed
    timeout /t 5
    goto loop
)

adb install -r -t app\build\outputs\apk\debug\app-debug.apk
if %ERRORLEVEL% NEQ 0 (
    echo Install failed
    timeout /t 5
    goto loop
)

echo App installed successfully. Waiting for changes...
echo Starting app...
adb shell am start -n com.example.fooddeli/.Activity.IntroActivity

timeout /t 10
goto loop 