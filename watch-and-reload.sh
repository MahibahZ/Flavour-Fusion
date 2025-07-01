#!/bin/bash
echo "Starting Android development watcher..."
echo "Press Ctrl+C to stop"

while true; do
  echo "Building and installing app..."
  ./gradlew assembleDebug
  
  if [ $? -ne 0 ]; then
    echo "Build failed"
    sleep 5
    continue
  fi
  
  adb install -r -t app/build/outputs/apk/debug/app-debug.apk
  
  if [ $? -ne 0 ]; then
    echo "Install failed"
    sleep 5
    continue
  fi
  
  echo "App installed successfully. Waiting for changes..."
  echo "Starting app..."
  adb shell am start -n com.example.fooddeli/.Activity.IntroActivity
  
  sleep 10
done 