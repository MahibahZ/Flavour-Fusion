const shell = require('shelljs');
const chalk = require('chalk');
const path = require('path');

console.log(chalk.blue('Building and installing app...'));

// Build the app
console.log(chalk.yellow('Building debug APK...'));
if (shell.exec('gradlew.bat assembleDebug').code !== 0) {
  console.log(chalk.red('Error: Gradle build failed'));
  process.exit(1);
}

// Install the app
console.log(chalk.yellow('Installing APK...'));
if (shell.exec('adb install -r -t app/build/outputs/apk/debug/app-debug.apk').code !== 0) {
  console.log(chalk.red('Error: APK installation failed'));
  process.exit(1);
}

// Restart the app
console.log(chalk.yellow('Restarting app...'));
shell.exec('adb shell am force-stop com.example.fooddeli');
shell.exec('adb shell am start -n com.example.fooddeli/.Activity.IntroActivity');

console.log(chalk.green('App successfully updated and restarted!'));
console.log(chalk.blue('Waiting for file changes...')); 