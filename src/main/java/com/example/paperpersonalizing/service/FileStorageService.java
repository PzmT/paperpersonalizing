package com.example.paperpersonalizing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class FileStorageService {
    @Autowired
    private ParseService parseService;
    @Autowired
    private FileStorageService fileStorageService;
    public void readUserDirectory(List<File> files) throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {
                parseService.parseCategory(file);
                // 递归处理子目录
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    readUserDirectory(Arrays.asList(subFiles));
                }
            } else {
                parseService.parsePaper(file);
            }
        }
    }

    public void readDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    parseService.parseCategory(file);
                    // 递归处理子目录
                    readDirectory(file);
                } else {
                    parseService.parsePaper(file);
                }
            }
        }
    }
    public List<File> readZip(MultipartFile zipFile, String rootDirectoryPath) throws IOException {
        File tempDirectory = new File(rootDirectoryPath);
        if (!tempDirectory.mkdir()) {
            throw new IOException("Failed to create temp directory for unzipping the file");
        }

        ZipInputStream zis = new ZipInputStream(zipFile.getInputStream());
        ZipEntry zipEntry = zis.getNextEntry();
        List<File> extractedFiles = new ArrayList<>();

        while (zipEntry != null) {
            File newFile = newFile(tempDirectory, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // Fix for Windows backward slash issue
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();

                extractedFiles.add(newFile);
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        return extractedFiles;
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
        }

        return destFile;
    }

}
