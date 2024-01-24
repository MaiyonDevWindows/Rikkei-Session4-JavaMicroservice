package com.maiyon.config.initialize;

import java.io.File;

public class DataFilePaths {
    public static String getProjectAbsolutePath(){
        File currentProject = new File("");
        return currentProject.getAbsolutePath();
    }
    public static String getCSVDataFilePath(){
        return String.join(File.separator, getProjectAbsolutePath(),
                "src", "main", "java", "com", "maiyon", "config", "initialize", "csvFiles");
    }
}