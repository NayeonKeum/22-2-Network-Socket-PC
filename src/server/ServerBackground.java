package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
 
public class ServerBackground {
//    private ServerSocket serverSocket;
//    private Socket socket;
//    private ServerGui gui;
//    private String msg;
//    static int count_clients = 0;
//    
//    static int server_port = ServerGui.server_port;
//    
//    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
//    
//
//	static DataOutputStream out_main;
// 
//    public final void setGui(ServerGui gui) {
//        this.gui = gui;
//    }
// 
//    public void setting() throws IOException {
//        Collections.synchronizedMap(clientsMap);
//        serverSocket = new ServerSocket(server_port);
//        while (true) {
//            System.out.println("���� �����...");
//            socket = serverSocket.accept();
//            Receiver receiver = new Receiver(socket);
//            receiver.start();
//            count_clients+=1;
//            sendMessage("0;�ο���:" + count_clients);
//        }
//    }
// 
//    public static void main(String[] args) throws IOException {
//        ServerBackground serverBackground = new ServerBackground();
//        serverBackground.setting();
//    }
// 
//    // Ŭ���̾�Ʈ �߰�
//    public void addClient(String nick, DataOutputStream out) throws IOException {
//        sendMessage(nick + "���� �����ϼ̽��ϴ�.\n");
//        clientsMap.put(nick, out);
//    }
//    // Ŭ���̾�Ʈ ����
//    public void removeClient(String nick) {
//        sendMessage(nick + "���� �����̽��ϴ�.\n");
//        clientsMap.remove(nick);
//    }
// 
//    // �޽��� ����
//    public void sendMessage(String msg) {
//        Iterator<String> it = clientsMap.keySet().iterator();
//        String key = "";
//        while (it.hasNext()) {
//            key = it.next();
//            try {
//                clientsMap.get(key).writeUTF(msg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    
// // ���� Ŭ���̾�Ʈ�� �޽���(port ����Ʈ) ������
// 	public void sendPortlist() {
// 		try {
// 			out_main.writeUTF("ports:" + getPortlistString());
// 		} catch (IOException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
// 	}
// 
//    class Receiver extends Thread {
//        private DataInputStream in;
//        private DataOutputStream out;
//        private String nick;
// 
//        public Receiver(Socket socket) throws IOException {
//            out = new DataOutputStream(socket.getOutputStream());
//            in = new DataInputStream(socket.getInputStream());
//            nick = in.readUTF();
//            
//            if (nick.equals("ClientMain")) {
//				out_main = out;
//				out_main.writeUTF("ports:" + getPortlistString());
//	            System.out.println("���� : ���� Ŭ���̾�Ʈ ready...");
//	            
//	            count_clients-=1;
//	            sendMessage("0;�ο���:" + count_clients);
//			} else {
//				addClient(nick, out);
//			}
//        }
// 
//        public void run() {
//            try {
//                while (in != null) {
//                    msg = in.readUTF();
//                    sendMessage(msg);
//                    gui.appendMsg(msg);
//                }
//            } catch (IOException e) {
//                // ������������ ����
//            	if (!nick.equals("ClientMain")) {
//	                removeClient(nick);
//	                count_clients -= 1;
//	                sendMessage("0;�ο���:" + count_clients);
//            	}
//            }
//        }
//    }
//    
//    private static String getPortlistString() {
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
//    	return fileText;
//    }
}