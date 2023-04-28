package view;

import controller.ProfileController;
import model.User;
import view.commands.CheckValidion;
import view.commands.ProfileCommands;
import view.message.ProfileMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    ProfileController profileController;
    User currentUser;

    ProfileMenu(User currentUser) {
        this.currentUser = currentUser;
        profileController = new ProfileController(currentUser);
    }

    public void run(Scanner scanner) {
        String line;
        Matcher matcher;
        while (true) {
            line = scanner.nextLine();
            if ((matcher = ProfileCommands.getMatcher(line, ProfileCommands.BACK)) != null)
                break;
            else if ((matcher = ProfileCommands.getMatcher(line, ProfileCommands.HELP)) != null)
                System.out.println("it needs repair");
            else if ((matcher = ProfileCommands.getMatcher(line, ProfileCommands.CHANGE_PASSWORD)) != null)
                checkChangePassword(matcher);
            else if ((matcher = ProfileCommands.getMatcher(line, ProfileCommands.CHANGE_SOMETHING)) != null)
                checkChangeSomething(matcher);
            else if ((matcher = ProfileCommands.getMatcher(line, ProfileCommands.DISPLAY_PROFILE)) != null)
                checkDisplay(matcher);
            else
                System.out.println("invalid command!");

        }
    }


    public void checkChangePassword(Matcher matcher) {
        String oldPassword = matcher.group("oldPassword");
        String newPassword = matcher.group("newPassword");
        String confirm = matcher.group("confirm");
        ProfileMessages profileMessages = profileController.changePassword(oldPassword, newPassword, confirm);
        if (profileMessages == ProfileMessages.WEAK_PASSWORD)
            System.out.println("weak password");
        else if (profileMessages == ProfileMessages.PASSWORD_AND_CONFIRM)
            System.out.println("password and confirm are not the same");
        else if(profileMessages == ProfileMessages.INCORRECT_PASSWORD)
            System.out.println("incorrect password");
        else
            System.out.println("password  changed successfully");
    }

    public void checkChangeSomething(Matcher matcher) {
        String option = matcher.group("whichOption");
        String newOption = matcher.group("newOption");
        if (!(option.equals("username") || option.equals("nickname") || option.equals("slogan") || option.equals("email"))) {
            System.out.println("invalid option to change");
            return;
        }
        if ((option.equals("username") || option.equals("email")) && newOption.contains(" ")) {
            System.out.println("field " + option + " cannot contain space");
            return;
        }
        if ((option.equals("email") && !CheckValidion.check(newOption, CheckValidion.CHECK_EMAIL)) || (option.equals("username") && !CheckValidion.check(newOption, CheckValidion.CHECK_USERNAME))) {
            System.out.println("invalid format for " + option);
            return;
        }
        ProfileMessages profileMessages = profileController.changeSomething(currentUser.getUserName(), option, newOption);
        if (profileMessages == ProfileMessages.TAKEN_USERNAME)
            System.out.println("username already exists");
        else if (profileMessages == ProfileMessages.TAKEN_EMAIL)
            System.out.println("Taken email");
        else
            System.out.println("success!");
    }


    public void checkRemoveSlogan() {

    }

    public void checkDisplay(Matcher matcher) {
        String part = matcher.group("part");
        System.out.println(profileController.display(part));
    }

}
