package controller;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String exp , IOException e) {
        super(exp, e);
    }
}
