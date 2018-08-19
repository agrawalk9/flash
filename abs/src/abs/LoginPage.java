package abs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPage extends user{
	JButton loginBtn,registerPage;
    JLabel u,p,status;
    JTextField u1,p1;
    JFrame loginFrame;
    JPanel panel;
    static Socket clientSocket ;

     public void in() {
        //server=new Server();
        loginBtn = new JButton();
        u1=new JTextField(6);
        p1=new JTextField(6);
        u=new JLabel("Username",JLabel.LEFT);
        p=new JLabel("Password",JLabel.LEFT);
        status=new JLabel("",JLabel.LEFT);
        registerPage=new JButton("Register");
        loginFrame=new JFrame();
        panel=new JPanel();
        loginFrame.setSize(500,600);
        loginFrame.setLayout(new FlowLayout());
        loginBtn.setText("Login");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String username=u1.getText(),password=p1.getText();String s1=null;
                if(u1.getText().equals(" ") || p1.getText().equals(" ")){
                    String data="Enter correct information";
                    status.setText(data);
                }
                else{
                    DataFetch d1=new DataFetch();
                    d1.fetchData();
                     try{
                            while(d1.rs2.next()){
                            String u2=d1.rs2.getString("username");
                            String p2=d1.rs2.getString("password");
                            if(u2.equals(username)){
                                if(p2.equals(password)){
                                    user.username=d1.rs2.getString("username");
                                    user.id=1;
                                    user.name=d1.rs2.getString("name");
                                    user.dob=d1.rs2.getString("dob");
                                    //System.out.println("Name");
                                    InetAddress IP=InetAddress.getLocalHost();
                                    //System.out.println("Name");
                                    clientSocket = new Socket("127.0.0.1",25000);
                                   //System.out.println("Name");
                                    OutputStream os = clientSocket.getOutputStream();
                                    OutputStreamWriter osw = new OutputStreamWriter(os);
                                    BufferedWriter bw1 = new BufferedWriter(osw);
                                    try{
                                        bw1.write("CHAT:CON:"+u2+":"+IP.getHostAddress());
                                        bw1.flush();
                                    }
                                    catch(IOException e){
                                         System.out.println(e);
                                    }
                                    u1.setText("");p1.setText("");
                                    loginFrame.dispose();
                                    OnlineUsers o1=new OnlineUsers(loginFrame,clientSocket);
                                    o1.onlineUser();
                                }
                                else{
                                    s1="Wrong Password";
                                    p1.setText("");
                                }
                            }
                            /*else{
                                s1="Wrong Username";
                                u1.setText("");p1.setText("");
                                break;
                            }*/
                        }
                        d1.con.close();

                     }
                     catch(Exception e){
                        System.out.println(e);
                     }
                }
                status.setText(s1);
            }
        });
        registerPage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loginFrame.dispose();
                Register registerFrame=new Register();
                 registerFrame.register();
                 //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

            /*private void setDefaultCloseOperation(int DISPOSE_ON_CLOSE) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }*/
        });
        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        panel.setLayout(new GridLayout(6,1));
        panel.add(u);
        panel.add(u1);
        panel.add(p);
        panel.add(p1);
        panel.add(loginBtn);
        panel.add(registerPage);
        panel.add(status);
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("n");
        LoginPage l=new LoginPage();
        l.in();

	}

}
