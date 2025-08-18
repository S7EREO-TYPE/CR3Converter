# Usage Instructions for CR3Converter

## Overview
The CR3Converter application is a user-friendly tool designed to convert Canon Raw v3 (.cr3) files into high-quality JPEG images. This application supports bulk processing and offers various customization options for the conversion process.

## Features
- **Bulk Conversion**: Select a folder containing multiple CR3 files and convert them all to JPEG format in one go.
- **Recursive Scanning**: Optionally include subfolders in the search for CR3 files.
- **Quality Control**: Adjust the JPEG quality using a slider, with a range from 50% to 100%.
- **Brightness Adjustment**: Modify the brightness of the output JPEG images with a slider, allowing values from 50% to 300%.
- **Logging**: View detailed logs of the conversion process, including success and failure messages.

## Getting Started
1. **Select Folder**: Click the "Select Folder" button to choose the directory containing your CR3 files. The application will scan the selected folder and its subfolders (if enabled) for CR3 files.
2. **Adjust Settings**: Use the quality and brightness sliders to set your desired output parameters.
3. **Start Conversion**: Once the files are detected, click the "Start Bulk Conversion" button to begin the conversion process. The progress will be displayed in the progress bar, along with logs in the text area.

## Conversion Process
- The application will create JPEG files in the same directory as the original CR3 files.
- If a JPEG file already exists for a CR3 file, the application will skip the conversion for that file and log a message.
- The conversion process will utilize various methods to ensure the best quality output, including LibRaw and ImageIO.

## Notes
- Ensure that LibRaw is installed and correctly configured on your system, as it is required for the conversion of CR3 files.
- The application is designed to preserve the original CR3 files during the conversion process.

## Troubleshooting
- If you encounter issues with the conversion, check the logs for error messages that may indicate the cause of the problem.
- Make sure that the selected folder contains valid CR3 files and that you have the necessary permissions to read and write files in that directory.

## Conclusion
The CR3Converter application provides a powerful and efficient way to convert CR3 files to JPEG format, making it an essential tool for photographers and digital artists. Enjoy seamless conversion with customizable settings to suit your needs!