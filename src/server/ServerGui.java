package server;

import java.awt.BorderLayout;
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
import server.Server_Back.ReceiveInfo;

import javax.swing.JButton;
 
public class ServerGui extends JFrame implements ActionListener {
 
    private static final long serialVersionUID = 1L;
    private JTextArea jta = new JTextArea(40, 25); // 대화 나타나는 창
    private JTextField jtf = new JTextField(25); // 보낼 텍스트 입력하는 칸
    private JButton jbtn_sensor = new JButton("센서 경보 발령"); // 센서 경보발령 버튼
    public Server_Back server;
    int server_port = 0;
    
    
    public ServerGui(int portnum) throws IOException {
    	server_port = portnum;
    	add(jbtn_sensor, BorderLayout.NORTH);
        add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);
        
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (server_port == 65001) {
        	setVisible(false);
        } else {
        	setVisible(true);
        }
        
        setBounds(600, 100, 400, 600);
        setTitle("서버 : port : " + server_port);
        
        // 센서 경보발령 버튼 액샨
        jbtn_sensor.addActionListener(new ActionListener() {
    		@Override
    	    public void actionPerformed(ActionEvent e) {
    			String location = jtf.getText();
    			if (!location.equals("")) {
        			server.sendMessage("5;서버:센서가 산불을 감지하였습니다.");
    				server.sendMessage("6;위치:https://www.google.co.kr/maps/place/"+location);
    	        	jtf.setText("");
    			}
    	    }
    	});
 		
        // 서버 실행
        server = new Server_Back();
        server.setGui(this);
        server.setting(server_port);
        //server.start();
        
//        Thread thread = new Thread(server);
//        thread.start();
        
    }
 
    	
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "0;서버:" + jtf.getText();
        System.out.print(msg);
        appendMsg(msg);
        
        server.sendMessage(msg);
		
        jtf.setText("");
    }
 
    public void appendMsg(String msg) {
        jta.append(msg + '\n');
    }
    
 
}