package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
 
import javax.swing.*;

import server.ServerGui;

import java.awt.*;

public class ClientGui {
 
    private static final long serialVersionUID = 1L;

    static ClientBackground client;
    static String nickName;
    static String user_type;
    
    static int client_port = 0;
 
    public ClientGui(int client_port, String user_type, String nickName) {
    	ClientGui.client_port = client_port;
    	ClientGui.nickName = nickName;
    	ClientGui.user_type = user_type;
    	
    	
    	window f = new window();
 
    	client = new ClientBackground();
        client.setGui(this);
        client.setNickname(nickName);
        client.connet();
        
        Thread thread = new Thread(client);
        thread.start();
    }
 
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
////        do {
////        	System.out.print("역할을 입력하세요(1:중앙관리본부/2:지역소방본부/3:소방대원) : ");
////        	user_type = scanner.nextLine();
////        } while(!(user_type.equals("1") || user_type.equals("2") || user_type.equals("3")));
////        do {
////        	System.out.print("4자리 이하의 닉네임을 입력하세요 : ");
////        	nickName = scanner.nextLine();
////        } while(nickName.length() > 4 || nickName.contains(":") || nickName.contains(";")
////        		|| nickName.equals("") || nickName.equals("서버") 
////        		|| nickName.equals("인원수"));
//        
//        try {
//	        do {
//	        	System.out.print("port 번호 : ");
//	        	client_port = Integer.valueOf(scanner.nextLine()).intValue();
//	        } while(!isInPortList(client_port));
//	        
//	        scanner.close();
//	        new ClientGui();
//    	} catch (Exception e) {
//    		System.err.print(e);
//
//            scanner.close();
//
//            System.exit(1);
//    	}
//    }
// 
//    private static boolean isInPortList(int port_num) {
//    	
//    	File file = new File("portlist.txt");
//    	String fileText = ""; //= ReadFileText(file);
//    	int nBuffer;  
//    	try 
//    	{
//    		BufferedReader buffRead = new BufferedReader(new FileReader(file));
//
//    		while ((nBuffer = buffRead.read()) != -1) {
//    			fileText += (char)nBuffer;
//    			}
//    		buffRead.close();
//       } catch (Exception ex) {
//    	   System.out.println(ex.getMessage());
//       }  
//
//    	
//    	String[] ports = fileText.split(",");
//    	
//    	if(file.exists()){
//        	for (int i=0; i < ports.length; i++) {
//    	    	if (ports[i].equals(port_num+"")) {
//    	    		return true;
//    	    	}
//        	}
//    	} else return false;
//    	
//    	
//		return false;
//    	
//    }

 
}