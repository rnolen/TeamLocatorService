package com.geocent.teamlocator.exception;

public class InvalidMissionException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidMissionException() {}
    
    public InvalidMissionException( String message ) {
        super( message );
    }

}
