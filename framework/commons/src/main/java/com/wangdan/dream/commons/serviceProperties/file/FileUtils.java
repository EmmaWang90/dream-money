package com.wangdan.dream.commons.serviceProperties.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


public class FileUtils {
    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static File createFile(String filePath) {
        File file = new File(filePath);
        File result = null;
        if (file.exists()) {
            if (file.delete())
                result = createNewFile(file);
            else
                logger.warn("failed to delete old file of {}", filePath);
        } else {
            String parentPath = file.getParent();
            File parentPathFile = new File(parentPath);
            boolean mkPathPathResult = parentPathFile.mkdirs();
            if (mkPathPathResult)
                result = createNewFile(file);
            else
                logger.warn("failed to mkdir for parent path of {}", filePath);
        }
        return result;
    }

    private static File createNewFile(File file) {
        boolean result = false;
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            logger.error("failed to create file {}", file.getAbsolutePath(), e);
        }
        return result ? file : null;
    }

    public static String read(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) {
            BufferedReader fileReader = null;
            try {
                fileReader = new BufferedReader(new FileReader(file));
                String lineContent = null;
                while ((lineContent = fileReader.readLine()) != null)
                    stringBuilder.append(lineContent);
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                if (fileReader != null) {
                    try {
                        fileReader.close();
                        fileReader = null;
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static void save(InputStream fileInputStream, String filePath) throws Exception {
        File file = createFile(filePath);
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(fileInputStream, file);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

}
