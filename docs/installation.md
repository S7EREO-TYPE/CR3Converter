# Installation Instructions for CR3Converter

## Prerequisites

Before installing the CR3Converter application, ensure that you have the following prerequisites:

- **Java Development Kit (JDK)**: Make sure you have JDK 8 or higher installed on your machine. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) or use an open-source alternative like [OpenJDK](https://openjdk.java.net/).

- **Gradle**: The project uses Gradle for building. You can download and install Gradle from the [Gradle website](https://gradle.org/install/).

- **LibRaw**: The application requires LibRaw for processing CR3 files. Download the latest version from the [LibRaw website](https://www.libraw.org/download).

## Installation Steps

1. **Clone the Repository**:
   Open a terminal and clone the CR3Converter repository using the following command:
   ```
   git clone https://github.com/S7EREO-TYPE/CR3Converter.git
   ```

2. **Navigate to the Project Directory**:
   Change to the project directory:
   ```
   cd CR3Converter
   ```

3. **Install Dependencies**:
   Use Gradle to install the necessary dependencies. Run the following command:
   ```
   ./gradlew build
   ```

4. **Set Up LibRaw**:
   - Extract the downloaded LibRaw package to a directory of your choice.
   - Update the path in the `CR3Converter.java` file to point to the `dcraw_emu.exe` executable. This can be found in the `bin` directory of the extracted LibRaw package.

5. **Run the Application**:
   After building the project, you can run the application using the following command:
   ```
   ./gradlew run
   ```

## Additional Configuration

- If you want to customize the application further, you can modify the source code located in the `src/main/java/com/converter/` directory.

- Ensure that the `lib` and `resources/icons` directories are populated with any necessary files or icons you wish to include in your project.

## Troubleshooting

- If you encounter issues during installation or running the application, check the console output for error messages and ensure that all prerequisites are correctly installed.

- For further assistance, refer to the `usage.md` file in the `docs` directory for detailed usage instructions.