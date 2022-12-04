package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server_Back extends Thread {
	
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	Vector<ReceiveInfo> ClientList = new Vector<ReceiveInfo>();
	ServerSocket serversocket;
	Socket socket;
	private ServerGui servergui;
	private String msg;
	static int count_clients = 0;
	
	public void setGui(ServerGui servergui) {
		this.servergui = servergui;
	}
	
	public void setting(int server_port) {
		try {
			serversocket = new ServerSocket(server_port);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
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
	
	 // 클라이언트 추가
    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage(nick + "님이 접속하셨습니다.\n");
        clientsMap.put(nick, out);
    }
    // 클라이언트 삭제
    public void removeClient(String nick) {
        sendMessage(nick + "님이 나가셨습니다.\n");
        clientsMap.remove(nick);
    }   
    
    // 메세지 보내기
	public void sendMessage(String msg) {
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
	
	// 쓰레드
	class ReceiveInfo extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		String nick;
		
		public ReceiveInfo(Socket socket) throws IOException {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			nick = in.readUTF();
			addClient(nick, out);
		}
		
		public void run() {
			try {				
				while (in != null) {
	                msg = in.readUTF();
	                sendMessage(msg);
	                servergui.appendMsg(msg);
				}
			} catch (Exception e){
				removeClient(nick);
                count_clients -= 1;
                sendMessage("0;인원수:" + count_clients);
			}
		}
		
		
	}
}
