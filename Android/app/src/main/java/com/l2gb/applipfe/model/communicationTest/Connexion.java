package com.l2gb.applipfe.model.communicationTest;

import com.l2gb.applipfe.model.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion implements Runnable {

    private Socket socket = null;
    public static Thread chatThread;
    private String adresse;
    private int port;
    private ChatServeurClient chat;

    public Connexion(String adresse, int port)
    {
        this.adresse=adresse;
        this.port=port;

    }

    /**public void run() {

        try {
            this.socket = new Socket(this.adresse,this.port);
            this.chat = new ChatServeurClient(this.socket);
            chatThread = new Thread(this.chat);
            chatThread.start();

        } catch (IOException e) {
            System.err.println("Le serveur ne répond plus ");
        }
    }*/

    public void run() {
        this.chat = new ChatServeurClient(this.adresse,this.port);
        chatThread = new Thread(this.chat);
        chatThread.start();
    }

    public ChatServeurClient getChat() {
        return this.chat;
    }

    public void setChat(ChatServeurClient chat) {
        this.chat = chat;
    }
}