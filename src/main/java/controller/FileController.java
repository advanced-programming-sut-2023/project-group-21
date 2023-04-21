package controller;

import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileController {
    private static final String jsonAddress = "/home/morteza/Desktop/program_ap/projectMaven3/AP/src/main/java/controller/users.json";
    private static JSONArray allUsers;

    public static void start() {
        JSONParser jp = new JSONParser();
        try {
            FileReader fileReader = new FileReader(jsonAddress);
            Object temp = jp.parse(fileReader);
            allUsers = (JSONArray) temp;
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParseException p) {
            System.out.println(p.getMessage());
        }

    }

    public static int getSecurityQuestion(String username){
        JSONObject temp;
        for(int i1=0;i1<allUsers.size();i1++){
            temp = (JSONObject) allUsers.get(i1);
            if(temp.get("username").equals(username))
                return ((Long)temp.get("number")).intValue();
        }
        return 0;
    }

    public static String getInfo(String username,String info) {
        JSONObject jsonObject;
        for (Object j : allUsers) {
            jsonObject = (JSONObject) j;
            if (jsonObject.get("username") != null && jsonObject.get("username").equals(username))
                return (String) jsonObject.get(info);
        }
        return "";
    }

    public static void modifyInfo(String field,String username,String newValue){
        JSONObject tempJsonObject;
        for(int i1=0;i1<allUsers.size();i1++){
            tempJsonObject = (JSONObject) allUsers.get(i1);
            if(tempJsonObject.get("username").equals(username))
                tempJsonObject.put(field,newValue);
        }
    }
    public static void finish() {
        try {
            FileWriter fileWriter = new FileWriter(jsonAddress,false);
            fileWriter.write(allUsers.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static User getStayedUser(){
        JSONObject jsonObject;
        for(int i1=0;i1<allUsers.size();i1++){
            jsonObject = (JSONObject) allUsers.get(i1);
            if(jsonObject.get("stayed-logged-in").equals("t"))
                return getUserByUsername((String) jsonObject.get("username"));
        }
        return null;
    }
    public static User getUserByUsername(String username){
        JSONObject tempObject;
        for(int i1=0;i1<allUsers.size();i1++){
            tempObject = (JSONObject) allUsers.get(i1);
            if(tempObject.get("username").equals(username)){
                return (new User((String) tempObject.get("username"),(String) tempObject.get("password"),(String) tempObject.get("nickname"),(String) tempObject.get("email"),(String) tempObject.get("slogan")));
            }
        }
        return null;
    }

    public static boolean checkExistenceOfUserOrEmail(String info, boolean flag) {//true -->check username
        if (flag) {
            for (int i1 = 0; i1 < allUsers.size(); i1++) {
                JSONObject jsonObject = (JSONObject) allUsers.get(i1);
                if (jsonObject.get("username").equals(info))
                    return true;
            }
            return false;
        }
        for (int i1 = 0; i1 < allUsers.size(); i1++) {
            JSONObject jsonObject = (JSONObject) allUsers.get(i1);
            if (jsonObject.get("username").equals(info))
                return true;
        }
        return false;
    }

    public static void changeStayed(String username){
        JSONObject jsonObject;
        for(int i2=0;i2<allUsers.size();i2++){
            jsonObject = (JSONObject) allUsers.get(i2);
            jsonObject.put("stayed-logged-in","f");
        }
        for(int i1=0;i1<allUsers.size();i1++){
            jsonObject = (JSONObject) allUsers.get(i1);
            if(jsonObject.get("username").equals(username))
                jsonObject.put("stayed-logged-in","t");
        }
    }

    public static void addUserToFile(String username, String password, String email, String nickname, String slogan, int number,
                                     String answer) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("email", email);
        jsonObject.put("nickname", nickname);
        jsonObject.put("slogan", slogan);
        jsonObject.put("number", number);
        jsonObject.put("answer", answer);
        jsonObject.put("score", 0);
        jsonObject.put("stayed-logged-in","f");
        allUsers.add(jsonObject);
        try {
            FileWriter fileWriter = new FileWriter(jsonAddress, false);
            fileWriter.write(allUsers.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
