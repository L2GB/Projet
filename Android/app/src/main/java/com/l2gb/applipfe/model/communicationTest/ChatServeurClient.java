package com.l2gb.applipfe.model.communicationTest;

import com.l2gb.applipfe.model.JsonUtil;

import java.io.*;
import java.net.*;



public class ChatServeurClient implements Runnable {

    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Thread requestThread;
    private JsonUtil communication;

    /*public ChatServeurClient(Socket s){
        this.socket = s;
        this.communication = new JsonUtil();
        try {
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public ChatServeurClient(String adresse,int port)
    {
        this.communication = new JsonUtil();
        try {
            this.socket = new Socket(adresse,port);
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //try {

            this.requestThread = new Thread(new Reception(in));
            this.requestThread.start();
            //System.out.println(this.communication.getJoursModel());
            //askProfilObjet();
        /*
        } catch (IOException e) {
            System.err.println("Le serveur distant s'est déconnecté !");
        }*/
    }

    public void sendRequest(String message)
    {
        this.out.println(message);
        this.out.flush();
    }

    public void askProfilObjet()
    {
        this.out.println(this.communication.getJoursModel());
        this.out.flush();
    }

}