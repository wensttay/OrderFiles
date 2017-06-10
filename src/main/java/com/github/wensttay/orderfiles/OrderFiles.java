package com.github.wensttay.orderfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

/**
 * @version
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 16/01/2017 - 12:00:00
 */
public class OrderFiles {

    private SimpleDateFormat timeFormatToNewFolders = new SimpleDateFormat("dd.MM.yyyy");

    public void orderByDate(File fileHomeFolder) {
        System.out.println("Processing ...");
        orderByDate(fileHomeFolder, fileHomeFolder);
        System.out.println("Completed !");
    }
    
    public void orderByDate(File fileHomeFolder, File fileSubFolder){
        
        if (!fileHomeFolder.exists()
                || !fileSubFolder.exists()
                || !fileHomeFolder.isDirectory()
                || !fileSubFolder.isDirectory()){
            return;
        }

        File[] listOfFiles = fileSubFolder.listFiles();

        for (File f : listOfFiles) {
            if (f.exists()) {
                if (!f.isDirectory()) {
                    File dir = createDiretory(fileHomeFolder, 
                            getTimeFormatToNewFolders().format(f.lastModified()));
                    copyToDiretory(dir, f);
                } else {
                    orderByDate(fileHomeFolder, f);
                }
            }
        }
    }
    
    private File createDiretory(File fileHomeFolder, String fileName) {
        File newDiretory = new File(fileHomeFolder.getPath() + File.separator + fileName);

        if (!newDiretory.exists()
                && !newDiretory.isDirectory()) {
            newDiretory.mkdir();
        }

        return newDiretory;
    }

    private void copyToDiretory(File fileDir, File fileToCopy) {
        try {
            File checkNewFile = new File(fileDir.getPath() + File.separator + fileToCopy.getName());

            if (checkNewFile.exists()) { 
                File newDuplicateFile = getNewDuplicateFile(fileDir, fileToCopy);
                
                if(newDuplicateFile != null) {
                    checkNewFile = newDuplicateFile;
                }else{         
                    return;
                }
            }

            Files.copy(fileToCopy.toPath(),
                    checkNewFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private File getNewDuplicateFile(File dir, File fileToCopy) {

        boolean exists = true;
        int count = 2;

        while (exists) {
            String nameFileToCopy = fileToCopy.getName();
            String fileNameWithOutFormat = "";
            String fileFormat = "";

            int pos = nameFileToCopy.lastIndexOf(".");

            if (pos > 0) {
                fileNameWithOutFormat = nameFileToCopy.substring(0, pos);
                fileFormat = nameFileToCopy.substring(pos, nameFileToCopy.length());
            }

            File newDuplicateFile 
                    = new File(dir.getPath() + File.separator + fileNameWithOutFormat + " (" + count + ")" + fileFormat);

            if (!newDuplicateFile.exists()) {
                return newDuplicateFile;
            } else {
                count++;
            }
        }
        
        return null;
    }

    /**
     * @return the timeFormatToNewFolders
     */
    public SimpleDateFormat getTimeFormatToNewFolders() {
        return timeFormatToNewFolders;
    }

    /**
     * @param timeFormatToNewFolders the timeFormatToNewFolders to set
     */
    public void setTimeFormatToNewFolders(SimpleDateFormat timeFormatToNewFolders) {
        this.timeFormatToNewFolders = timeFormatToNewFolders;
    }

}
