/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.exceptions;

/**
 *
 * @author dade
 */
public class JproworkRuntimeException extends RuntimeException {
    
    public JproworkRuntimeException () {
       
    }

    public JproworkRuntimeException (String message) {
        super (message);
    }
    
    public JproworkRuntimeException (String message, Throwable cause) {
        super (message, cause);
    }
    
    
}
