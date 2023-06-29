package model;

import controller.OtherController;
import model.generalenums.MessageEnum;

import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private final String senderUsername;
    private final long time;
    private final MessageEnum type;
    private boolean seenState = false;


    public Message(String senderUsername, String text, long time, MessageEnum type, boolean seenState) {//for loading from file
        this.senderUsername = senderUsername;
        this.text = text;
        this.time = time;
        this.type = type;
        this.seenState = seenState;
    }

    public Message(String username, String text, MessageEnum type) {//for creating new message
        this.senderUsername = username;
        this.text = makeAlignment(text, 80);
        this.type = type;
        this.time = System.currentTimeMillis();
    }

    private String makeAlignment(String input, int numberOfChar) {//it should improve!
        return OtherController.makeAlignment(input,numberOfChar);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void changeSeen() {
        seenState = true;
    }

    public long getTime() {
        return time;
    }

    public MessageEnum getType() {
        return type;
    }

    public String getTimeString() {
        int time1 = (int)time/1000;
        String result = "";
        int hour = (int) ((time1 % 86400) / 3600);// to calculate hour!
        int min = (int) ((time1 % 3600) / 60);
        result += hour + ":" + min;
        return result;
    }

    public int calculateLineNumber(){
        int result = 0;
        for (int i = 0;i<text.length();i++)
            if (text.charAt(i) == '\n')
                result++;
        return result;
    }
}
