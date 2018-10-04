package com.mad.studymate.validation;

public interface Authentication {
    boolean validate(String username, String password);

    boolean validate(String username, String password, String email);

    void generateError(String message);
}
