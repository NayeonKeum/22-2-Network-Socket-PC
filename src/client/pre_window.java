package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class pre_window extends JFrame {
	
	static JLabel lb_type, lb_nickname, lb_port, lb_type1, lb_type2, lb_type3;
	
	static JTextField tf_type, tf_nickname, tf_port;
	static JButton btn_enter;
	static String color_chatRoom = "#ccdeeb";
	
	Container c = getContentPane();
	
    static int client_port = 0;

    static String nickName;
    static String user_type;
    static String port_num;

	// ����ڿ��� ���̰� �ϴ� �޼ҵ� 
	public void shows() {
		// 12, 20
		c.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,  //1
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //3
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //5
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //7
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //9
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //11
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //13
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //15
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //17
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,  //19
                FormSpecs.DEFAULT_ROWSPEC,})
        );
        
		lb_type1 = new JLabel("1: �߾Ӱ�������");
		lb_type2 = new JLabel("2: �����ҹ溻��");
		lb_type3 = new JLabel("3: �ҹ���");
		lb_type1.setFont(new Font("����", Font.PLAIN, 10));
		lb_type2.setFont(new Font("����", Font.PLAIN, 10));
		lb_type3.setFont(new Font("����", Font.PLAIN, 10));

		lb_type = new JLabel("������ �����ϼ���.(1~3)");
		lb_nickname = new JLabel("�г���(4�ڸ� ����) : ");
		lb_port = new JLabel("��Ʈ ��ȣ : ");
		lb_type.setFont(new Font("����", Font.BOLD, 11));
		lb_nickname.setFont(new Font("����", Font.BOLD, 11));
		lb_port.setFont(new Font("����", Font.BOLD, 11));
		
        c.add(lb_type1, "8, 6");
        c.add(lb_type2, "8, 8");
        c.add(lb_type3, "8, 10");
        c.add(lb_type, "8, 14");
        c.add(lb_nickname, "8, 16");
        c.add(lb_port, "8, 18");
        
        tf_type = new JTextField(7);
        tf_nickname = new JTextField(7);
        tf_port = new JTextField(7);
        c.add(tf_type, "10, 14");
        c.add(tf_nickname, "10, 16");
        c.add(tf_port, "10, 18");
        
        btn_enter = new JButton("����");
        btn_enter.setFont(new Font("����", Font.PLAIN, 11));
    	btn_enter.setSize(getMinimumSize());
    	btn_enter.addActionListener(new ActionListener() {
    		 public void actionPerformed(ActionEvent e) {
    				user_type = tf_type.getText();
    				nickName = tf_nickname.getText();
    				port_num = tf_port.getText();
    				if (!user_type.equals("") && !nickName.equals("") && !port_num.equals("")) {
						client_port = Integer.valueOf(port_num).intValue();
    					if ((user_type.equals("1") || user_type.equals("2") || user_type.equals("3")) && isInPortList(client_port)) {
    						setVisible(false);
    						new ClientGui(client_port, user_type, nickName);
    						System.out.println("Ŭ���̾�Ʈ�� ä�ù濡 �����մϴ�.");
    					}
    				}
    		}
    	});

        c.add(btn_enter, "10, 20");
        
        c.setBackground(Color.decode("#fae6e3"));	// ���� ����
	
	
        setVisible(true);
	    
	}
	 
	// �ȵ��� �⺻ ���� 
	public pre_window() {
	    setTitle("Client ���� �غ�");
	    setSize(300,300);
	    setLocation(500,100);	// ����� â�� ��Ÿ���� ��ġ - ����â�� ��ġ�� �ʰ� �����Ѵ�.
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    this.shows();
	 }
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        do {
//        	System.out.print("������ �Է��ϼ���(1:�߾Ӱ�������/2:�����ҹ溻��/3:�ҹ���) : ");
//        	user_type = scanner.nextLine();
//        } while(!(user_type.equals("1") || user_type.equals("2") || user_type.equals("3")));
//        do {
//        	System.out.print("4�ڸ� ������ �г����� �Է��ϼ��� : ");
//        	nickName = scanner.nextLine();
//        } while(nickName.length() > 4 || nickName.contains(":") || nickName.contains(";")
//        		|| nickName.equals("") || nickName.equals("����") 
//        		|| nickName.equals("�ο���"));
        
//        try {
//	        do {
//	        	System.out.print("port ��ȣ : ");
//	        	client_port = Integer.valueOf(scanner.nextLine()).intValue();
//	        } while(!isInPortList(client_port));
//	        
//	        scanner.close();
//	        new pre_window();
//    	} catch (Exception e) {
//    		e.printStackTrace();
//
//            scanner.close();
//
//            System.exit(1);
//    	}
        

        new pre_window();
    }
	
private static boolean isInPortList(int port_num) {
    	
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

    	
    	String[] ports = fileText.split(",");
    	
    	if(file.exists()){
        	for (int i=0; i < ports.length; i++) {
    	    	if (ports[i].equals(port_num+"")) {
    	    		return true;
    	    	}
        	}
    	} else return false;
    	
    	
		return false;
    	
    }
}
