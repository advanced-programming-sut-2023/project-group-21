package model;

import model.generalenums.MessageEnum;

import java.io.Serializable;

public class Message implements Serializable {
    private  String text;
    private final String senderUsername;
    private final long time;
    private final MessageEnum type;
    private boolean seenState = false;


    public Message(String senderUsername, String text, long time, MessageEnum type,boolean seenState) {//for loading from file
        this.senderUsername = senderUsername;
        this.text = text;
        this.time = time;
        this.type = type;
        this.seenState = seenState;
    }

    public Message(String username,String text,MessageEnum type){//for creating new message
        this.senderUsername = username;
        this.text = makeAlignment(text,38);
        this.type = type;
        this.time = System.currentTimeMillis();
    }

    private String makeAlignment(String input,int numberOfChar){//it should improve!
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i<input.length();i++){
            if (!(i%numberOfChar == 0 && i != 0))
                builder.append(input.charAt(i));
            else
                builder.append(input.charAt(i)).append('\n');
        }
        return builder.toString();
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
    public void changeSeen(){
        seenState = true;
    }
    public long getTime() {
        return time;
    }

    public MessageEnum getType() {
        return type;
    }
}
