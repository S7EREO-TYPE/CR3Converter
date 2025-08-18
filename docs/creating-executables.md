# How to Create Executables for CR3Converter

## Windows Executable Options

### 1. Native Windows Executable (.exe) with jpackage
The best option for creating a native Windows executable. This requires Java 14+ for the build process.

**Steps:**
1. Build the project: `gradlew.bat build`
2. Create executable: `gradlew.bat jpackage`
3. Find your executable in: `build/jpackage/CR3Converter/bin/CR3Converter.exe`
4. Find installer in: `build/jpackage/CR3Converter-Installer.msi`

**Benefits:**
- Native Windows executable
- No Java required on target machine (bundles JRE)
- Professional installer (.msi)
- Windows Start Menu integration
- Desktop shortcuts

### 2. Batch File Launcher (CR3Converter.bat)
Simple launcher that requires Java to be installed on the target machine.

**Usage:**
- Double-click `CR3Converter.bat`
- Automatically finds and launches the JAR file
- Requires Java 8+ installed

### 3. PowerShell Launcher (CR3Converter.ps1)
More robust launcher with better error handling.

**Usage:**
- Right-click → "Run with PowerShell"
- Or from command line: `.\CR3Converter.ps1`
- Includes help: `.\CR3Converter.ps1 -Help`

### 4. JAR to EXE Converters (Third-party)
You can also use tools like:
- **Launch4j** (Free) - Creates lightweight .exe wrapper
- **exe4j** (Commercial) - Professional native launcher
- **jpackage** (Built into Java 14+) - Official Oracle solution

## Adding an Icon

To add a custom icon to your executable:

1. **For jpackage:** Place icon files in `resources/icons/`
   - Windows: `app-icon.ico`
   - macOS: `app-icon.icns` 
   - Linux: `app-icon.png`

2. **For Launch4j:** Use any .ico file

## Distribution

Once created, you can distribute:
- **Standalone executable** - Single .exe file (requires bundled JRE)
- **Installer** - .msi file for professional installation
- **Portable** - Folder with executable and launcher scripts
