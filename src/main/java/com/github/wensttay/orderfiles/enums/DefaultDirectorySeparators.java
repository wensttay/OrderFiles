/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wensttay.orderfiles.enums;

/**
 *
 * @author Wensttay
 */
public enum DefaultDirectorySeparators {
    WINDOWS("/"),
    LINUX("\\");
    
    private final String directorySeparator;

    private DefaultDirectorySeparators(String directorySeparator) {
        this.directorySeparator = directorySeparator;
    }

    /**
     * @return the directorySeparator
     */
    public String getDirectorySeparator() {
        return directorySeparator;
    }
    
}
