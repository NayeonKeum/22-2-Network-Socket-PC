package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server_Back_backup {
	
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	Vector<ReceiveInfo> ClientList = new Vector<ReceiveInfo>();
	ServerSocket serversocket;
	Socket socket;
	private ServerGui servergui;
	private String msg;
	static int count_clients = 0;
	
	static DataOutputStream out_main;
	
	public void setGui(ServerGui servergui) {
		this.servergui = servergui;
	}
	
	public void setting(int server_port) {
		try {
			serversocket = new ServerSocket(server_port);
			
			AcceptThread acceptThread = new AcceptThread();
			acceptThread.start();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	 // 클라이언트 추가
    public void addClient(String nick, DataOutputStream out) throws IOException {
    	System.out.println("addClient (nick: " + nick + ", out: " + out + ")");  //임시, 확인용
        sendMessage(nick + "님이 접속하셨습니다.\n");
        clientsMap.put(nick, out);
    }
    // 클라이언트 삭제
    public void removeClient(String nick) {
    	System.out.println("removeClient (nick: " + nick + ")");  //임시, 확인용
        sendMessage(nick + "님이 나가셨습니다.\n");
        clientsMap.remove(nick);
    }   
    
    // 메세지 보내기
	public void sendMessage(String msg) {
    	System.out.println("sendMessage (msg: " + msg + "), Server_Back this: " + this);  //임시, 확인용
    	
    	
		Iterator<String> it = clientsMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            try {
                clientsMap.get(key).writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	// 메인 클라이언트에 메시지(port 리스트) 보내기
	public void sendPortlist() {
		try {
			out_main.writeUTF("ports:" + getPortlistString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 쓰레드
	class ReceiveInfo extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		String nick;
		
		public ReceiveInfo(Socket socket) throws IOException {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			nick = in.readUTF();
			
			if (nick.equals("ClientMain")) {
				out_main = out;
				out_main.writeUTF("ports:" + getPortlistString());
	            System.out.println("서버 : 메인 클라이언트 ready...");
	            
	            count_clients-=1;
	            sendMessage("0;인원수:" + count_clients);
			} else {
				addClient(nick, out);
			}
		}
		
		public void run() {
			try {				
				while (in != null) {
	                msg = in.readUTF();
	                sendMessage(msg);
	                servergui.appendMsg(msg);
				}
			} catch (Exception e){
				if (!nick.equals("ClientMain")) {
					removeClient(nick);
	                count_clients -= 1;
	                sendMessage("0;인원수:" + count_clients);
				}
			}
		}
		
		
	}
	
	class AcceptThread extends Thread {
		public void run() {
			try {
				while (true) {
					System.out.println("서버 대기중...");
					socket = serversocket.accept();
					ReceiveInfo receive = new ReceiveInfo(socket);
					receive.start();
					
					count_clients+=1;
		            sendMessage("0;인원수:" + count_clients);
				} 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
private static String getPortlistString() {
    	
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
    	
    	return fileText;
    }
}
