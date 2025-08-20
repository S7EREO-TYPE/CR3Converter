package com.converter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * CR3 to JPEG Converter Application
 * Converts Canon Raw v3 (.cr3) files to high-quality JPEG files with bulk processing support
 */
public class CR3Converter extends JFrame {
    private JTextArea logArea;
    private JButton selectFolderButton;
    private JButton convertButton;
    private JSlider qualitySlider;
    private JLabel qualityLabel;
    private JProgressBar progressBar;
    private JSlider brightnessSlider;
    private JLabel brightnessLabel;
    private JCheckBox recursiveCheckBox;
    private JLabel fileCountLabel;
    
    private File workingFolder;
    private final List<File> cr3Files;
    
    public CR3Converter() {
        cr3Files = new ArrayList<>();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("CR3 to JPEG Bulk Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Control panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Folder selection
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        selectFolderButton = new JButton("Select Folder (CR3 files → JPEG output)");
        selectFolderButton.addActionListener(new SelectFolderListener());
        controlPanel.add(selectFolderButton, gbc);
        
        // Recursive checkbox
        gbc.gridy = 1;
        recursiveCheckBox = new JCheckBox("Include subfolders", true);
        recursiveCheckBox.addActionListener(e -> {
            if (workingFolder != null) {
                scanForCR3Files();
            }
        });
        controlPanel.add(recursiveCheckBox, gbc);
        
        // File count label
        gbc.gridy = 2;
        fileCountLabel = new JLabel("No files selected");
        fileCountLabel.setFont(fileCountLabel.getFont().deriveFont(Font.BOLD));
        controlPanel.add(fileCountLabel, gbc);
        
        // Quality slider
        gbc.gridy = 3;
        qualityLabel = new JLabel("JPEG Quality: 95%");
        controlPanel.add(qualityLabel, gbc);
        
        gbc.gridy = 4;
        qualitySlider = new JSlider(50, 100, 95);
        qualitySlider.setMajorTickSpacing(10);
        qualitySlider.setMinorTickSpacing(5);
        qualitySlider.setPaintTicks(true);
        qualitySlider.setPaintLabels(true);
        qualitySlider.addChangeListener(e -> 
            qualityLabel.setText("JPEG Quality: " + qualitySlider.getValue() + "%"));
        controlPanel.add(qualitySlider, gbc);
        
        // Brightness slider
        gbc.gridy = 5;
        brightnessLabel = new JLabel("Brightness: 150%");
        controlPanel.add(brightnessLabel, gbc);
        
        gbc.gridy = 6;
        brightnessSlider = new JSlider(50, 300, 150);
        brightnessSlider.setMajorTickSpacing(50);
        brightnessSlider.setMinorTickSpacing(25);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);
        brightnessSlider.addChangeListener(e -> 
            brightnessLabel.setText("Brightness: " + brightnessSlider.getValue() + "%"));
        controlPanel.add(brightnessSlider, gbc);
        
        // Convert button
        gbc.gridy = 7;
        convertButton = new JButton("Start Bulk Conversion");
        convertButton.addActionListener(new ConvertListener());
        convertButton.setEnabled(false);
        convertButton.setPreferredSize(new Dimension(250, 40));
        controlPanel.add(convertButton, gbc);
        
        // Progress bar
        gbc.gridy = 8;
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(250, 25));
        controlPanel.add(progressBar, gbc);
        
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(680, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        log("CR3 to JPEG Bulk Converter initialized");
        log("Features:");
        log("  ✓ Single folder operation - CR3 files and JPEG output in same folder");
        log("  ✓ Bulk processing of CR3 files");
        log("  ✓ Recursive subfolder scanning");
        log("  ✓ Preserves original CR3 files");
        log("Note: This application requires LibRaw dcraw_emu for CR3 support");
        log("");
        
        // Test LibRaw installation
        testLibRawInstallation();
    }
    
    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    private class SelectFolderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser folderChooser = new JFileChooser();
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            folderChooser.setDialogTitle("Select folder containing CR3 files");
            
