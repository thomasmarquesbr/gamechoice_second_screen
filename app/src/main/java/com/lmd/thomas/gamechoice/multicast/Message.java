package com.lmd.thomas.gamechoice.multicast;

import java.net.InetAddress;

/**
 * Created by thomas on 09/09/17.
 */

public class Message {

    private String tag;
    private String message;
    private long epoch = 0;
    private InetAddress ip;

    public Message(String message) throws IllegalArgumentException {
        this(message, (InetAddress) null);
    }

    public Message(String message, InetAddress ip) throws IllegalArgumentException {
        String split[] = message.split(" ");
        if(split.length < 3)
            throw new IllegalArgumentException();

        tag = split[0];
        epoch = Integer.parseInt(split[1]);
        this.ip = ip;

        message = "";
        for(int i = 2; i < split.length; i++)
            message = message.concat(split[i] + " ");

        this.message = message.substring(0, message.length() - 1);

    }

    public Message(String tag, String message) {
        this(tag, message, null);
    }

    public Message(String tag, String message, InetAddress ip) {
        this(tag, message, ip, System.currentTimeMillis()/1000);
    }

    public Message(String tag, String message, InetAddress ip, long time) {
        this.tag = tag;
        this.message = message;
        epoch = time;
        this.ip = ip;
    }

    public String getTag() { return tag; }
    public String getMessage() { return message; }
    public long getEpochTime() { return epoch; }
    public InetAddress getSrcIp() { return ip; }

    public String toString() { return tag+" " + epoch + " " + message; }

}
