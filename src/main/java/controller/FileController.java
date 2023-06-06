package controller;

import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class FileController {
    private static final String jsonAddress = (new File("").getAbsolutePath()) +
            "/src/main/resources/users/users.json";//repaired!
    private static final String maps = (new File("").getAbsolutePath())+"/src/main/resources/maps/";
    private static JSONArray allUsers=new JSONArray();

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

    public static String encode(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR SHA256!");
        }
        return "";
    }

    public static int getSecurityQuestion(String username) {
        JSONObject temp;
        for (Object allUser : allUsers) {
            temp = (JSONObject) allUser;
            if (temp.get("username").equals(username)) {
                if(temp.get("number") instanceof Integer)
                    return (Integer) temp.get("number");
                if (temp.get("number") instanceof  Long)
                    return ((Long)temp.get("number")).intValue();
            }
        }
        return 0;
    }

    public static String getInfo(String username, String info) {
        JSONObject jsonObject;
        for (Object j : allUsers) {
            jsonObject = (JSONObject) j;
            if (jsonObject.get("username") != null && jsonObject.get("username").equals(username))
                return (String) jsonObject.get(info);
        }
        return "";
    }

    public static void modifyInfo(String field, String username, String newValue) {
        JSONObject tempJsonObject;
        for (Object allUser : allUsers) {
            tempJsonObject = (JSONObject) allUser;
            if (tempJsonObject.get("username").equals(username))
                tempJsonObject.put(field, newValue);
        }
    }

    public static void finish() {
        try {
            FileWriter fileWriter = new FileWriter(jsonAddress, false);
            fileWriter.write(allUsers.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User getStayedUser() {
        JSONObject jsonObject;
        for (Object allUser : allUsers) {
            jsonObject = (JSONObject) allUser;
            if (jsonObject.get("stayed-logged-in").equals("t"))
                return getUserByUsername((String) jsonObject.get("username"));
        }
        return null;
    }

    public static User getUserByUsername(String username) {
        JSONObject tempObject;
        for (Object allUser : allUsers) {
            tempObject = (JSONObject) allUser;
            if (tempObject.get("username").equals(username)) {
                return (new User((String) tempObject.get("username"), (String) tempObject.get("password"),
                        (String) tempObject.get("nickname"), (String) tempObject.get("email"),
                        (String) tempObject.get("slogan")));
            }
        }
        return null;
    }

    public static boolean checkExistenceOfUserOrEmail(String info, boolean flag) {//true -->check username
        if (flag) {
            for (Object allUser : allUsers) {
                JSONObject jsonObject = (JSONObject) allUser;
                if (jsonObject.get("username").equals(info))
                    return true;
            }
            return false;
        }
        for (Object allUser : allUsers) {
            JSONObject jsonObject = (JSONObject) allUser;
            if (jsonObject.get("username").equals(info))
                return true;
        }
        return false;
    }

    public static void changeStayed(String username) {
        JSONObject jsonObject;
        for (Object allUser : allUsers) {
            jsonObject = (JSONObject) allUser;
            jsonObject.put("stayed-logged-in", "f");
        }
        for (Object allUser : allUsers) {
            jsonObject = (JSONObject) allUser;
            if (jsonObject.get("username").equals(username))
                jsonObject.put("stayed-logged-in", "t");
        }
    }

    public static void addUserToFile(String username, String password, String email, String nickname, String slogan,
                                     int number,
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
        jsonObject.put("stayed-logged-in", "f");
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

    public static void updateScore(String username, int newScore) {
        for (Object allUser : allUsers) {
            JSONObject jo = (JSONObject) allUser;
            if (jo.get("username").equals(username))
                jo.put("score", newScore);
        }
    }

    public static void saveMap(String username, String code) {
        String address = maps + username + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(address, false);
            fileWriter.write(code);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> loadMap(String username) {
        ArrayList<String> myMap = new ArrayList<>();
        if (!checkExistenceOfMap(username))
            return null;
        String address = maps + username + ".txt";
        try {
            FileReader fileReader = new FileReader(address);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                myMap.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (myMap.size() != 200 && myMap.size() != 400)
            return null;
        return myMap;
    }

    public static boolean checkExistenceOfMap(String username) {
        String fileName = maps + username + ".txt";
        File f = new File(fileName);
        return f.exists() && !f.isDirectory();
    }

    public static int getRank(String username) {
        int score = -1;
        for (int i1 = 0; i1 < allUsers.size(); i1++) {
            if (((JSONObject) allUsers.get(i1)).get("username").equals(username))
                score = (Integer) (((JSONObject) allUsers.get(i1)).get("score"));
        }
        if (score == -1)
            return -1;
        int rank = 1;
        for (int i2 = 0; i2 < allUsers.size(); i2++) {
            if (score < (Integer) (((JSONObject) allUsers.get(i2)).get("score")))
                rank++;
        }
        return rank;
    }

    public static void modifyScore(String username, int change) {
        JSONObject temp;
        for (int i1 = 0; i1 < allUsers.size(); i1++) {
            temp = (JSONObject) allUsers.get(i1);
            if (temp.get("username").equals(username))
                temp.replace("score", ((Long)temp.get("score"))+ change);
        }
    }


    public void sortUsers(){
        JSONObject tempReplace;
        for(int i1=0;i1<allUsers.size();i1++){
            for(int i2=i1+1;i2<allUsers.size();i2++)
                if(((int)((JSONObject)allUsers.get(i1)).get("score"))>((int)((JSONObject)allUsers.get(i2)).get("score"))){
                    tempReplace = (JSONObject) allUsers.get(i1);
                }
        }
    }
    public static String suggestUsername(String username) {
        if (!checkExistenceOfUserOrEmail(username, true))
            return username;
        for (int i1 = 0; i1 < 100; i1++) {
            if (!checkExistenceOfUserOrEmail(username + String.valueOf(i1), true))
                return username + String.valueOf(i1);
        }
        return suggestUsername(username + "a");
    }

}