            if (folderChooser.showOpenDialog(CR3Converter.this) == JFileChooser.APPROVE_OPTION) {
                workingFolder = folderChooser.getSelectedFile();
                log("Working folder: " + workingFolder.getAbsolutePath());
                log("JPEGs will be created in the same folder as CR3 files");
                scanForCR3Files();
                updateConvertButtonState();
            }
        }
    }
    
    private void scanForCR3Files() {
        cr3Files.clear();
        if (workingFolder != null) {
            scanDirectory(workingFolder, recursiveCheckBox.isSelected());
            int count = cr3Files.size();
            fileCountLabel.setText("Found " + count + " CR3 file" + (count != 1 ? "s" : ""));
            
            log("Scanning completed: " + count + " CR3 files found");
            if (count > 0) {
                log("Sample files:");
                for (int i = 0; i < Math.min(5, count); i++) {
                    File cr3 = cr3Files.get(i);
                    String relativePath = workingFolder.toPath().relativize(cr3.toPath()).toString();
                    log("  - " + relativePath);
                }
                if (count > 5) {
                    log("  ... and " + (count - 5) + " more files");
                }
            }
        }
    }
    
    private void scanDirectory(File dir, boolean recursive) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName().toLowerCase();
                    if (name.endsWith(".cr3")) {
                        cr3Files.add(file);
                    }
                } else if (file.isDirectory() && recursive) {
                    scanDirectory(file, recursive);
                }
            }
        }
    }
    
    private void updateConvertButtonState() {
        boolean canConvert = !cr3Files.isEmpty();
        convertButton.setEnabled(canConvert);
        
        if (canConvert) {
            convertButton.setText("Convert " + cr3Files.size() + " Files");
        } else {
            convertButton.setText("Start Bulk Conversion");
        }
    }
    
    private class ConvertListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    convertAllFiles();
                    return null;
                }
                
                @Override
                protected void process(List<String> chunks) {
                    for (String message : chunks) {
                        log(message);
                    }
                }
                
                @Override
                protected void done() {
                    convertButton.setEnabled(true);
                    progressBar.setValue(100);
                    progressBar.setString("Conversion complete");
                }
            };
            
            convertButton.setEnabled(false);
            progressBar.setValue(0);
            progressBar.setString("Starting conversion...");
            worker.execute();
        }
    }
    
    private void convertAllFiles() {
        int totalFiles = cr3Files.size();
        int successCount = 0;
        int failureCount = 0;
        
        // Create separator
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            separator.append("=");
        }
        String separatorStr = separator.toString();
        
        log("\n" + separatorStr);
        log("STARTING BULK CONVERSION");
        log(separatorStr);
        log("Total files to convert: " + totalFiles);
        log("Working folder: " + workingFolder.getAbsolutePath());
        log("");
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < totalFiles; i++) {
            File cr3File = cr3Files.get(i);
            String baseName = cr3File.getName().replaceFirst("\\.[^.]+$", "");
            
            // Create JPEG in the same folder as the CR3 file
            File jpegFile = new File(cr3File.getParentFile(), baseName + ".jpg");
            
            final int currentIndex = i;
            final int progress = (int) (((double) (i + 1) / totalFiles) * 100);
            
            SwingUtilities.invokeLater(() -> {
                progressBar.setValue(progress);
                progressBar.setString("Converting " + (currentIndex + 1) + "/" + totalFiles + ": " + cr3File.getName());
            });
            
            String relativePath = workingFolder.toPath().relativize(cr3File.toPath()).toString();
            log("Converting [" + (i + 1) + "/" + totalFiles + "]: " + relativePath);
            
            try {
                // Skip if JPEG already exists
                if (jpegFile.exists()) {
                    log("  → JPEG already exists, skipping");
                    successCount++;
                    continue;
                }
                
                if (convertCR3ToJPEG(cr3File, jpegFile)) {
                    log("  ✓ Success → " + jpegFile.getName());
                    successCount++;
                } else {
                    log("  ✗ Failed");
                    failureCount++;
                }
            } catch (Exception ex) {
                log("  ✗ Error: " + ex.getMessage());
                failureCount++;
            }
            
            // Show progress every 10 files or on last file
            if ((i + 1) % 10 == 0 || i == totalFiles - 1) {
                long elapsed = System.currentTimeMillis() - startTime;
                double avgTimePerFile = (double) elapsed / (i + 1);
                long estimatedTotal = (long) (avgTimePerFile * totalFiles);
                long remaining = estimatedTotal - elapsed;
                
                log("  Progress: " + (i + 1) + "/" + totalFiles + 
                    " (" + String.format("%.1f", ((double)(i + 1) / totalFiles) * 100) + "%)" +
                    " | ETA: " + formatTime(remaining));
            }
        }
        
        long totalTime = System.currentTimeMillis() - startTime;
        
        log("\n" + separatorStr);
        log("CONVERSION SUMMARY");
        log(separatorStr);
        log("Successful conversions: " + successCount);
        log("Failed conversions: " + failureCount);
        log("Total files processed: " + totalFiles);
        log("Success rate: " + String.format("%.1f", ((double) successCount / totalFiles) * 100) + "%");
        log("Total time: " + formatTime(totalTime));
        log("Average time per file: " + String.format("%.1f", (double) totalTime / totalFiles / 1000) + " seconds");
        log("Working folder: " + workingFolder.getAbsolutePath());
        
        if (successCount > 0) {
            log("\n✓ Conversion completed successfully!");
            log("JPEG files created alongside their corresponding CR3 files");
        }
    }
    
    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes % 60, seconds % 60);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds % 60);
        } else {
            return String.format("%ds", seconds);
        }
    }
    
    private boolean convertCR3ToJPEG(File cr3File, File jpegFile) throws Exception {
        // Method 1: Try LibRaw simple PPM conversion (most reliable)
        if (convertUsingLibRawSimple(cr3File, jpegFile)) {
            return true;
        }
        
        // Method 2: Try LibRaw direct conversion
        if (convertUsingLibRawDirectJPEG(cr3File, jpegFile)) {
            return true;
        }
        
        // Method 3: Try using RawTherapee CLI (if available)
        if (convertUsingRawTherapee(cr3File, jpegFile)) {
            return true;
        }
        
        // Method 4: Try using standard dcraw (will likely fail)
        if (convertUsingDcraw(cr3File, jpegFile)) {
            return true;
        }
        
        // Method 5: Try using ImageIO (may not support CR3)
        return convertUsingImageIO(cr3File, jpegFile);
    }
    
    private boolean convertUsingLibRawSimple(File cr3File, File jpegFile) {
        try {
            String librawPath = "C:\\Users\\User\\Desktop\\Github Projects\\LibRaw-0.21.4\\bin\\dcraw_emu.exe";
            
            // Check if LibRaw executable exists
            File librawFile = new File(librawPath);
            if (!librawFile.exists()) {
                log("  ✗ LibRaw not found at: " + librawPath);
                return false;
            }
            
            // Copy CR3 to temp location in same folder for processing
            String baseName = cr3File.getName().replaceFirst("\\.[^.]+$", "");
            File tempCr3File = new File(cr3File.getParentFile(), "temp_" + baseName + ".cr3");
            
            log("    Copying CR3 for processing...");
            copyFile(cr3File, tempCr3File);
            
            // Calculate brightness from slider
            float brightnessMultiplier = brightnessSlider.getValue() / 100.0f;
            
            // Simple PPM output with basic settings
            ProcessBuilder pb = new ProcessBuilder(
                librawPath,
                "-w",           // Use camera white balance
                "-b", String.valueOf(brightnessMultiplier), // Brightness
                "-g", "2.222", "4.5", // Gamma correction
                "-o", "1",      // sRGB output
                tempCr3File.getAbsolutePath()
            );
            
            // Set working directory to the folder containing the CR3 file
            pb.directory(cr3File.getParentFile());
            
            log("    Executing: " + String.join(" ", pb.command()));
            
            Process process = pb.start();
            
            // Capture both streams for debugging
            StringBuilder stdout = new StringBuilder();
            StringBuilder stderr = new StringBuilder();
            
            try (BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                
                String line;
                while ((line = stdoutReader.readLine()) != null) {
                    stdout.append(line).append("\n");
                }
                while ((line = errorReader.readLine()) != null) {
                    stderr.append(line).append("\n");
                }
            }
            
            int exitCode = process.waitFor();
            
            log("    LibRaw exit code: " + exitCode);
            if (stderr.length() > 0) {
                log("    LibRaw stderr: " + stderr.toString().trim());
            }
            
            // Clean up temp CR3 file
            tempCr3File.delete();
            
            if (exitCode == 0) {
                // Look for the generated PPM file
                File ppmFile = new File(cr3File.getParentFile(), "temp_" + baseName + ".ppm");
                
                log("    Looking for PPM file: " + ppmFile.getName());
                
                if (ppmFile.exists()) {
                    log("    PPM file found: " + ppmFile.length() + " bytes");
                    
                    BufferedImage image = null;
                    
                    // Try custom PPM reader first
                    try {
                        image = readPPMFile(ppmFile);
                        log("    Successfully read with custom PPM reader: " + image.getWidth() + "x" + image.getHeight());
                    } catch (IOException | IllegalArgumentException e1) {
                        log("    Custom PPM reader failed: " + e1.getMessage());
                        
                        // Fallback to ImageIO
                        try {
                            image = ImageIO.read(ppmFile);
                            if (image != null) {
                                log("    Successfully read with ImageIO: " + image.getWidth() + "x" + image.getHeight());
                            }
                        } catch (IOException | IllegalArgumentException e2) {
                            log("    ImageIO also failed: " + e2.getMessage());
                        }
                    }
                    
                    if (image != null) {
                        writeJPEGWithQuality(image, jpegFile, qualitySlider.getValue() / 100.0f);
                        ppmFile.delete(); // Clean up immediately
                        log("    JPEG written successfully: " + jpegFile.length() + " bytes");
                        return true;
                    } else {
                        log("    Failed to read PPM file");
                    }
                    
                    // Clean up PPM file even if conversion failed
                    if (ppmFile.exists()) {
                        ppmFile.delete();
                    }
                } else {
                    log("    PPM file not found");
                    
                    // Check for any other files that might have been created
                    File[] files = cr3File.getParentFile().listFiles();
                    if (files != null) {
                        for (File f : files) {
                            if (f.getName().startsWith("temp_" + baseName) && 
                                !f.getName().equals(tempCr3File.getName())) {
                                log("    Found alternative output: " + f.getName());
                                
                                try {
                                    BufferedImage image = f.getName().toLowerCase().endsWith(".ppm")
                                        ? readPPMFile(f)
                                        : ImageIO.read(f);
                                    if (image != null) {
                                        writeJPEGWithQuality(image, jpegFile, qualitySlider.getValue() / 100.0f);
                                        f.delete();
                                        return true;
                                    }
                                } catch (IOException | IllegalArgumentException e) {
                                    log("    Failed to read " + f.getName() + ": " + e.getMessage());
                                }
                                
                                f.delete(); // Clean up anyway
                            }
                        }
                    }
                }
            } else {
                log("    LibRaw failed with exit code: " + exitCode);
            }
            
        } catch (IOException | InterruptedException e) {
            log("    Exception in LibRaw method: " + e.getMessage());
        }
        return false;
    }
    
    // Add file copy utility method
    private void copyFile(File source, File dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
    
    private boolean convertUsingLibRawDirectJPEG(File cr3File, File jpegFile) {
        try {
            String librawPath = "C:\\Users\\User\\Desktop\\Github Projects\\LibRaw-0.21.4\\bin\\dcraw_emu.exe";
            String baseName = cr3File.getName().replaceFirst("\\.[^.]+$", "");
            float brightnessMultiplier = brightnessSlider.getValue() / 100.0f;
            
            // Check if LibRaw executable exists
            File librawFile = new File(librawPath);
            if (!librawFile.exists()) {
                log("    ✗ LibRaw not found at: " + librawPath);
                return false;
            }
            
            // Copy CR3 to temp location
            File tempCr3File = new File(cr3File.getParentFile(), "temp_" + baseName + ".cr3");
            log("    Copying CR3 for direct method...");
            copyFile(cr3File, tempCr3File);
            
            ProcessBuilder pb = new ProcessBuilder(
                librawPath, "-w", "-H", "2", "-b", String.valueOf(brightnessMultiplier),
                "-g", "2.222", "4.5", "-q", "3", "-o", "1", 
                tempCr3File.getAbsolutePath()
            );
            
            pb.directory(cr3File.getParentFile());
            
            log("    Trying LibRaw direct method");
            
            Process process = pb.start();
            
            // Capture streams quietly
            try (BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                
                while (stdoutReader.readLine() != null) { /* consume */ }
                while (errorReader.readLine() != null) { /* consume */ }
            }
            
            int exitCode = process.waitFor();
            
            // Clean up temp CR3 file
            tempCr3File.delete();
            
            if (exitCode == 0) {
                File[] files = cr3File.getParentFile().listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.getName().startsWith("temp_" + baseName) && !f.equals(tempCr3File)) {
                            log("      Found output file: " + f.getName());
                            
                            BufferedImage image = null;
                            
                            if (f.getName().toLowerCase().endsWith(".ppm")) {
                                try {
                                    image = readPPMFile(f);
                                } catch (IOException e1) { /* try ImageIO */ }
                            }
                            
                            if (image == null) {
                                try {
                                    image = ImageIO.read(f);
                                } catch (IOException e1) { /* silent fail */ }
                            }
                            
                            if (image != null) {
                                writeJPEGWithQuality(image, jpegFile, qualitySlider.getValue() / 100.0f);
                                f.delete(); // Clean up immediately
                                return true;
                            }
                            
                            f.delete(); // Clean up anyway
                        }
                    }
                }
            }
            
        } catch (IOException | InterruptedException e) {
            // Silent failure for bulk processing
        }
        return false;
    }
    
    private BufferedImage readPPMFile(File ppmFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(ppmFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            
            // Read PPM header
            String magic = readPPMToken(bis);
            if (!magic.equals("P6")) {
                throw new IOException("Unsupported PPM format: " + magic);
            }
            
            // Skip comments
            String widthStr = readPPMToken(bis);
            String heightStr = readPPMToken(bis);
            String maxValStr = readPPMToken(bis);
            
            int width = Integer.parseInt(widthStr);
            int height = Integer.parseInt(heightStr);
            int maxVal = Integer.parseInt(maxValStr);
            
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            // Read pixel data
            byte[] buffer = new byte[3];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bis.read(buffer) != 3) {
                        throw new IOException("Unexpected end of file");
                    }
                    
                    int r = buffer[0] & 0xFF;
                    int g = buffer[1] & 0xFF;
                    int b = buffer[2] & 0xFF;
                    
                    if (maxVal != 255) {
                        r = (r * 255) / maxVal;
                        g = (g * 255) / maxVal;
                        b = (b * 255) / maxVal;
                    }
                    
                    int rgb = (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, rgb);
                }
            }
            
            return image;
        }
    }

    private String readPPMToken(BufferedInputStream bis) throws IOException {
        StringBuilder token = new StringBuilder();
        int b;
        
        // Skip whitespace
        do {
            b = bis.read();
            if (b == -1) throw new IOException("Unexpected end of file");
        } while (Character.isWhitespace(b));
        
        // Skip comments
        if (b == '#') {
            do {
                b = bis.read();
                if (b == -1) throw new IOException("Unexpected end of file");
            } while (b != '\n' && b != '\r');
            return readPPMToken(bis); // Recursively read next token
        }
        
        // Read token
        do {
            token.append((char) b);
            b = bis.read();
            if (b == -1) break;
        } while (!Character.isWhitespace(b));
        
        return token.toString();
    }
    
    // Simplified versions of other conversion methods
    @SuppressWarnings("unused")
    private boolean convertUsingDcraw(File cr3File, File jpegFile) { 
        // Method stub - parameters intentionally unused
        return false; 
    }
    
    @SuppressWarnings("unused")
    private boolean convertUsingRawTherapee(File cr3File, File jpegFile) { 
        // Method stub - parameters intentionally unused
        return false; 
    }
    
    @SuppressWarnings("unused")
    private boolean convertUsingImageIO(File cr3File, File jpegFile) { 
        // Method stub - parameters intentionally unused
        return false; 
    }
    
    private void writeJPEGWithQuality(BufferedImage image, File outputFile, float quality) 
            throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writers found");
        }
        
        ImageWriter writer = writers.next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);
            
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
    }
    
    private void testLibRawInstallation() {
        String librawPath = "C:\\Users\\User\\Desktop\\Github Projects\\LibRaw-0.21.4\\bin\\dcraw_emu.exe";
        File librawFile = new File(librawPath);
        
        if (librawFile.exists()) {
            log("✓ LibRaw found at: " + librawPath);
            
            try {
                ProcessBuilder pb = new ProcessBuilder(librawPath, "--help");
                Process process = pb.start();
                
                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                     BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                    while ((line = errorReader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }
                
                int exitCode = process.waitFor();
                
                if (output.length() > 0) {
                    String[] lines = output.toString().split("\n");
                    for (int i = 0; i < Math.min(3, lines.length); i++) {
                        if (lines[i].trim().length() > 0) {
                            log("  " + lines[i].trim());
                        }
                    }
                }
                
                if (exitCode == 0 || exitCode == 1) {
                    log("✓ LibRaw is responding");
                } else {
                    log("✗ LibRaw help test exit code: " + exitCode);
                }
                
            } catch (IOException | InterruptedException e) {
                log("✗ LibRaw test error: " + e.getMessage());
            }
        } else {
            log("✗ LibRaw not found at: " + librawPath);
            log("  Please ensure LibRaw is installed at the correct location");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CR3Converter().setVisible(true);
        });
    }
}
