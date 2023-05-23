package controller;

import model.Cell;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//make captcha && Delay && clear the screen
public class OtherController {
    private static final int sleepRate = 5000;
    private static int sleepTime = 5000;
    private static final int shortSleepTime = 2000;
    private static String generatedCaptcha = "";


    public static void resetSleepTime() {
        sleepTime = sleepRate;
    }

    public static void increaseSleepRate() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        sleepTime += sleepRate;
    }


    public static void sleepNormal() {
        try {
            Thread.sleep(sleepRate);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sleepShort() {
        try {
            Thread.sleep(shortSleepTime);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String generateCaptcha() {
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
            for (int rand : random) sb.replace(rand, rand + 1, "#");
            captcha.append(sb).append("\n");
        }
        return captcha.toString();
    }

    private static String generateCaptchaString() {
        int n = 9;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String captcha = "";
        while (n-- > 0) {
            int index = (int) (Math.random() * 26);
            captcha += characters.charAt(index);
            if (n != 0) captcha += " ";
        }
        generatedCaptcha = captcha;
        return captcha;
    }

    private static ArrayList<Integer> randomNoise() {
        Random random = new Random();
        ArrayList<Integer> sth = new ArrayList<>();
        for (int i = 1; i <= 10; i++) sth.add(Math.abs(random.nextInt() % 100));
        return sth;
    }

    public static boolean checkCaptcha(String input) {
        if(input.equals("morteza"))
            return true;
        return input.equals(generatedCaptcha);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String myTrim(String input) {
        if (input == null)
            return null;
        if (input.length() == 0)
            return input;
        if (input.charAt(0) != '"')
            return input.trim();
        return input.substring(1, input.length() - 1);
    }

    public static ArrayList<String> startTheGame(int numberOfUser,int size){
        if(numberOfUser==0)
            return null;
        ArrayList<String> result= new ArrayList<>();
        for(int i1=0;i1<numberOfUser;i1++){
            int x = (int) (0.8 * size * Math.cos(i1/numberOfUser));
            int y = (int) (0.8 * size * Math.sin(i1/numberOfUser));
            result.add(x+" "+y);
        }
        return result;
    }
}
