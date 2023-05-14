package view.message;

public enum SignUpMessages {
    USERNAME_REPEAT("this username is already taken"),
    EMAIL_REPEAT("please enter another email , this email is already used"),
    SUCCESS("success\n"),
    INVALID_NUMBER(""),
    BAD_REPEAT("password and confirm are not the same\n"),
    OUT_OF_RANGE(""),
    NO_EMAIL("please enter your email\n"),
    VALID_USERNAME("please enter valid username\n"),
    VALID_EMAIL("please enter valid email\n"),
    WEAK_PASSWORD("weak password\n"),
    PRINT_NOTHING("");
    private String message;
    SignUpMessages(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
