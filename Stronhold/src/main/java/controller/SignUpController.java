package controller;

import model.Game;
import model.User;
import view.message.SignUpMessages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
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

    public String generateCaptcha() {
        int width = 100;
        int height = 20;
        ArrayList<Integer> random;
        StringBuilder captcha = new StringBuilder();
        String[] fonts = {Font.DIALOG_INPUT, Font.DIALOG, Font.SANS_SERIF, Font.SERIF};
        Random randomize = new Random();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setFont(new Font(fonts[Math.abs(randomize.nextInt() % 4)], Font.BOLD, 10));
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString(generateCaptchaString(), 10, 20);

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            random = randomNoise();
            for (int x = 0; x < width; x++) {
                if (bufferedImage.getRGB(x, y) != -16777216) sb.append("%");
                else sb.append(" ");
            }
            if (sb.toString().trim().isEmpty()) continue;
            for (int rand: random) sb.replace(rand, rand + 1, "#");
            captcha.append(sb).append("\n");
        }
        return captcha.toString();
    }

    private String generateCaptchaString() {
        int n = 9;
        Random rand = new Random(26);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String captcha = "";
        while (n-->0){
            int index = (int)(Math.random()*26);
            captcha += characters.charAt(index);
            if (n != 0) captcha += " ";
        }
        return captcha;
    }

    private ArrayList<Integer> randomNoise() {
        Random random = new Random();
        ArrayList<Integer> sth = new ArrayList<>();
        for (int i = 1; i <= 10; i++) sth.add(Math.abs(random.nextInt() % 100));
        return sth;
    }

}
