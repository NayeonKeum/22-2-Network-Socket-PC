package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
 
public class ClientBackground implements Runnable {
 
    private Socket socket;
    private DataInputStream in;
    private static DataOutputStream out;
    private ClientGui gui;
    private String msg;
    private String nickName;
    
    static int client_port = ClientGui.client_port;
 
    public final void setGui(ClientGui gui) {
        this.gui = gui;
    }
 
    public void connet() {
        try {
            socket = new Socket("192.168.23.214", client_port);

            System.out.println("서버 연결됨.");

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
 
            out.writeUTF(nickName);
            System.out.println("클라이언트 : 메시지 전송완료");

            /* 이 while문에서 오류가 남. - thread 처리 */
//            while (in != null) {
//                msg = in.readUTF();
//                new adjust(msg, nowTime(3));
//            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "포트가 열려있지 않습니다.");
            System.exit(1);
        }
    }
 
    public static void main(String[] args) {
        ClientBackground clientBackground = new ClientBackground();

        clientBackground.connet();
    }
 
    public static void sendMessage(String msg2) {
        try {
            out.writeUTF(msg2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void setNickname(String nickName) {
        this.nickName = nickName;
    }
    
    public String nowTime(int op) {	//op 0: "", op 1: ymd, op2: md, op3: hm
    	SimpleDateFormat format;
    	switch(op) {
    	case 0:
    		return "";
    	case 1:
    		format = new SimpleDateFormat ( "yyyy년 MM월 dd일");
    		break;
    	case 2:
    		format = new SimpleDateFormat ( "MM월 dd일");
    		break;
    	case 3:
    		format = new SimpleDateFormat ( "HH:mm:ss");
    		break;
    	default:
    		format = new SimpleDateFormat ( "HH:mm");
    		break;
    	}
    	
    	Date date = new Date();
    	String time = format.format(date);
    	
    	return time;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (in != null) {
            try {
				msg = in.readUTF();
	            new adjust(msg, nowTime(3));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
 
}
