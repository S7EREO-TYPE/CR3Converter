# CR3Converter PowerShell Launcher
# This script launches the CR3Converter Java application

param(
    [switch]$Help
)

if ($Help) {
    Write-Host "CR3Converter Launcher"
    Write-Host "Usage: .\CR3Converter.ps1 [options]"
    Write-Host ""
    Write-Host "Options:"
    Write-Host "  -Help    Show this help message"
    exit 0
}

# Check if Java is available
try {
    $javaVersion = & java -version 2>&1 | Select-String "version"
    Write-Host "Found Java: $javaVersion"
} catch {
    Write-Error "Java is not installed or not in PATH. Please install Java 8 or higher."
    Read-Host "Press Enter to exit"
    exit 1
}

# Get script directory
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

# Look for JAR file
$jarFile = $null
$possibleJars = @(
    "$scriptDir\CR3Converter-all-1.0.0.jar",
    "$scriptDir\build\libs\CR3Converter-all-1.0.0.jar",
    "$scriptDir\build\libs\CR3Converter-1.0.0.jar"
)

foreach ($jar in $possibleJars) {
    if (Test-Path $jar) {
        $jarFile = $jar
        break
    }
}

if (-not $jarFile) {
    Write-Error "CR3Converter JAR file not found. Please build the project first using: .\gradlew.bat build"
    Read-Host "Press Enter to exit"
    exit 1
}

# Launch the application
Write-Host "Starting CR3Converter..."
Write-Host "JAR: $jarFile"

try {
    & java -Djava.awt.headless=false -jar $jarFile
} catch {
    Write-Error "Failed to start CR3Converter: $_"
    Read-Host "Press Enter to exit"
    exit 1
}
