package com.customermanagment.sims.exception;
/**
 * Record_Not_Found Exception
 *
 * @author  Olivier Brinkman
 * @version 1.0
 * @since   12/02/2019
 */
public class Record_Not_Found extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * record not found
     */
    public Record_Not_Found() {
        super("Cannot find specified record.");
    }

}