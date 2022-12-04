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
//            System.out.println("서버 대기중...");
//            socket = serverSocket.accept();
//            Receiver receiver = new Receiver(socket);
//            receiver.start();
//            count_clients+=1;
//            sendMessage("0;인원수:" + count_clients);
//        }
//    }
// 
//    public static void main(String[] args) throws IOException {
//        ServerBackground serverBackground = new ServerBackground();
//        serverBackground.setting();
//    }
// 
//    // 클라이언트 추가
//    public void addClient(String nick, DataOutputStream out) throws IOException {
//        sendMessage(nick + "님이 접속하셨습니다.\n");
//        clientsMap.put(nick, out);
//    }
//    // 클라이언트 삭제
//    public void removeClient(String nick) {
//        sendMessage(nick + "님이 나가셨습니다.\n");
//        clientsMap.remove(nick);
//    }
// 
//    // 메시지 전송
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
// // 메인 클라이언트에 메시지(port 리스트) 보내기
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
//	            System.out.println("서버 : 메인 클라이언트 ready...");
//	            
//	            count_clients-=1;
//	            sendMessage("0;인원수:" + count_clients);
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
//                // 사용접속종료시 에러
//            	if (!nick.equals("ClientMain")) {
//	                removeClient(nick);
//	                count_clients -= 1;
//	                sendMessage("0;인원수:" + count_clients);
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