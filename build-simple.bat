@echo off
REM =====================================
REM CR3Converter Simple Build Script
REM Bypasses Gradle for Java 17+ compatibility
REM =====================================

echo.
echo =================================
echo    CR3Converter Build Script
echo =================================
echo.

REM Check Java installation
echo [1/5] Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ ERROR: Java is not installed or not in PATH
    echo Please install Java 8+ from: https://openjdk.java.net/
    pause
    exit /b 1
)

REM Display Java version
echo ✓ Java found:
java -version 2>&1 | findstr "version"

REM Create build directories
echo.
echo [2/5] Creating build directories...
if not exist "build\classes\java\main" mkdir build\classes\java\main >nul 2>&1
if not exist "build\libs" mkdir build\libs >nul 2>&1
echo ✓ Directories created

REM Compile Java source
echo.
echo [3/5] Compiling Java sources...
javac -d build\classes\java\main -sourcepath src\main\java src\main\java\com\converter\CR3Converter.java

if errorlevel 1 (
    echo ❌ Compilation failed!
    echo Check source code for errors.
    pause
    exit /b 1
)
echo ✓ Compilation successful

REM Create manifest
echo.
echo [4/5] Creating JAR manifest...
(
    echo Main-Class: com.converter.CR3Converter
    echo Implementation-Title: CR3 to JPEG Converter
    echo Implementation-Version: 1.0.0
    echo Implementation-Vendor: S7EREO-TYPE
    echo Built-By: %USERNAME%
    echo Build-Date: %DATE% %TIME%
    echo.
) > build\MANIFEST.MF
echo ✓ Manifest created

REM Create JAR
echo.
echo [5/5] Creating JAR file...

REM Try to create the standard JAR name
jar -cvfm build\libs\CR3Converter-all-1.0.0.jar build\MANIFEST.MF -C build\classes\java\main . >nul 2>&1

if errorlevel 1 (
    REM If that fails, create with timestamp
    echo ⚠️  Standard JAR name in use, creating timestamped version...
    set timestamp=%date:~-4,4%%date:~-10,2%%date:~-7,2%-%time:~0,2%%time:~3,2%
    set timestamp=%timestamp: =0%
    jar -cvfm build\libs\CR3Converter-%timestamp%.jar build\MANIFEST.MF -C build\classes\java\main . >nul
    if errorlevel 1 (
        echo ❌ JAR creation failed!
        pause
        exit /b 1
    )
    echo ✓ JAR created: CR3Converter-%timestamp%.jar
) else (
    echo ✓ JAR created: CR3Converter-all-1.0.0.jar
)

echo.
echo =================================
echo        BUILD SUCCESSFUL! ✅
echo =================================
echo.
echo 🎯 Your application is ready to run:
echo.
echo   Option 1: Double-click → CR3Converter.bat
echo   Option 2: PowerShell  → .\CR3Converter.ps1  
echo   Option 3: Direct JAR  → java -jar build\libs\CR3Converter-*.jar
echo.
echo 📁 Files created:
dir /b build\libs\*.jar 2>nul
echo.
echo 📚 Next steps:
echo   1. Test the application with sample CR3 files
echo   2. Configure LibRaw path if needed (see docs/usage.md)
echo   3. Create shortcuts for easy access
echo.
pause
