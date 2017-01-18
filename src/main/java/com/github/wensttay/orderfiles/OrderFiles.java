package com.github.wensttay.orderfiles;

import com.github.wensttay.orderfiles.enums.DefaultDirectorySeparators;
import com.github.wensttay.orderfiles.enums.DefaultFolderStructures;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

/**
 * @version @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 16/01/2017 - 12:00:00
 */
public class OrderFiles {

    private SimpleDateFormat timeFormatToNewFolders;
    private String directorySeparator;

    public OrderFiles(DefaultDirectorySeparators defaultDirectorySeparators) {
        this.directorySeparator = defaultDirectorySeparators.getDirectorySeparator();
        timeFormatToNewFolders = DefaultFolderStructures.SIMPLE.getSimpleDateFormat();
    }

    public OrderFiles(DefaultDirectorySeparators defaultDirectorySeparators, DefaultFolderStructures defaultFolderStructures) {
        this.directorySeparator = defaultDirectorySeparators.getDirectorySeparator();
        this.timeFormatToNewFolders = defaultFolderStructures.getSimpleDateFormat();
    }

    public OrderFiles(SimpleDateFormat timeFormatToNewFolders, String directorySeparator) {
        this.timeFormatToNewFolders = timeFormatToNewFolders;
        this.directorySeparator = directorySeparator;
    }

    public void orderByDate(File fileHomeFolder) {
        System.out.println("Processing ...");
        orderByDate(fileHomeFolder, fileHomeFolder);
        System.out.println("Completed !");
    }

    public void orderByDate(File fileHomeFolder, File fileSubFolder) {

        if (!fileHomeFolder.exists()
                || !fileSubFolder.exists()
                || !fileHomeFolder.isDirectory()
                || !fileSubFolder.isDirectory()) {
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

        File newDiretory = new File(fileHomeFolder.getPath() + getSeparator() + fileName);
//        System.out.println(newDiretory.getPath());

        if (!newDiretory.exists()
                || !newDiretory.isDirectory()) {
            newDiretory.mkdirs();
        }

        return newDiretory;
    }

    private void copyToDiretory(File fileDir, File fileToCopy) {
        try {
            File checkNewFile = new File(fileDir.getPath() + getSeparator() + fileToCopy.getName());

            if (checkNewFile.exists()) {
                File newDuplicateFile = getNewDuplicateFile(fileDir, fileToCopy);

                if (newDuplicateFile != null) {
                    checkNewFile = newDuplicateFile;
                } else {
                    return;
                }
            }

            Files.copy(fileToCopy.toPath(),
                    checkNewFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            String fileToCopyPath = fileToCopy.getPath();
            int smartNameBreak = fileToCopyPath.lastIndexOf("\\") / 2;
            String from = fileToCopyPath.substring(0, 3) + "..." + fileToCopyPath.substring(smartNameBreak, fileToCopyPath.length());

            String checkNewFilePath = checkNewFile.getPath();
            smartNameBreak = checkNewFilePath.lastIndexOf("\\") / 2;
            String to = checkNewFilePath.substring(0, 3) + "..." + checkNewFilePath.substring(smartNameBreak, checkNewFilePath.length());

            System.out.println("Copy From: " + from + " >>> To: " + to);

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
                    = new File(dir.getPath() + getSeparator() + fileNameWithOutFormat + " (" + count + ")" + fileFormat);

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

    /**
     * @return the separator
     */
    public String getSeparator() {
        return directorySeparator;
    }

    /**
     * @param separator the separator to set
     */
    public void setSeparator(String separator) {
        this.directorySeparator = separator;
    }

}
