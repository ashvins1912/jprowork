/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.exceptions;

/**
 *
 * @author dade
 */
public class MissingParameterException extends JproworkRuntimeException {
    
    public MissingParameterException() {}
    
    public MissingParameterException(String message) {
        super(message);
    }
    
}
