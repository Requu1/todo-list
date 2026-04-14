package com.github.Requu1.todolist.exception;

public class DuplicateTaskException extends RuntimeException {
    public DuplicateTaskException(String message) {
        super(message);
    }
}
