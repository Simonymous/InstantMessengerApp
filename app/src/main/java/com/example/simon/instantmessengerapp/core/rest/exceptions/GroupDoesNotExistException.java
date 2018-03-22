package com.example.simon.instantmessengerapp.core.rest.exceptions;

import static com.example.simon.instantmessengerapp.core.rest.constants.GroupRestConstants.ERR_GROUP_DOES_NOT_EXIST;

/**
 * own exception to throw if a group does not exist
 */
public class GroupDoesNotExistException extends Exception {
    private static final long serialVersionID = 2L;

    /**
     * default constructor
     */
    public GroupDoesNotExistException() {
        super(ERR_GROUP_DOES_NOT_EXIST);
    }

    /**
     * constructor with given error message
     * @param string
     */
    public GroupDoesNotExistException(String string) {
        super(string);
    }
}
