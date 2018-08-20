package abs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Writer{
	static InetAddress IP=null;
    static OutputStream os=null;
    static OutputStreamWriter osw=null;
    static BufferedWriter bw=null;
    static Socket serverSocket=null; 
}

class ReadMSG implements Runnable{
    Socket serverSocket=null;
    String[] tokens;
    JTextArea textArea;
    JFrame loginFrame;
    @Override
    public void run() {
        try {
            InputStream is = serverSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            char[] msg1 = new char[1024];
            while(true){  
                try{Thread.sleep(200);}catch(Exception e){}
                int status=0;
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
                tokens = s1.split(delims);
                if(tokens[3].equals("Logged inn Established")) {
                	loginFrame.dispose();
                    OnlineUsers o1=new OnlineUsers(loginFrame,serverSocket);
                    o1.onlineUser();
                }
                else {
                	textArea.append(tokens[2]+" : "+tokens[3]+"\n");
                }
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ChatReadMSG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void acceptConnection(Socket serverSocket,JTextArea t,JFrame loginFrame){
        this.serverSocket=serverSocket;
        textArea=t;
        this.loginFrame=loginFrame;
    }
}

public class LoginPage extends user{
	JButton loginBtn,registerPage;
    JLabel u,p,status;
    JTextField u1,p1;
    JTextArea textArea;
    JFrame loginFrame;
    JPanel panel;

     public void in() {
        //server=new Server();
        loginBtn = new JButton();
        u1=new JTextField(6);
        p1=new JTextField(6);
        u=new JLabel("Username",JLabel.LEFT);
        p=new JLabel("Password",JLabel.LEFT);
        textArea=new JTextArea();
        status=new JLabel("",JLabel.LEFT);
        registerPage=new JButton("Register");
        loginFrame=new JFrame();
        panel=new JPanel();
        loginFrame.setSize(500,600);
        loginFrame.setLayout(new FlowLayout());
        loginBtn.setText("Login");
        try {
	        Writer.IP=InetAddress.getLocalHost();
	        Writer.serverSocket = new Socket("127.0.0.1",25000);
	        Writer.os = Writer.serverSocket.getOutputStream();
	        Writer.osw = new OutputStreamWriter(Writer.os);
	        Writer.bw = new BufferedWriter(Writer.osw);
	        Writer.bw.write("CHAT:CON:::");
	        Writer.bw.flush();
        }
        catch(Exception e) {
        	System.out.println(e);
        }
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	String data="";
                String username=u1.getText(),password=p1.getText();
                if(username.equals("") || password.equals("")){
                    data="Enter correct information";
                    textArea.append(data);
                    u1.setText("");p1.setText("");
                }
                else{
                	try {
                		Writer.bw.flush();
						Writer.bw.write("CHAT:LOGIN:"+username+":"+password+":"+Writer.IP.getHostAddress());
						Writer.bw.flush();
						textArea.append("Logging in"+"\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	u1.setText("");p1.setText("");
                  }
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
        panel.add(textArea);
        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("n");
        LoginPage l=new LoginPage();
        l.in();
        ReadMSG readMsg=new ReadMSG();
        readMsg.acceptConnection(Writer.serverSocket,l.textArea,l.loginFrame);
        Thread t=new Thread(readMsg);
        t.start();
	}

}
