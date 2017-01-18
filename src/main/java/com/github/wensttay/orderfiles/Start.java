package com.github.wensttay.orderfiles;

import com.github.wensttay.orderfiles.enums.DefaultDirectorySeparators;
import com.github.wensttay.orderfiles.enums.DefaultFolderStructures;
import java.io.File;

/**
 * @version
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 16/01/2017 - 12:00:00
 */
public class Start {
    
    public static void main(String[] args) {
        String path = "D:\\Fotos";
        OrderFiles orderFiles;
        
        // SIMPLE FOLDER MODE
//        orderFiles = new OrderFiles(DefaultDirectorySeparators.WINDOWS,
//                DefaultFolderStructures.SIMPLE);
        
        // TREE FOLDER MODE
//        orderFiles = new OrderFiles(DefaultDirectorySeparators.WINDOWS,
//                DefaultFolderStructures.TREE);

        // TREE FOLDER MODE WITH FULL MOTH NAMES
        orderFiles = new OrderFiles(DefaultDirectorySeparators.WINDOWS,
                DefaultFolderStructures.TREE_MOTH);
        
        orderFiles.orderByDate(new File(path));
    }
}
