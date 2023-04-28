package view.message;

public enum ProfileMessages {
    PASSWORD_AND_CONFIRM("password and confirm are not the same!"),
    WEAK_PASSWORD("weak password"),
    TAKEN_USERNAME("this username is already taken"),
    TAKEN_EMAIL("this email is already taken"),
    INVALID_FORMAT("invalid format!"),
    INCORRECT_PASSWORD("the password is not correct!"),
    SUCCESS("success!");
    private String message;
    ProfileMessages(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
