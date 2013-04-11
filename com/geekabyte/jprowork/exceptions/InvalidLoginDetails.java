/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.exceptions;

/**
 *
 * @author dade
 */
public class InvalidLoginDetails extends Exception {
    
    public InvalidLoginDetails (String errorMsg) {
        super(errorMsg);
    }
    
}
