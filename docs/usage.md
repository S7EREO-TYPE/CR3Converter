# Usage Guide - CR3Converter

## 🚀 Quick Start

### 1. Launch the Application
```bash
# Double-click or run:
CR3Converter.bat

# Or use PowerShell:
.\CR3Converter.ps1
```

### 2. Basic Workflow
1. **📁 Select Folder** - Choose folder containing CR3 files
2. **⚙️ Adjust Settings** - Configure quality and brightness
3. **▶️ Start Conversion** - Click "Start Bulk Conversion"
4. **📊 Monitor Progress** - Watch progress bar and logs

## 🎛️ Interface Overview

### Main Controls
- **Select Folder**: Choose source directory with CR3 files
- **Quality Slider**: JPEG quality (50-100%)
- **Brightness Slider**: Image brightness (50-300%)
- **Recursive Checkbox**: Include subfolders
- **Start/Stop Button**: Begin or halt conversion process

### Information Display
- **File Count**: Shows detected CR3 files
- **Progress Bar**: Conversion progress indicator
- **Log Area**: Detailed processing information

## ⚙️ Settings & Options

### Quality Settings
- **Range**: 50% to 100%
- **Recommended**: 85-95% for best balance
- **Higher**: Better quality, larger file size
- **Lower**: Smaller files, reduced quality

### Brightness Control
- **Range**: 50% to 300%
- **Default**: 100% (no adjustment)
- **Darker**: Below 100%
- **Brighter**: Above 100%

### Processing Options
- **Recursive Scanning**: Process subfolders
- **Automatic Skip**: Skip existing JPEG files
- **Preserve Originals**: CR3 files remain untouched

## 🔄 Conversion Process

### File Processing Order
1. **Scan Directory** - Locate all CR3 files
2. **Check Existing** - Skip if JPEG already exists
3. **Primary Method** - LibRaw conversion
4. **Fallback Methods** - Alternative conversion approaches
5. **Quality Adjustment** - Apply brightness/quality settings
6. **Save JPEG** - Output to same directory as CR3

### Output Location
- JPEGs saved in same folder as source CR3 files
- Naming: `original_name.jpg` (replaces .cr3 extension)
- Existing files are automatically skipped

## 📊 Monitoring & Logs

### Progress Tracking
- **File Counter**: "Processing X of Y files"
- **Progress Bar**: Visual completion indicator
- **Success Rate**: Conversion statistics

### Log Messages
- ✅ **Success**: "✓ Converted: filename.cr3"
- ⏭️ **Skipped**: "⏭ Already exists: filename.jpg"
- ❌ **Failed**: "✗ Failed: filename.cr3 - error details"

## ❓ Troubleshooting

### Common Issues

**No CR3 Files Found**
- Verify folder contains .cr3 files
- Check "Recursive" option for subfolders
- Ensure files have .cr3 extension

**LibRaw Not Found**
- Install LibRaw from [libraw.org](https://www.libraw.org/)
- Update path in source code if needed
- Check PATH environment variable

**Conversion Failures**
- Verify CR3 file integrity
- Check disk space availability
- Ensure write permissions in target folder

**Java/Launch Issues**
```bash
# Check Java installation
java -version

# Test JAR directly
java -jar build/libs/CR3Converter-all-1.0.0.jar

# Check file permissions
dir CR3Converter.bat
```

## 🎯 Best Practices

### Performance Tips
- Process smaller batches for faster feedback
- Use SSD storage for better I/O performance
- Close other applications during large conversions

### Quality Guidelines
- **Web/Social**: 70-80% quality
- **Print/Archive**: 90-95% quality
- **Professional**: 95-100% quality

### File Management
- Keep CR3 originals as master files
- Organize output by date or project
- Use consistent naming conventions

## 🔧 Advanced Usage

### Batch Processing
- Select parent folder with multiple subfolders
- Enable "Recursive" for deep folder scanning
- Monitor logs for processing statistics

### Custom LibRaw Configuration
- Update LibRaw path in source code
- Recompile after path changes
- Test with sample files before batch processing

## Conclusion
The CR3Converter application provides a powerful and efficient way to convert CR3 files to JPEG format, making it an essential tool for photographers and digital artists. Enjoy seamless conversion with customizable settings to suit your needs!