package com.customermanagment.sims.exception;
/**
 * Access_Forbidden Exception
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public class Access_Forbidden extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * access forbidden
     */
    public Access_Forbidden() {
        super("You are not authorized to access that resource.");
    }

}