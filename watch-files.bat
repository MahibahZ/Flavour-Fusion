@echo off
echo Starting file watcher for Android development...
echo Press Ctrl+C to stop

set LAST_BUILD_TIME=0

:loop
set CURRENT_TIME=%time%

:: Check for changes in Java files
for /r %%f in (app\src\main\java\*.java) do (
    if %%~tf gtr %LAST_BUILD_TIME% (
        echo Change detected in %%f
        goto build
    )
)

:: Check for changes in XML layout files
for /r %%f in (app\src\main\res\layout\*.xml) do (
    if %%~tf gtr %LAST_BUILD_TIME% (
        echo Change detected in %%f
        goto build
    )
)

:: Check for changes in drawable files
for /r %%f in (app\src\main\res\drawable\*.xml) do (
    if %%~tf gtr %LAST_BUILD_TIME% (
        echo Change detected in %%f
        goto build
    )
)

timeout /t 2 /nobreak > nul
goto loop

:build
echo Building and installing app...
set LAST_BUILD_TIME=%time%

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

echo App installed successfully. Restarting app...
adb shell am force-stop com.example.fooddeli
adb shell am start -n com.example.fooddeli/.Activity.IntroActivity

timeout /t 5
goto loop 