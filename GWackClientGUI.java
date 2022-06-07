import javax.lang.model.util.AbstractAnnotationValueVisitor14;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.awt.geom.*; 
import java.io.*;

public class GWackClientGUI extends JFrame{

    protected JTextField Name;
    protected volatile boolean connected = false;
    protected JTextField IP;
    protected JTextField Port;
    protected JComboBox theme;
    protected TextArea Messages;
    protected TextArea compose;
    protected String username;
    protected TextArea Members;
    protected String inport;
    protected String ip_address;
    protected Networking network;


    public GWackClientGUI (){
        super();
        this.setTitle("｡･ﾟﾟ･✴GWackChannel✴･ﾟﾟ･｡");
        this.setSize(300,400);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout());

        Name = new JTextField("", 3);
        IP = new JTextField("", 3);
        Port = new JTextField(inport, 3); // input port?
        String themes[] = { "#general", "#studyhall", "#gaming" };
        theme = new JComboBox<>(themes);

        Name.setForeground(Color.magenta);
        IP.setForeground(Color.magenta);
        Port.setForeground(Color.magenta);


        Messages = new TextArea(10, 36);
        compose = new TextArea(10, 36);
        Members = new TextArea(10, 18);
        JButton connect = new JButton("Connect");
        Messages.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        compose.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Members.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        

        //top panel section
        topPanel.add(new JLabel("Name"));
        topPanel.add(Name);
        topPanel.add(new JLabel("IP Address"));
        topPanel.add(IP);
        topPanel.add(new JLabel("Port"));
        topPanel.add(Port);
        topPanel.add(new JLabel("Pick Theme"));
        topPanel.add(theme);
        topPanel.add(connect);
        this.add(topPanel, BorderLayout.NORTH);
        topPanel.setBackground(Color.lightGray);
        //end top panel section

        //start mid panel section
        JPanel MidPanel = new JPanel(new FlowLayout());
        MidPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        MidPanel.setBackground(Color.pink);
        MidPanel.add(new JLabel("Messages"));
        MidPanel.add(Messages, BorderLayout.NORTH);
        MidPanel.add(new JLabel("Members Online"));
        MidPanel.add(Members, BorderLayout.WEST);
        MidPanel.add(new JLabel("Compose"));
        MidPanel.add(compose, BorderLayout.SOUTH);
        //end mid panel section

        JPanel DownPanel = new JPanel(new FlowLayout());
        JButton send = new JButton("Send");
        JButton busy = new JButton("Can't talk, busy :/ ");
        JButton greet = new JButton("Hi! :) ");
        JButton bye = new JButton("TTYL :D ");
        DownPanel.add(send, BorderLayout.SOUTH);
        DownPanel.add(busy, BorderLayout.WEST);
        DownPanel.add(greet, BorderLayout.EAST);
        DownPanel.add(bye, BorderLayout.CENTER);
        DownPanel.setBackground(Color.lightGray);

       
        this.add(MidPanel, BorderLayout.CENTER);
        this.add(DownPanel, BorderLayout.SOUTH);
        this.pack();

        bye.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent bye){
                
                if (network != null){
                    network.sendBye();
                }
            }
        });

        greet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent bye){
                
                if (network != null){
                    network.sendGreet();
                }
            }
        });

        busy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent bye){
                
                if (network != null){
                    network.sendBusy();
                }
            }
        });

        

         this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                network.sendMessage();

            }
        });
        



        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent o){

                if(connect.getText() == "Connect"){
                    setTitle("｡･ﾟﾟ･✴GWack -- GW Slack Simulator (connected)✴･ﾟﾟ･｡");
                    connect.setText("Disconnect");
                    Messages.setEditable(false);
                    Members.setEditable(false);
                    IP.setEditable(false);
                    Port.setEditable(false);
                    Name.setEditable(false);
                    theme.setEditable(false);
                    username = Name.getText().toLowerCase();
                    ip_address = IP.getText();
                    network = new Networking(username, Integer.parseInt(Port.getText()), ip_address, GWackClientGUI.this);
                    network.start();

                    send.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                            network.sendMessage();
                        }
                    });
 
                }   else if (connect.getText() == "Disconnect") {
                
                    JOptionPane.showMessageDialog(GWackClientGUI.this, "You have disconnected",
                                "ERROR", JOptionPane.ERROR_MESSAGE);

                    network.clearOnline();
                    Messages.setEditable(true);
                    Members.setEditable(true);
                    IP.setEditable(true);
                    Port.setEditable(true);
                    Name.setEditable(true);
                    theme.setEditable(true);
                    Messages.setText("");
                    Name.setText("");
                    IP.setText("");
                    Port.setText("");
                    connect.setText("Connect");

                    
                   
                }

            }
        });

       

    }

    }

