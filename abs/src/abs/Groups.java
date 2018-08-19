package abs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Groups {
	JButton createBtn,addBtn,showUsersBtn,showGroupUsersBtn;
    JFrame f;
    JLabel l,l1,l2,l3,l4,l5;
    JTextField t,t1,t2,t3,t4;
    JScrollPane sp1,sp2;
    JTextArea textArea1,textArea2;
    String n,u,data,status;
    void CreateGroup(){
        t=new JTextField(20);
        t1=new JTextField(20);
        t2=new JTextField(20);
        t3=new JTextField(20);
        t4=new JTextField(20);
        l=new JLabel("Group Name");
        l1=new JLabel("Name");
        l2=new JLabel("Username");
        l3=new JLabel("Group Name");
        l4=new JLabel("Group Name");
        l5=new JLabel();
        createBtn=new JButton("Create");
        addBtn=new JButton("Add");
        showUsersBtn=new JButton("Users");
        showGroupUsersBtn=new JButton("Group Users");
        textArea1=new JTextArea(20,5);
        textArea2=new JTextArea(20,5);
        sp1=new JScrollPane(textArea1);
        sp2=new JScrollPane(textArea2);
        f=new JFrame();
        f.setSize(800,800);
        f.setLayout(new GridLayout(14,2));
        f.setVisible(true);
        createBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                create();
                System.out.println("Group created successfully");
                
            }
        });   
        showUsersBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                show();
            }
        });
        showGroupUsersBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textArea2.setText("");
                try {
                    DataFetch dg=new DataFetch();
                    dg.fetchData();
                    String query="SELECT * FROM "+t4.getText()+";";
                    dg.rs2=dg.stmt.executeQuery(query);
                    String data;
                    while(dg.rs2.next()){
                        data=dg.rs2.getString("MembersName")+" "+dg.rs2.getString("MembersUsername")+"\n";
                        textArea2.setLineWrap(true);
                        textArea2.append(data);
                    }
                } catch (SQLException ex3) {
                    System.out.println(ex3);
                }
            }
        });
        addBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                DataFetch df1=new DataFetch();
                df1.fetchData();
                String query1="INSERT INTO "+t3.getText()+" VALUES ('"+t1.getText()+"' , '"+t2.getText()+"' );";
                try{
                    df1.stmt.executeUpdate(query1);
                    status="User Added";
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
                try {
                    df1.con.close();
                } catch (SQLException ex4) {
                    System.out.println(ex4);
                }
            }
        });
        f.add(l);
        f.add(t);
        f.add(createBtn);
        f.add(showUsersBtn);
        f.getContentPane().add(sp1);
        f.add(l3);
        f.add(t3);
        f.add(l1);
        f.add(t1);
        f.add(l2);
        f.add(t2);
        f.add(addBtn);
        f.add(l5);
        f.add(l4);
        f.add(t4);
        f.add(showGroupUsersBtn);
        f.getContentPane().add(sp2);
    }
    void show(){
        try{
                    DataFetch d=new DataFetch();
                    d.fetchData();
                    textArea1.setText("");
                    while(d.rs2.next()){
                        n=d.rs2.getString("name");
                        u=d.rs2.getString("username");
                        data=n+"  "+u+"\n";
                        textArea1.setLineWrap(true);
                        textArea1.append(data);
                    }
                    d.con.close();
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
    }
    
    void create(){
        DataFetch df=new DataFetch();
        df.fetchData();
        try{
            String query= "CREATE TABLE "+t.getText() +
                       " (MembersName varchar(20), " +
                       " MembersUsername varchar(20));"; 
            df.stmt.executeUpdate(query);
        }
        catch(Exception ex){
             System.out.println(ex);       
        }
        try {
            df.con.close();
        } catch (SQLException ex5) {
            System.out.println(ex5);
        }
    }
}
