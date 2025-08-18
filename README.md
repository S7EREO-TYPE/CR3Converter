# CR3 to JPEG Converter

A Java-based desktop application for bulk converting Canon Raw v3 (.cr3) files to high-quality JPEG images. Features adjustable quality/brightness, recursive folder scanning, progress tracking, and detailed logging with LibRaw integration for professional raw processing.

## ✨ Features

- 🔄 **Bulk Processing**: Convert multiple CR3 files in a single operation
- 📁 **Recursive Scanning**: Option to include subfolders when searching for CR3 files
- 🎛️ **Quality Control**: Adjustable JPEG quality settings (0-100%)
- 🌞 **Brightness Adjustment**: Fine-tune brightness of output images
- 📊 **Progress Tracking**: Real-time progress bar with file counts
- 📝 **Detailed Logging**: Comprehensive logging of conversion process
- ⚡ **Multiple Launchers**: Easy-to-use executable options

## 📋 Requirements

- **Java Development Kit (JDK) 8 or higher**
- **LibRaw** (dcraw_emu) for CR3 file support
- **Gradle** (optional - simple build script included for newer Java versions)

## 🚀 Quick Start

### 1. Clone & Setup
```bash
git clone https://github.com/S7EREO-TYPE/CR3Converter.git
cd CR3Converter
```

### 2. Build the Application
```bash
# Option A: Using Gradle (Java 8-16)
./gradlew build                    # Linux/macOS
.\gradlew.bat build               # Windows PowerShell
gradlew.bat build                 # Windows Command Prompt

# Option B: Simple Build (Java 17+, bypasses Gradle issues)
.\build-simple.bat                # Windows
```

### 3. Run the Application

**🎯 Easiest Methods:**
```bash
# Double-click or run:
CR3Converter.bat                  # Windows Batch Launcher

# PowerShell with error handling:
.\CR3Converter.ps1               # Windows PowerShell Launcher
```

**⚙️ Alternative Methods:**
```bash
# Direct JAR execution:
java -jar build/libs/CR3Converter-all-1.0.0.jar

# Using Gradle:
.\gradlew.bat run                # Windows
./gradlew run                    # Linux/macOS
```

## 🎯 How to Use

1. **Launch** the application using any method above
2. **Select Folder** containing your CR3 files
3. **Configure Settings**:
   - Adjust JPEG quality (0-100%)
   - Set brightness level
   - Enable recursive folder scanning if needed
4. **Start Conversion** and monitor progress
5. **Check Results** in the detailed log output

## 🔧 LibRaw Setup

The application uses LibRaw for CR3 processing. Update the LibRaw path in `CR3Converter.java` if needed:

```java
// Update this path to your LibRaw installation
private static final String LIBRAW_PATH = "C:\\path\\to\\libraw\\dcraw_emu.exe";
```

## 📁 Project Structure

```
CR3Converter/
├── src/main/java/com/converter/     # Java source code
├── docs/                            # Documentation
├── resources/icons/                 # Application icons
├── gradle/wrapper/                  # Gradle wrapper files
├── CR3Converter.bat                # Windows batch launcher
├── CR3Converter.ps1                # PowerShell launcher  
├── build-simple.bat               # Simple build script
├── build.gradle                    # Gradle build configuration
└── README.md                       # This file
```

## 🛠️ Development

### Building from Source
```bash
# Clone the repository
git clone https://github.com/S7EREO-TYPE/CR3Converter.git
cd CR3Converter

# Build with Gradle (Java 8-16)
./gradlew build

# Or use simple build (Java 17+)
./build-simple.bat  # Windows
```

### Creating Executables
- **Batch Launcher**: Ready to use - `CR3Converter.bat`
- **PowerShell Launcher**: Ready to use - `CR3Converter.ps1`
- **Native Executable**: Requires Java 14+ and jpackage support

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## 📞 Support

- 🐛 **Issues**: [GitHub Issues](https://github.com/S7EREO-TYPE/CR3Converter/issues)
- 📖 **Documentation**: See `/docs` folder for detailed guides
- 💡 **Feature Requests**: Open an issue with the "enhancement" label