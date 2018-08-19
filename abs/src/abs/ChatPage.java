package abs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ChatReadMSG implements Runnable{
    Socket s=null;
    String[] tokens1;
    JTextArea textArea2;
    @Override
    public void run() {
        try {
            InputStream is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            char[] msg1 = new char[1024];
            while(true){  
                try{Thread.sleep(200);}catch(Exception e){}
                int status=0;
                // Check tokens for FROMMSG
                try  {
                    System.out.println("status before:"+status);
                    status = br.read(msg1,0,1024);
                    System.out.println("status1");
                    System.out.println("status after:"+status);
                }
                catch(Exception e){
                    System.out.println(e);
                    return;
                }
                System.out.println("Read "+status); 
                //textArea2.setLineWrap(true);
                String s1 = new String(String.valueOf(msg1).trim()); 
                String delims = "[:]";
                tokens1 = s1.split(delims);
                textArea2.append(tokens1[2]+" : "+tokens1[3]+"\n");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ChatReadMSG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void acceptConnection(Socket soc,JTextArea t){
        s=soc;
        textArea2=t;
    }  
}

class ChatPage extends user{
    JFrame chatFrame;
    int z=0;
    JPanel chatPanel;
    public JTextArea textArea1;
    static JTextArea textArea2;
    JButton sendBtn;
    JScrollPane sp;
    String clientUsername,data;
    Socket clientSocket;
    OutputStream os=null;
    OutputStreamWriter osw=null;
    BufferedWriter bw=null;
    ChatPage(String username,Socket socket){
        clientUsername=username;
        this.clientSocket=socket;
    }
    void chat(){
        chatPanel=new JPanel();
        chatFrame=new JFrame();
        textArea2=new JTextArea(6,20);
        sp=new JScrollPane(textArea2);
        textArea1=new JTextArea(2,10);
        sendBtn=new JButton("Send");
        chatFrame.setLayout(new FlowLayout());
        chatFrame.setVisible(true);
        chatFrame.setSize(500,500);
        
        ChatReadMSG chatReadMSG=new ChatReadMSG();
        try{
            chatReadMSG.acceptConnection(clientSocket,textArea2);
        }
        catch(Exception e){
            System.out.println(e);
        }
        Thread t=new Thread(chatReadMSG);
        t.start();
        
        try{
            os = clientSocket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
        }            
        catch(Exception e){
            System.out.println(e);
        }
        sendBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //textArea2.setLineWrap(true);
                String msg=textArea1.getText(),message="";
                textArea1.setText("");
                String MSG="MSG",CHAT="CHAT";
                //while(true){
                    if(!msg.equals("")){
                        try {
                            bw.flush();
                            bw.write(CHAT+":"+MSG+":"+clientUsername+":"+msg);
                            System.out.println("Message sent to the server is "+msg);
                            message=msg;
                            msg="";
                            bw.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(ChatPage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    textArea2.append(user.username+" : "+message+"\n");
                    textArea1.setText("");                
                //}
            }        
        });
        chatFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });
        chatPanel.setLayout(new FlowLayout());
        chatPanel.add(textArea1);
        chatPanel.add(sendBtn);
        chatFrame.add(chatPanel);
        chatFrame.getContentPane().add(sp);
    }
}
