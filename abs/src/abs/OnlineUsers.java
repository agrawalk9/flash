package abs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OnlineUsers extends user {
	JButton userProfileBtn,othersProfileBtn,logoutBtn,chatBtn,groupBtn;
    JTextField clientUsername;
    JLabel label;
   
    JFrame frame,loginFrame;
   Socket clientSocket;
    OnlineUsers(JFrame loginframe,Socket socket){
        loginFrame=new JFrame();
        this.loginFrame=loginframe;
        this.clientSocket=socket;
    }
    OnlineUsers(){
        
    }
    public void onlineUser(){
       
        userProfileBtn=new JButton();
        groupBtn=new JButton("Groups");
        othersProfileBtn=new JButton();
        logoutBtn=new JButton("Logout");
        userProfileBtn.setText("User");
        chatBtn=new JButton("Chat");
        clientUsername=new JTextField(10);
        othersProfileBtn.setText("Others");
        label=new JLabel();
        frame=new JFrame();
        frame.setSize(700,600);
        frame.setLayout(new FlowLayout());
        
        
        userProfileBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ProfilePage userProfile=new ProfilePage(user.username,user.dob,user.name,0);
                userProfile.show();
            }
        });
        othersProfileBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                OthersProfile othersProfile=new OthersProfile();
                othersProfile.p();
            }
        });        
        groupBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Groups group=new Groups();
                group.CreateGroup();
            }
        });
        
        logoutBtn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               try {
                   InetAddress IP=InetAddress.getLocalHost();
                   OutputStream os = clientSocket.getOutputStream(); 
                   
                   user.id=0;
                  
                   OutputStreamWriter osw = new OutputStreamWriter(os);
                   BufferedWriter bw1 = new BufferedWriter(osw);
                   //osw.flush();
                   bw1.write("CHAT:LGT:"+user.username+":"+IP.getHostAddress());
                   bw1.flush();
                   
                   clientSocket.close();
                   
                   frame.dispose();
                   loginFrame.setVisible(true);
                   
                   os.close();
               } catch (UnknownHostException ex1) {
                   System.out.println(ex1);
               } catch (IOException ex2) {
                   System.out.println(ex2);
               }
               } 
        });
        chatBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s=clientUsername.getText();
                    DataFetch d=new DataFetch();
                    d.fetchData();
                    try{
                          while(d.rs2.next()){
                              String u2=d.rs2.getString("username");
                              if(u2.equals(s)){
                                  ChatPage c=new ChatPage(clientUsername.getText(),clientSocket);
                                  c.chat();
                              }
                              
                          }
                          d.con.close();
                    }
                    catch(Exception ex){
                        System.out.println(ex);
                    }
                  
            }
        });    

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        }); 
        frame.setLayout(new FlowLayout());
        frame.add(userProfileBtn);
        frame.add(othersProfileBtn);
        frame.add(logoutBtn);
     
        frame.add(clientUsername);
        frame.add(chatBtn);
        frame.add(label);
        frame.add(groupBtn);
        frame.setVisible(true);
    }
}
