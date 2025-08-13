@echo off
echo Building SmartSolar APK...
echo.

REM Clean previous build
echo Cleaning previous build...
call gradlew clean

REM Build debug APK
echo Building debug APK...
call gradlew assembleDebug

REM Check if build was successful
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build successful!
    echo APK location: app\build\outputs\apk\debug\app-debug.apk
    
    REM Copy to releases folder with timestamp
    set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%
    set TIMESTAMP=%TIMESTAMP: =0%
    
    echo Copying APK to APK releases folder...
    copy "app\build\outputs\apk\debug\app-debug.apk" "APK releases\SmartSolar-v1.0-debug-%TIMESTAMP%.apk"
    
    echo.
    echo APK copied to: APK releases\SmartSolar-v1.0-debug-%TIMESTAMP%.apk
    echo.
    echo You can now:
    echo 1. Install the APK on your device
    echo 2. Upload it to GitHub releases
    echo 3. Share it with others
) else (
    echo.
    echo Build failed! Check the error messages above.
)

echo.
pause
