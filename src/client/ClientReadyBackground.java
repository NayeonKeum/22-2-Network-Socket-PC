package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
 
public class ClientReadyBackground implements Runnable {
 
    private Socket socket;
    private DataInputStream in;
    private static DataOutputStream out;
    private String msg;
    
    static int client_port = 65001;

    public void connet() {
        try {
            socket = new Socket("192.168.23.214", client_port);

            System.out.println("���� �����. Ŭ���̾�Ʈ ready");

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
 
            out.writeUTF("ClientMain");
            System.out.println("Ŭ���̾�Ʈ ready...");
            
//            msg = in.readUTF();
//			System.out.println("ClientReady msg : " + msg);
//			if (msg.contains("ports:")) {
//				ClientMainPage.ports = msg;
//			}

            /* �� while������ ������ ��. - thread ó�� */
//            while (in != null) {
//                msg = in.readUTF();
//                new adjust(msg, nowTime(3));
//            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "��Ʈ�� �������� �ʽ��ϴ�.");
            System.exit(1);
        }
    }
 
    public static void main(String[] args) {
    	ClientReadyBackground clientReadyBackground = new ClientReadyBackground();

    	clientReadyBackground.connet();
    }
 
    public static void sendMessage(String msg2) {
        try {
            out.writeUTF(msg2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (in != null) {
            try {
				msg = in.readUTF();
				System.out.println("ClientReady msg : " + msg);
				
				if (msg.contains("ports:")) {
					ClientMainPage.ports = msg;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
 
}
