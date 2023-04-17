package controller;

import view.message.SignUpMessages;

public class CheckValidation {
    public static SignUpMessages isPasswordValid(String password) {
        if (password.length() < 6) return null;
        else if (!password.matches(".*[A-Z]+.*")) return null;
        else if (!password.matches(".*[a-z]+.*")) return null;
        else if (!password.matches(".*[`~!@#$%^&*()_+|}{“:?><\\[;',./\\-=]")) return null;
        return null;
    }
}
