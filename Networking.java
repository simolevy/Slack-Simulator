import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.jar.Attributes.Name;
import java.net.*;
import java.util.Random;
import java.lang.Thread;

public class Networking extends Thread {

// send in pw.println(SECRET INFO) 
// call reading thread (will read from buffered reader)
// write message from GUI using the printwriter to send to the server.
// use buffered reader to get string from server. display
// writing is inverse of that ^^^
// take what is in the GUI and use print writer to send (writing)
// ssh-cs2113.adamaviv.com

private BufferedReader buff;
private String online="";
private PrintWriter pw;
private GWackClientGUI GUI;
private String username;
private Socket socket = null;
private Integer port;
private String IP;
private ServerSocket ss = null; // socket 4 new clients
   

    public Networking(String username, Integer port, String IP, GWackClientGUI GUI){ 
        this.username = username;
        this.port = port;
        this.IP = IP;
        this.GUI = GUI;
        // start 2 connect.
        try {
        this.socket = new Socket(IP, port);
      

        } catch(Exception e){
            JOptionPane.showMessageDialog(GUI, "Connection failed.",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                                
                
                System.exit(1);
        }

    }

    
        public void run(){

            try{
  
                pw = new PrintWriter(socket.getOutputStream());
                buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //System.out.println("" + IP);
                //System.out.println("" + port);
                //System.out.println("" + username);
                pw.println("SECRET");
                pw.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
                pw.println("NAME");
                pw.println("✼" + GUI.Name.getText().toLowerCase() + "❃");  // user name
                pw.flush();

                while(true){

                    
                    String message = buff.readLine();
                    if(message.equals("START_CLIENT_LIST")){
                        message = buff.readLine();
                        //pw.write(message);
                        //clientList.add(message);
                        while(!(message.equals("END_CLIENT_LIST"))) {
                            online += message + "\n"; //only the client name?
                            message = buff.readLine();
                        }

                        GUI.Members.setText(online);
                        } else {

                            this.GUI.Messages.append(message + "\n");

                        }
                }                
                

            }catch(Exception e){
                //e.printStackTrace();

                JOptionPane.showMessageDialog(GUI, "Client adding failure!",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                
                System.exit(1);
            }

            
            
        }


   

    public BufferedReader getbuff(){
        return this.buff;
    }

    public PrintWriter getpw(){
        return this.pw;
    }

    public void sendBye(){
        //System.out.println("reach?");
       
        String s = "TTYL :D";
        this.pw.write(s);
        this.pw.flush();
        this.GUI.Messages.append("[✼" + username  + "✼] " + s);
        this.GUI.Messages.append("\n");

    }

    public void sendGreet(){
        //System.out.println("reach?");
       
        String s = "Hi! :) ";
        this.pw.write(s);
        this.pw.flush();
        this.GUI.Messages.append("[✼" + username  + "✼] " + s);
        this.GUI.Messages.append("\n");

    }

    public void sendBusy(){
        //System.out.println("reach?");
       
        String s = "Can't talk, busy :/ ";
        this.pw.write(s);
        this.pw.flush();
        this.GUI.Messages.append("[✼" + username  + "✼] " + s);
        this.GUI.Messages.append("\n");

    }

    public void clearOnline(){
        online ="";
        GUI.Members.setText("");

    }


    public void sendMessage() {
        String s = GUI.compose.getText();
        System.out.println(s);
        this.pw.write(s);
        this.pw.flush();
        this.GUI.Messages.append("[✼" + username  + "✼] " + s);
        this.GUI.Messages.append("\n");
        this.GUI.compose.setText("");
    }

    private void getMessage(){
        String cmsg;
        try{
            while((cmsg = buff.readLine()) != null){
                pw.write(cmsg);
                pw.flush();
            }
        }catch(Exception gmsg){}
        
    }

}



    

