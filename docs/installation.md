# Installation Guide - CR3Converter

## 📋 Prerequisites

- **Java 8+** - [Download OpenJDK](https://openjdk.java.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- **LibRaw** - [Download LibRaw](https://www.libraw.org/download) for CR3 file processing
- **Git** - For cloning the repository

## 🚀 Quick Installation (Recommended)

### Step 1: Clone the Repository
```bash
git clone https://github.com/S7EREO-TYPE/CR3Converter.git
cd CR3Converter
```

### Step 2: Build the Application
```bash
# For Java 17+ (bypasses Gradle compatibility issues)
.\build-simple.bat

# For Java 8-16 (using Gradle)
.\gradlew.bat build    # Windows
./gradlew build        # Linux/macOS
```

### Step 3: Run the Application
```bash
# Easiest method - Double-click or run:
CR3Converter.bat

# Alternative methods:
.\CR3Converter.ps1                                    # PowerShell launcher
java -jar build/libs/CR3Converter-all-1.0.0.jar     # Direct JAR
```

## ⚙️ LibRaw Configuration

1. Download and extract LibRaw
2. Note the location of `dcraw_emu.exe`
3. Update the path in `src/main/java/com/converter/CR3Converter.java`:

```java
// Update this path to your LibRaw installation
private static final String LIBRAW_PATH = "C:\\path\\to\\libraw\\bin\\dcraw_emu.exe";
```

## 📂 File Structure After Installation

```
CR3Converter/
├── build/libs/CR3Converter-all-1.0.0.jar    # Executable JAR
├── CR3Converter.bat                          # Windows launcher
├── CR3Converter.ps1                          # PowerShell launcher
├── build-simple.bat                          # Simple build script
└── src/main/java/com/converter/              # Source code
```

## ❓ Troubleshooting

### Java Version Issues
- If using Java 17+, use `.\build-simple.bat`
- Check version with `java -version`

### LibRaw Setup
- Ensure `dcraw_emu.exe` is accessible
- Update path in `CR3Converter.java`
- Rebuild after path changes