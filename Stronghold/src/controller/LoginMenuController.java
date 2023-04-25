package controller;

import model.User;
import view.message.LoginMessages;
import view.message.SignUpMessages;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginMenuController {
    private ArrayList<String> userInfo = new ArrayList<>();
    private final String address ="/home/morteza/Desktop/program_ap/project/Stronghold3/Stronghold/src/controller/users.txt";//please change
    public LoginMessages CheckLogin(String username,String password){
        SignUpController signUpController = new SignUpController();
        if(!signUpController.checkExistenceOfUserOrEmail(username,true))
            return LoginMessages.NO_USER_EXISTS;
        if(!password.equals(getPassword(username)))
            return LoginMessages.INCORRECT_PASSWORD;
        return LoginMessages.SUCCESS;
    }

    public User Login(String username){
        User user = new User(username,userInfo.get(1),userInfo.get(4),userInfo.get(2),userInfo.get(3));
        return user;
    }

    private String getPassword(String username){
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        try {
            fileInputStream = new FileInputStream(address);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null){
                String words[] = line.split("##");
                if(words[0].equals(username)) {
                    for(int i1=0;i1< words.length;i1++) {
                        if (!words[i1].equals("^^^"))
                            userInfo.add(words[i1]);
                        else
                            userInfo.add(null);
                    }
                    return words[1];
                }
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e){
            return "";
        }
        catch (IOException e){
            return "";
        }
        return "";
    }


    public LoginMessages forgotPassword(String username){
        return null;
    }
}
