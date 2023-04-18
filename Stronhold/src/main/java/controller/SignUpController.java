package controller;

import model.Game;
import model.User;
import view.message.SignUpMessages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class SignUpController {
    private final String address = "";
    private String username, password, email, nickname, slogan;

    public SignUpMessages checkUserCreation(Matcher matcher) {
        return null;
    }

    private String packUser(String username, String password, String email, String slogan, String nickname,
            int questionNumber, String answer) {// temp code
        String temp = "";
        temp += (username + "##" + password + "##" + email);
        if (slogan == null)
            temp += "##^^^";
        else
            temp += ("##" + slogan);
        if (nickname == null)
            temp += "##^^^";
        else
            temp += ("##" + nickname);
        temp += ("##" + questionNumber);
        temp += ("##" + answer);
        temp += ("##" + 0);
        return temp;
    }

    public SignUpMessages createUserLastCheck(String username, String password, String email, String nickname,
            String slogan) {
        if (checkExistenceOfUserOrEmail(username, true))
            return SignUpMessages.USERNAME_REPEAT;
        if (checkExistenceOfUserOrEmail(email, false))
            return SignUpMessages.EMAIL_REPEAT;
        return SignUpMessages.SUCCESS;
    }

    public User createUser(String username, String password, String email, String nickname, String slogan, int number,
            String answer) {
        User user = new User(username, password, nickname, email, slogan);
        String info = packUser(username, password, email, slogan, nickname, number, answer);
        addToFile(info);
        return user;
    }

    protected boolean checkExistenceOfUserOrEmail(String info, boolean flag) {// if flag username else email
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        try {
            fileInputStream = new FileInputStream(address);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null && flag) {
                String[] userInfo = line.split("##");
                if (userInfo[0].equals(info))
                    return true;
                line = bufferedReader.readLine();
            }
            while (line != null && !flag) {
                String[] userInfo = line.split("##");
                if (userInfo[2].equals(info))
                    return true;
                line = bufferedReader.readLine();
            }
            fileInputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public SignUpMessages pickQuestion(int number, String answer, String confirm) {
        if (number > 3 || number < 1)
            return SignUpMessages.OUT_OF_RANGE;
        if (!answer.equals(confirm))
            return SignUpMessages.BAD_REAPAET;
        Game.addUser(new User(username, password, email, nickname, slogan));
        return SignUpMessages.SUCCESS;
    }

    public String generateRandomPassword() {
        return generateSecurePassword();
    }

    public String giveRandomSlogan() {
        slogan = Game.SLOGANS.get((int) (Math.random() % 10));
        return slogan;
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

    private void addToFile(String userInfo) {
        try {
            FileWriter fileWriter = new FileWriter(address, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(userInfo);
            bufferedWriter.close();
            printWriter.close();
        } catch (IOException e) {
            return;
        }
    }

}
