package controller;

import model.Game;
import model.User;
import view.commands.CheckValidion;
import view.message.SignUpMessages;


import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import javax.xml.catalog.CatalogManager;


public class SignUpController {
    private String  slogan;

    public SignUpMessages createUserLastCheck(String username, String password, String email, String nickname,
            String slogan) {
        if (FileController.checkExistenceOfUserOrEmail(username, true)) {
            OtherController.sleepNormal();
            return SignUpMessages.USERNAME_REPEAT;
        }
        if (FileController.checkExistenceOfUserOrEmail(email, false)) {
            OtherController.sleepNormal();
            return SignUpMessages.EMAIL_REPEAT;
        }
        OtherController.resetSleepTime();
        return SignUpMessages.SUCCESS;
    }

    public User createUser(String username, String password, String email, String nickname, String slogan, int number,
            String answer) {
        password = FileController.encode(password);
        answer = FileController.encode(answer);
        User user = new User(username, password, nickname, email, slogan,0);
        FileController.addUserToFile(username,password,email,nickname,slogan,number,answer);
        return user;
    }


    public SignUpMessages pickQuestion(int number, String answer, String confirm) {
        if (number > 2 || number < 0)
            return SignUpMessages.OUT_OF_RANGE;
        if (!answer.equals(confirm))
            return SignUpMessages.BAD_REPEAT;
        return SignUpMessages.SUCCESS;
    }

    public String generateRandomPassword() {
        return generateSecurePassword();
    }

    public String giveRandomSlogan() {
        slogan = Game.SLOGANS.get((int) (Math.random() * 10));
        return slogan;
    }

    public SignUpMessages checkValidationFormat(String email,String username,String password,String confirm){
        if (email == null)
            return SignUpMessages.NO_EMAIL;
        else if (!confirm.equals(password))
            return SignUpMessages.BAD_REPEAT;
        else if (!CheckValidion.check(username, CheckValidion.CHECK_USERNAME))
            return SignUpMessages.VALID_USERNAME;
        else if (!CheckValidion.check(password, CheckValidion.CHECK_PASSWORD))
            return SignUpMessages.WEAK_PASSWORD;
        else if (!CheckValidion.check(email, CheckValidion.CHECK_EMAIL))
            return SignUpMessages.VALID_EMAIL;
        return SignUpMessages.PRINT_NOTHING;
    }

    private String generateSecurePassword() {
        CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
        LCR.setNumberOfCharacters(5);

        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        UCR.setNumberOfCharacters(3);

        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        DR.setNumberOfCharacters(3);

        CharacterRule SR = new CharacterRule(EnglishCharacterData.Special);
        SR.setNumberOfCharacters(2);

        PasswordGenerator passGen = new PasswordGenerator();

        return passGen.generatePassword(13, SR, LCR, UCR, DR);
    }


}
