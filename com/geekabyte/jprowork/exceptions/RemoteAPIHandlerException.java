/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geekabyte.jprowork.exceptions;

/**
 *
 * @author dade
 */
public class RemoteAPIHandlerException extends JproworkRuntimeException {
    
    public RemoteAPIHandlerException(String message) {
        super(message);
    }
    
    public RemoteAPIHandlerException (String message, Throwable cause) {
        super(message, cause);
    }
}
