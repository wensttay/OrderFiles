/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wensttay.orderfiles.enums;

import java.text.SimpleDateFormat;

/**
 *
 * @author Wensttay
 */
public enum DefaultFolderStructures {
    SIMPLE(new SimpleDateFormat("yyyy.MM.dd")),
    TREE(new SimpleDateFormat("yyyy\\MM\\dd")),
    TREE_MOTH(new SimpleDateFormat("yyyy\\MMMM\\dd"));
    
    private final SimpleDateFormat simpleDateFormat;

    private DefaultFolderStructures(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }
}
