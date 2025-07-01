const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');
const chalk = require('chalk');

console.log(chalk.blue('Starting Android development watcher...'));
console.log(chalk.blue('Press Ctrl+C to stop'));

// Directories to watch
const javaDir = path.join(__dirname, '../app/src/main/java');
const layoutDir = path.join(__dirname, '../app/src/main/res/layout');
const drawableDir = path.join(__dirname, '../app/src/main/res/drawable');

// File extensions to watch
const extensions = ['.java', '.xml'];

// Last build timestamp
let lastBuildTime = Date.now();
let buildInProgress = false;

// Function to build and install the app
function buildAndInstall() {
  if (buildInProgress) {
    console.log(chalk.yellow('Build already in progress, skipping...'));
    return;
  }

  buildInProgress = true;
  console.log(chalk.blue('Building and installing app...'));

  // Build the app
  exec('gradlew.bat assembleDebug', (error, stdout, stderr) => {
    if (error) {
      console.log(chalk.red('Error: Gradle build failed'));
      console.error(stderr);
      buildInProgress = false;
      return;
    }

    // Install the app
    exec('adb install -r -t app/build/outputs/apk/debug/app-debug.apk', (error, stdout, stderr) => {
      if (error) {
        console.log(chalk.red('Error: APK installation failed'));
        console.error(stderr);
        buildInProgress = false;
        return;
      }

      // Restart the app
      exec('adb shell am force-stop com.example.fooddeli', () => {
        exec('adb shell am start -n com.example.fooddeli/.Activity.IntroActivity', () => {
          console.log(chalk.green('App successfully updated and restarted!'));
          lastBuildTime = Date.now();
          buildInProgress = false;
        });
      });
    });
  });
}

// Watch function for a directory
function watchDirectory(dir) {
  fs.watch(dir, { recursive: true }, (eventType, filename) => {
    if (!filename) return;
    
    // Check if the file extension is one we care about
    const ext = path.extname(filename);
    if (!extensions.includes(ext)) return;
    
    // Avoid duplicate builds
    const now = Date.now();
    if (now - lastBuildTime < 5000) return;
    
    console.log(chalk.yellow(`File changed: ${filename}`));
    buildAndInstall();
  });
}

// Start watching directories
try {
  watchDirectory(javaDir);
  console.log(chalk.green(`Watching Java files in ${javaDir}`));
  
  watchDirectory(layoutDir);
  console.log(chalk.green(`Watching layout files in ${layoutDir}`));
  
  watchDirectory(drawableDir);
  console.log(chalk.green(`Watching drawable files in ${drawableDir}`));
} catch (error) {
  console.log(chalk.red(`Error setting up watchers: ${error.message}`));
}

console.log(chalk.blue('Watcher started. Make changes to your code and the app will rebuild automatically.')); 