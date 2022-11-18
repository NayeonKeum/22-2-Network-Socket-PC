package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.ClientGui;

import javax.swing.JButton;
 
public class ServerGui extends JFrame implements ActionListener {
 
    private static final long serialVersionUID = 1L;
    private JTextArea jta = new JTextArea(40, 25);
    private JTextField jtf = new JTextField(25);
    private JButton jbtn = new JButton("port 닫기");
    private ServerBackground server;
    static int server_port = 0;
    
 
    public ServerGui() throws IOException {
        
        add_port(server_port);
    	
    	jbtn.addActionListener(new ActionListener() {
    		@Override
    	    public void actionPerformed(ActionEvent e) {
        		del_port(server_port);
        		System.exit(0);
    	    }
    	});
 
    	add(jbtn, BorderLayout.NORTH);
        add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);
        
 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setBounds(200, 100, 400, 600);
        setTitle("서버 : port : " + server_port);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		del_port(server_port);
        		System.exit(0);
        	}
        });
 
        server = new ServerBackground();
        server.setGui(this);
        server.setting();
    }
 
    public static void main(String[] args) throws IOException {
    	Scanner scanner = new Scanner(System.in);
    	try {
	        do {
	        	System.out.print("port 번호 : ");
	        	server_port = Integer.valueOf(scanner.nextLine()).intValue();
	        } while(server_port < 5000 || server_port > 65000 || isAlreadyExist(server_port));
	        
	        scanner.close();
	        new ServerGui();
    	} catch (Exception e) {
    		server_port = 8790;
            scanner.close();
            new ServerGui();
    	}
    	
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "서버:" + jtf.getText();
        System.out.print(msg);
        server.sendMessage(msg);
        jtf.setText("");
    }
 
    public void appendMsg(String msg) {
        jta.append(msg + '\n');
    }
    
    
    private static boolean isAlreadyExist(int port_num) {
    	File file = new File("portlist.txt");
    	String fileText = ""; //= ReadFileText(file);
    	int nBuffer;  
    	try 
    	{
    		BufferedReader buffRead = new BufferedReader(new FileReader(file));

    		while ((nBuffer = buffRead.read()) != -1) {
    			fileText += (char)nBuffer;
    			}
    		buffRead.close();
       } catch (Exception ex) {
    	   System.out.println(ex.getMessage());
       }
    	
    	String[] ports = fileText.split(",");
    	
    	if(file.exists()){
        	for (int i=0; i < ports.length; i++) {
    	    	if (ports[i].equals(port_num+"")) {
    	    		return true;
    	    	}
        	}
        	return false;
    	} else {
    		return false;
    	}
    	
    }
    
    
    private void add_port(int port_num) {
    	try{
    		String data = server_port + ",";
 
    		File file =new File("portlist.txt");
 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file.getName(),true); //뒤에 덧붙이기
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(data);
    	        bufferWritter.close();
 
	        System.out.println("ADD PORT TO FILE Done");
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    private void del_port(int port_num) {
    	File file = new File("portlist.txt");
    	String fileText = ""; //= ReadFileText(file);
    	int nBuffer;  
    	try 
    	{
    		BufferedReader buffRead = new BufferedReader(new FileReader(file));

    		while ((nBuffer = buffRead.read()) != -1) {
    			fileText += (char)nBuffer;
    			}
    		buffRead.close();
       } catch (Exception ex) {
    	   System.out.println(ex.getMessage());
       }  

    	
    	String[] ports = fileText.split(",");
    	
    	if(file.exists()){
        	for (int i=0; i < ports.length; i++) {
    	    	if (ports[i].equals(port_num+"")) {
    	    		System.out.println("port 번호 " + ports[i] + "삭제");
    	    		ports[i] = "";
    	    	}
        	}
			
	    	try { 
		    	BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
		        // 파일 쓰기
		    	for (String s : ports) {
		    		if (!s.equals(""))
		    			buffWrite.write(s+",", 0, (s+",").length());
		    	}
		        // 파일 닫기  
		        buffWrite.flush();  
		        buffWrite.close();
	        } catch (IOException ex) {
	        	System.out.println(ex.getMessage());
	        }
    	}
    } 
 
}