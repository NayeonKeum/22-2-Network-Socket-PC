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
	
	static RoundJTextField tf_type, tf_nickname, tf_port;
	static RoundedButton btn_enter;
	static String color_chatRoom = "#ccdeeb";
	Color textcolor = new Color(148, 117, 98);
	
	Container c = getContentPane();
	
    static int client_port;

    static String nickName;
    static String user_type;
    static String port_num;

	// 사용자에게 보이게 하는 메소드 
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
        
		lb_type1 = new JLabel("1: 중앙관리본부");
		lb_type2 = new JLabel("2: 지역소방본부");
		lb_type3 = new JLabel("3: 소방대원");
		lb_type1.setFont(new Font("굴림", Font.PLAIN, 10));
		lb_type2.setFont(new Font("굴림", Font.PLAIN, 10));
		lb_type3.setFont(new Font("굴림", Font.PLAIN, 10));

		lb_type1.setForeground(textcolor);
		lb_type2.setForeground(textcolor);
		lb_type3.setForeground(textcolor);

		lb_type = new JLabel("역할을 선택하세요.(1~3)");
		lb_nickname = new JLabel("닉네임(4자리 이하) : ");
		//lb_port = new JLabel("포트 번호 : ");
		lb_type.setFont(new Font("굴림", Font.BOLD, 11));
		lb_nickname.setFont(new Font("굴림", Font.BOLD, 11));
		//lb_port.setFont(new Font("돋움", Font.BOLD, 11));
		lb_type.setForeground(textcolor);
		lb_nickname.setForeground(textcolor);
		
        c.add(lb_type1, "8, 6");
        c.add(lb_type2, "8, 8");
        c.add(lb_type3, "8, 10");
        c.add(lb_type, "8, 14");
        c.add(lb_nickname, "8, 16");
        //c.add(lb_port, "8, 18");
        
        tf_type = new RoundJTextField(7);
        tf_nickname = new RoundJTextField(7);
        //tf_port = new JTextField(7);
        c.add(tf_type, "10, 14");
        c.add(tf_nickname, "10, 16");
        //c.add(tf_port, "10, 18");
        
        btn_enter = new RoundedButton("입장");
//        btn_enter.setFont(new Font("돋움", Font.PLAIN, 11));
    	btn_enter.setSize(getMinimumSize());
    	btn_enter.addActionListener(new ActionListener() {
    		 public void actionPerformed(ActionEvent e) {
    				user_type = tf_type.getText();
    				nickName = tf_nickname.getText();
    				//port_num = tf_port.getText();
    				if (!user_type.equals("") && !nickName.equals("")) {
						//client_port = Integer.valueOf(port_num).intValue();
    					if ((user_type.equals("1") || user_type.equals("2") || user_type.equals("3"))) {
    						setVisible(false);
    						new ClientGui(client_port, user_type, nickName);
    						System.out.println("클라이언트가 채팅방에 입장합니다.");
    					}
    				}
    		}
    	});

        c.add(btn_enter, "10, 20");
        
        c.setBackground(new Color(255, 238, 227));	// 배경색 지정
	
        setVisible(true);
	    
	}
	 
	// 읜도우 기본 세팅 
	public pre_window(int client_port) {
		pre_window.client_port = client_port;
		
	    setTitle("Client 정보 입력");
	    setSize(300,300);
	    setLocation(600,100);	// 실행시 창이 나타나는 위치 - 서버창과 겹치지 않게 설정한다.
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    this.shows();
	 }
	
//	public static void main(String[] args) {
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
////        try {
////	        do {
////	        	System.out.print("port 번호 : ");
////	        	client_port = Integer.valueOf(scanner.nextLine()).intValue();
////	        } while(!isInPortList(client_port));
////	        
////	        scanner.close();
////	        new pre_window();
////    	} catch (Exception e) {
////    		e.printStackTrace();
////
////            scanner.close();
////
////            System.exit(1);
////    	}
//        
//
//        new pre_window();
//    }
	
}
