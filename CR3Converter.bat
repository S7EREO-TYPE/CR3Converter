@echo off
REM CR3Converter Launcher
REM This batch file launches the CR3Converter Java application

setlocal

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 8 or higher from https://www.java.com/
    pause
    exit /b 1
)

REM Get the directory where this batch file is located
set "APP_DIR=%~dp0"

REM Look for the JAR file
set "JAR_FILE="
if exist "%APP_DIR%CR3Converter-all-1.0.0.jar" (
    set "JAR_FILE=%APP_DIR%CR3Converter-all-1.0.0.jar"
) else if exist "%APP_DIR%build\libs\CR3Converter-all-1.0.0.jar" (
    set "JAR_FILE=%APP_DIR%build\libs\CR3Converter-all-1.0.0.jar"
) else if exist "%APP_DIR%build\libs\CR3Converter-1.0.0.jar" (
    set "JAR_FILE=%APP_DIR%build\libs\CR3Converter-1.0.0.jar"
) else (
    echo ERROR: CR3Converter JAR file not found
    echo Please build the project first using: gradlew build
    pause
    exit /b 1
)

REM Launch the application
echo Starting CR3Converter...
java -Djava.awt.headless=false -jar "%JAR_FILE%"

endlocal
