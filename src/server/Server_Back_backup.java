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
	
	 // Ŭ���̾�Ʈ �߰�
    public void addClient(String nick, DataOutputStream out) throws IOException {
    	System.out.println("addClient (nick: " + nick + ", out: " + out + ")");  //�ӽ�, Ȯ�ο�
        sendMessage(nick + "���� �����ϼ̽��ϴ�.\n");
        clientsMap.put(nick, out);
    }
    // Ŭ���̾�Ʈ ����
    public void removeClient(String nick) {
    	System.out.println("removeClient (nick: " + nick + ")");  //�ӽ�, Ȯ�ο�
        sendMessage(nick + "���� �����̽��ϴ�.\n");
        clientsMap.remove(nick);
    }   
    
    // �޼��� ������
	public void sendMessage(String msg) {
    	System.out.println("sendMessage (msg: " + msg + "), Server_Back this: " + this);  //�ӽ�, Ȯ�ο�
    	
    	
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
	
	// ���� Ŭ���̾�Ʈ�� �޽���(port ����Ʈ) ������
	public void sendPortlist() {
		try {
			out_main.writeUTF("ports:" + getPortlistString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// ������
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
	            System.out.println("���� : ���� Ŭ���̾�Ʈ ready...");
	            
	            count_clients-=1;
	            sendMessage("0;�ο���:" + count_clients);
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
	                sendMessage("0;�ο���:" + count_clients);
				}
			}
		}
		
		
	}
	
	class AcceptThread extends Thread {
		public void run() {
			try {
				while (true) {
					System.out.println("���� �����...");
					socket = serversocket.accept();
					ReceiveInfo receive = new ReceiveInfo(socket);
					receive.start();
					
					count_clients+=1;
		            sendMessage("0;�ο���:" + count_clients);
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
