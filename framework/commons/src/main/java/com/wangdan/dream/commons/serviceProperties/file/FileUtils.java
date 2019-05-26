package com.wangdan.dream.commons.serviceProperties.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

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

}
