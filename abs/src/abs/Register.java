package abs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Register extends user {
    JButton register;
    JLabel nLabel,dLabel,uLabel,pLabel,status;
    JTextField n,d;
    JTextField u1,p1;
    JFrame registerFrame;
    JPanel registerPanel;
  
    public Register(){
        
    }
    void register() {
        register = new JButton("Register");
        n=new JTextField(6);
        u1=new JTextField(6);
        d=new JTextField(6);
        p1=new JTextField(6);
        nLabel=new JLabel("Name:",JLabel.LEFT);
        dLabel=new JLabel("DOB:",JLabel.LEFT);
        uLabel=new JLabel("Username:",JLabel.LEFT);
        pLabel=new JLabel("Password:",JLabel.LEFT);
        status=new JLabel("",JLabel.LEFT);
        registerFrame=new JFrame();
        registerPanel=new JPanel();
        registerFrame.setSize(600,600);
        registerFrame.setLayout(new FlowLayout());
        register.addActionListener((ActionEvent event) -> {
            name=n.getText();username=u1.getText();password=p1.getText();dob=d.getText();
            if(name.equals("")||dob.equals("")||username.equals("")||password.equals("")){
                status.setText("Please fill the details correctly");
            }
            else{
                DataFetch d2=new DataFetch();
                d2.fetchData();
                try{
                    d2.rs2.next();
                    int i=d2.rs2.getInt(1)+1;
                    d2.stmt.executeUpdate("INSERT INTO user VALUES ('"+i+"','"+name+"','"+username+"','"+password+"','"+dob+"')");
                    status.setText("Registered Successfully");
                    d2.con.close();
                    
                    
                    n.setText("");d.setText("");u1.setText("");p1.setText("");
                    registerFrame.dispose();
                    LoginPage l=new LoginPage();
                    l.in();
                }
                catch(SQLException e){
                    System.out.println(e);
                }    
            }
        });
        
        registerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        }); 
        
        registerPanel.setLayout(new GridLayout(6,1));
        registerPanel.add(nLabel);
        registerPanel.add(n);
        registerPanel.add(dLabel);
        registerPanel.add(d);
        registerPanel.add(uLabel);
        registerPanel.add(u1);
        registerPanel.add(pLabel);
        registerPanel.add(p1);
        registerPanel.add(register);
        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
    }
    
}