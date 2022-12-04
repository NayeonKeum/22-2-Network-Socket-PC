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
    private JTextArea jta = new JTextArea(40, 25); // ��ȭ ��Ÿ���� â
    private JTextField jtf = new JTextField(25); // ���� �ؽ�Ʈ �Է��ϴ� ĭ
    private JButton jbtn_sensor = new JButton("���� �溸 �߷�"); // ���� �溸�߷� ��ư
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
        setTitle("���� : port : " + server_port);
        
        // ���� �溸�߷� ��ư �׼�
        jbtn_sensor.addActionListener(new ActionListener() {
    		@Override
    	    public void actionPerformed(ActionEvent e) {
    			String location = jtf.getText();
    			if (!location.equals("")) {
        			server.sendMessage("5;����:������ ����� �����Ͽ����ϴ�.");
    				server.sendMessage("6;��ġ:https://www.google.co.kr/maps/place/"+location);
    	        	jtf.setText("");
    			}
    	    }
    	});
 		
        // ���� ����
        server = new Server_Back();
        server.setGui(this);
        server.setting(server_port);
        //server.start();
        
//        Thread thread = new Thread(server);
//        thread.start();
        
    }
 
    	
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "0;����:" + jtf.getText();
        System.out.print(msg);
        appendMsg(msg);
        
        server.sendMessage(msg);
		
        jtf.setText("");
    }
 
    public void appendMsg(String msg) {
        jta.append(msg + '\n');
    }
    
 
}