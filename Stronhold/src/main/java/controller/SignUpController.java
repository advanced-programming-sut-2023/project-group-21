package controller;

import model.Game;
import model.User;
import view.message.SignUpMessages;

import java.util.regex.Matcher;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class SignUpController {
    private String username, password, email, nickname, slogan;

    public SignUpMessages checkUserCreation(Matcher matcher) {
        return null;
    }

    public SignUpMessages pickQuestion(int number,String answer,String confirm){
        if (number > 3 || number < 1) return null;
        if (!answer.equals(confirm)) return null;
        Game.addUser(new User(username, password, email, nickname, slogan, answer, number));
        return null;
    }

    private String generateRandomPassword(){
        return generateSecurePassword();
    }

    private String giveRandomSlogan(){
        slogan = Game.SLOGANS.get((int) (Math.random()%10));
        return "repair";
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
