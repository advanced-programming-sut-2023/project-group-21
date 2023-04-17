package controller;

import model.User;
import view.message.SignUpMessages;

import java.io.*;

public class SignUpController {
    private final String address = "/home/morteza/Desktop/program_ap/project/Stronghold3/Stronghold/src/controller/users.txt";//please change before use
    public SignUpMessages createUserLastCheck(String username, String password, String email, String nickname, String slogan){
        if(checkExistenceOfUserOrEmail(username,true))
            return SignUpMessages.USERNAME_REPEAT;
        if(checkExistenceOfUserOrEmail(email,false))
            return SignUpMessages.EMAIL_REPEAT;
        return SignUpMessages.SUCCESS;
    }

    public User createUser(String username, String password, String email, String nickname, String slogan,int number,String answer){
        User user = new User(username,password,nickname,email,slogan);
        String info = packUser(username,password,email,slogan,nickname,number,answer);
        addToFile(info);
        return user;
    }
    protected boolean checkExistenceOfUserOrEmail(String info,boolean flag){// if flag username else email
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        try {
            fileInputStream = new  FileInputStream(address);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null && flag){
                String[] userInfo = line.split("##");
                if(userInfo[0].equals(info))
                    return true;
                line = bufferedReader.readLine();
            }
            while (line != null && !flag){
                String[] userInfo = line.split("##");
                if(userInfo[2].equals(info))
                    return true;
                line = bufferedReader.readLine();
            }
            fileInputStream.close();
        }
        catch (FileNotFoundException e){
            return false;
        }
        catch (IOException e){
            return false;
        }
        return  false;
    }

    private String packUser(String username,String password,String email,String slogan,String nickname,int questionNumber,String answer){//temp code
        String temp="";
        temp += (username+"##"+password+"##"+email);
        if(slogan == null)
            temp += "##^^^";
        else
            temp += ("##"+slogan);
        if(nickname == null)
            temp += "##^^^";
        else
            temp += ("##"+nickname);
        temp += ("##"+questionNumber);
        temp += ("##"+answer);
        temp += ("##"+0);
        return temp;
    }
    public SignUpMessages pickQuestion(int number,String answer,String confirm){
        return null;
    }

    public String generateRandomPassword(){
        return "repair";
    }

    public String giveRandomSlogan(){
        return "repair";
    }
    private void addToFile(String userInfo){
        try {
            FileWriter fileWriter = new FileWriter(address, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(userInfo);
            bufferedWriter.close();
            printWriter.close();
        }catch (IOException e){
            return;
        }
    }
}
