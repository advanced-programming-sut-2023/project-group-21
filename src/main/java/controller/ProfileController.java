package controller;

import model.User;
import view.message.ProfileMessages;
import view.commands.CheckValidion;

public class ProfileController {
    private final User user;

    public ProfileController(User user){
        this.user = user;
    }
    public ProfileMessages changeSomething(String username,String field,String newOption){
        if(field.equals("username")&&FileController.checkExistenceOfUserOrEmail(newOption,true))
            return ProfileMessages.TAKEN_USERNAME;
        if(field.equals("email")&&FileController.checkExistenceOfUserOrEmail(newOption,true))
            return ProfileMessages.TAKEN_EMAIL;
        FileController.modifyInfo(field,username,newOption);
        switch (field) {
            case "username" -> user.setUserName(newOption);
            case "email" -> user.setEmail(newOption);
            case "slogan" -> user.setSlogan(newOption);
            case "nickname" -> user.setNickName(newOption);
        }
        return ProfileMessages.SUCCESS;
    }


    public ProfileMessages changePassword(String oldPassword,String newPassword,String confirm){
        oldPassword = FileController.encode(oldPassword);
        if(!confirm.equals(newPassword))
            return ProfileMessages.PASSWORD_AND_CONFIRM;
        if(!user.getPassword().equals(oldPassword))
            return ProfileMessages.INCORRECT_PASSWORD;
        if(!CheckValidion.check(newPassword,CheckValidion.CHECK_PASSWORD))
            return ProfileMessages.WEAK_PASSWORD;
        newPassword = FileController.encode(newPassword);
        user.setPassword(newPassword);
        FileController.modifyInfo("password", user.getUserName(), newPassword);
        return ProfileMessages.SUCCESS;
    }



    public ProfileMessages removeSlogan(){
        return null;
    }

    public String display(String whatToDisplay){
        switch (whatToDisplay) {
            case "rank":
                return String.valueOf(FileController.getRank(user.getUserName()));
            case "username":
                return user.getUserName();
            case "email":
                return user.getEmail();
            case "nickname":
                return user.getNickName();
            case "password":
                return "sorry due to security reason it is impossible ";
            case "slogan":
                return user.getSlogan();
        }

        String str = "";
        str += "username: "+user.getUserName()+"\n"+"email: "+user.getEmail()+"\n";
        if(user.getNickName() != null)
            str += "nickname: "+user.getNickName()+"\n";
        if(user.getNickName() != null)
            str += "slogan: "+user.getSlogan();
        return str;
    }
}
