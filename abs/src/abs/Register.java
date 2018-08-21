package abs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*class RWriter {
	static InetAddress IP=null;
    static OutputStream os=null;
    static OutputStreamWriter osw=null;
    static BufferedWriter bw=null;
    static Socket serverSocket=null;
}*/

class Register implements Runnable {
    JButton register;
    String[] tokens;
    JLabel nLabel,dLabel,uLabel,pLabel,status;
    JTextField n,d;
    JTextField u1,p1;
    JFrame registerFrame;
    JPanel registerPanel;
  
    @Override
    public void run() {
    	InputStream is=null;
    	InputStreamReader isr=null;
    	BufferedReader br=null;
    	try {
            is = Writer.serverSocket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
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
                if(tokens[3].equals("Registered")) {
                	registerFrame.dispose();
                    OnlineUsers o1=new OnlineUsers();
                    o1.onlineUser();
                }
                else {
                	
                }
            }
    	}
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
            String name=n.getText(),username=u1.getText(),password=p1.getText(),dob=d.getText();
            if(name.equals("")||dob.equals("")||username.equals("")||password.equals("")){
                status.setText("Please fill the details correctly");
            }
            else{
            	try {
        	        /*Writer.IP=InetAddress.getLocalHost();
        	        Writer.serverSocket = new Socket("127.0.0.1",25000);
        	        Writer.os = Writer.serverSocket.getOutputStream();
        	        Writer.osw = new OutputStreamWriter(Writer.os);
        	        Writer.bw = new BufferedWriter(Writer.osw);*/
        	        Writer.bw.write("CHAT:REGISTER:"+name+":"+username+":"+password+":"+dob);
        	        Writer.bw.flush();
                }
            	catch(Exception e) {
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
        
        Thread t=new Thread();
        t.start();
        
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
        registerPanel.add(status);
        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
    }
}