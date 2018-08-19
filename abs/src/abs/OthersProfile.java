package abs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OthersProfile {
	JButton profileBtn,searchBtn;
    JLabel nameLabel,usernameLabel,label,name1;
    JTextField nameText,usernameText,nameText1;
    //JPanel othersProfileListPanel,panel2;
    JFrame othersProfileFrame;
    String n,u,n2,u2,d;
    JTextArea textArea;
    JScrollPane sp;
    public String[] name,username,dob;
    
    void p(){
        profileBtn=new JButton("Profile");
        searchBtn=new JButton("Search");
        nameText=new JTextField(20);
        nameText1=new JTextField(20);
        usernameText=new JTextField(20);
        nameLabel=new JLabel("name");
        name1=new JLabel("name");
        usernameLabel=new JLabel("username");
        label=new JLabel();
        textArea=new JTextArea(6,20);
        sp=new JScrollPane(textArea);
       
        othersProfileFrame=new JFrame();
        othersProfileFrame.setLayout(new FlowLayout());
        othersProfileFrame.setSize(700,600);
        othersProfileFrame.setVisible(true);
        
       
        
        searchBtn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
                try{                
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection(  
                    "jdbc:mysql://localhost:3306/users","root","kartikay");    
                    Statement stmt=con.createStatement(); 
                    String n1=nameText1.getText();
                    textArea.setText("");
                    ResultSet rs1=null;
                    rs1=stmt.executeQuery("select count(*) from user");
                    rs1.next();
                    int i=rs1.getInt(1);
                    i=i/2;
                    name=new String[i];
                    username=new String[i];
                    dob=new String[i];
                    int j=0;
                    ResultSet rs3=null;
                    rs3=stmt.executeQuery("select name,username,dob from user");
                    while(rs3.next()){
                        n=rs3.getString("name");
                         if(n1.equals(n)){
                            u=rs3.getString("username");
                            d=rs3.getString("dob");
                            name[j]=n;
                            username[j]=u;
                            dob[j]=d;
                            String data=n+" "+u+"\n";
                            textArea.setLineWrap(true);
                            textArea.append(data);
                            //System.out.println(username[j]+name[j]+dob[j]);
                            j++;
                         }    
                    }
                }    
                catch(Exception ex){
                   System.out.println(ex);
                }               
            }    
        });
        
        
        profileBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                u2=usernameText.getText();n2=nameText.getText();
                int x=name.length;int index;
                for(index=0;index<x;index++){
                    if(u2.equals(username[index])){
                        usernameText.setText("");nameText.setText("");
                        ProfilePage p=new ProfilePage(username[index],name[index],dob[index],1);
                        if(x==1){
                            username=null;
                            name=null;
                            dob=null;
                        }
                        p.show(); 
                    }
                }   
            }
        });
        
       
        othersProfileFrame.add(name1);
        othersProfileFrame.add(nameText1);
        othersProfileFrame.add(searchBtn);
        othersProfileFrame.getContentPane().add(sp);
        othersProfileFrame.add(nameLabel);
        othersProfileFrame.add(nameText);
        othersProfileFrame.add(usernameLabel);
        othersProfileFrame.add(usernameText);
        othersProfileFrame.add(profileBtn);
        
    }
}
