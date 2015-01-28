package com.l2gb.applipfe.model.communicationTest;

import com.l2gb.applipfe.model.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by pierrebaranger1 on 26/01/2015.
 */
public class Reception implements Runnable {

    private BufferedReader in;
    private String message = null;
    private JsonUtil jSonMethode;


    public Reception(BufferedReader in){
        this.in = in;
        this.jSonMethode = new JsonUtil();
    }

    public void run() {

        while(true){
            try {
                message = this.in.readLine();
                System.out.println(" dit :" +message);
                //jSonMethode.readResponse(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}