/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.exceptions;

/**
 *
 * @author dade
 */
public class InvalidParameterException extends JproworkRuntimeException {
    
    public InvalidParameterException(String message) {
        super(message);
    }
    
    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
