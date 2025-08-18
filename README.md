# CR3 to JPEG Converter

## Overview
The CR3 to JPEG Converter is a Java-based Swing application designed to convert Canon Raw v3 (.cr3) files into high-quality JPEG images. This application supports bulk processing, allowing users to convert multiple CR3 files at once, and includes options for adjusting JPEG quality and brightness.

## Features
- **Bulk Processing**: Convert multiple CR3 files in a single operation.
- **Recursive Folder Scanning**: Option to include subfolders when searching for CR3 files.
- **Quality Control**: Adjustable JPEG quality settings.
- **Brightness Adjustment**: Control over the brightness of the output JPEG images.
- **Log Output**: Detailed logging of the conversion process, including success and failure messages.

## Requirements
- Java Development Kit (JDK) 8 or higher
- LibRaw (dcraw_emu) for CR3 file support
- Gradle for building the project

## Installation
1. Clone the repository:
   ```
   git clone https://github.com/S7EREO-TYPE/CR3Converter.git
   ```
2. Navigate to the project directory:
   ```
   cd CR3Converter
   ```
3. Ensure that LibRaw is installed and the path is correctly set in the `CR3Converter.java` file.
4. Build the project using Gradle:
   ```bash
   # On Linux/macOS:
   ./gradlew build
   
   # On Windows:
   gradlew.bat build
   ```

## Usage
1. Run the application:
   ```bash
   # Using the built JAR:
   java -jar build/libs/CR3Converter-all-1.0.0.jar
   
   # Or using Gradle:
   # Linux/macOS:
   ./gradlew run
   
   # Windows:
   gradlew.bat run
   ```
2. Select the folder containing the CR3 files you wish to convert.
3. Adjust the quality and brightness settings as desired.
4. Click on "Start Bulk Conversion" to begin the conversion process.
5. Monitor the log area for updates on the conversion status.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any suggestions or improvements.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.