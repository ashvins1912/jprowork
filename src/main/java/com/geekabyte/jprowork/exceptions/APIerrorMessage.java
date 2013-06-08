/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.exceptions;

/**
 *
 * @author dade
 */
public class APIerrorMessage extends RuntimeException {
    
    public APIerrorMessage(String msg) {
        super(msg);
    }
}
