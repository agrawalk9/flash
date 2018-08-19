package abs;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfilePage {
	JLabel nameLabel,usernameLabel,dobLabel;
    JLabel name,username,dob;
    JPanel profilePanel;
    JFrame profileFrame;
    String u1,n1,d1;
    int j;
   
    public ProfilePage(String u,String dob,String n,int i){
        u1=u;
        n1=n;
        d1=dob;
        j=i;
    }
    void show() {
        nameLabel=new JLabel("Name");
        usernameLabel=new JLabel("Username");
        dobLabel=new JLabel("DOB");
        name=new JLabel();
        username=new JLabel();
        dob=new JLabel();
        profilePanel =new JPanel();
        profileFrame=new JFrame();
        profileFrame.setLayout(new FlowLayout());
        profileFrame.setSize(1000,1000);
        if(j==1){
            DataFetch d4=new DataFetch();
            d4.fetchData();
            try{                
                while(d4.rs2.next()){
                    if(u1.equals(d4.rs2.getString("username"))){
                        username.setText(d4.rs2.getString("username"));
                        name.setText(d4.rs2.getString("name"));
                        dob.setText(d4.rs2.getString("dob"));
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            
        }
        else if(j==0){
            username.setText(u1);
            name.setText(n1);
            dob.setText(d1);
        }
        profilePanel.setLayout(new GridLayout(3,1));
        profilePanel.add(nameLabel);
        profilePanel.add(name);
        profilePanel.add(usernameLabel);
        profilePanel.add(username);
        profilePanel.add(dobLabel);
        profilePanel.add(dob);
        profileFrame.add(profilePanel);
        profileFrame.setVisible(true);
    }
}
