package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
 
public class ServerBackground {
    private ServerSocket serverSocket;
    private Socket socket;
    private ServerGui gui;
    private String msg;
    static int count_clients = 0;
    
    static int server_port = ServerGui.server_port;
    
    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
 
    public final void setGui(ServerGui gui) {
        this.gui = gui;
    }
 
    public void setting() throws IOException {
        Collections.synchronizedMap(clientsMap);
        serverSocket = new ServerSocket(server_port);
        while (true) {
            System.out.println("���� �����...");
            socket = serverSocket.accept();
            Receiver receiver = new Receiver(socket);
            receiver.start();
            count_clients+=1;
            sendMessage("0;�ο���:" + count_clients);
        }
    }
 
    public static void main(String[] args) throws IOException {
        ServerBackground serverBackground = new ServerBackground();
        serverBackground.setting();
    }
 
    // Ŭ���̾�Ʈ �߰�
    public void addClient(String nick, DataOutputStream out) throws IOException {
        sendMessage(nick + "���� �����ϼ̽��ϴ�.\n");
        clientsMap.put(nick, out);
    }
    // Ŭ���̾�Ʈ ����
    public void removeClient(String nick) {
        sendMessage(nick + "���� �����̽��ϴ�.\n");
        clientsMap.remove(nick);
    }
 
    // �޽��� ����
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
 
    class Receiver extends Thread {
        private DataInputStream in;
        private DataOutputStream out;
        private String nick;
 
        public Receiver(Socket socket) throws IOException {
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
                    gui.appendMsg(msg);
                }
            } catch (IOException e) {
                // ������������ ����
                removeClient(nick);
                count_clients -= 1;
                sendMessage("0;�ο���:" + count_clients);
            }
        }
    }
}