/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.teamlocator.exception;

/**
 *
 * @author rnolen
 */
public class ServiceNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ServiceNotFoundException( String message ) {
        super(message);
    }
    
}
