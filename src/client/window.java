package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

//윈도우 배치 및 어레이리스트 담당 
class window extends JFrame {
	
	static ArrayList<BubbleSet> bts; // 서브 버튼들 패널 객체를 요소로 담는 배열
	static JScrollPane scrollPane = null;
	static ChatPanel p2;	// p2: 중간패널
	private JPanel p0, p3;	// p0: 상단패널, p3: 하단패널
	static JTextField edit;
	static JButton btn_exit, btn_send;
	static int curr=0; // 리스트 조정 클래스에서 쓰이는 변수
	public static String color_chatRoom = "#fcddca";  //연한 주황색 : #fff7f2, 주황색 : #fcddca, 하늘색 : #ccdeeb
    static JLabel lb_ppCount;

	// 사용자에게 보이게 하는 메소드 
	public void shows() {
		/* 상단 패널 : 나가기 버튼, 인원수, 채팅방 제목  */
		FormLayout p0_layout = new FormLayout(
				 "10px, pref, 30px, pref, 30px, pref, 10px",
		         "pref"	
				 );
		CellConstraints cc0 = new CellConstraints();
		p0 = new JPanel(p0_layout);
		
	
		// 인원수 구현
		int ppCount = 1;	// 임시 - 원래는 클라이언트 수 계산해야함!!
		lb_ppCount = new JLabel(ppCount+"명\t");
		p0.add(lb_ppCount, cc0.xy(6, 1));
		
		// 방제목 라벨
		JLabel lb_roomTitle = new JLabel(ClientGui.client_port + "번째 방");
		p0.add(lb_roomTitle, cc0.xy(4, 1));
		
		// 나가기 버튼 구현
		btn_exit = new JButton("exit");
		btn_exit.setFont(new Font("Serif", Font.PLAIN, 11));
		btn_exit.setSize(getMinimumSize());
		btn_exit.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
					System.out.println("클라이언트가 채팅방을 나갑니다.");
					System.exit(0);
					/* 임시 - 채팅방 나가기를 누르면 프로그램 자체가 닫히고 서버 접속이 아예 끊기는데,
					 * 채팅방을 여러 개 만들고 채팅방 들어오기 전 메인 화면이 따로 있다면
					 * 나가기 버튼을 눌렀을 때 프로그램을 종료하는 게 아니라 메인 화면으로 돌아가야한다.
					 */
			}
		});
		p0.add(btn_exit, cc0.xy(2, 1));
	
		
		/* 중간 패널 : 채팅 버블들 표시 */
		bts =new ArrayList<>(); // 버블 동적 배열 생성
		
		// 채팅방 입장과 동시에 입장 안내 메시지를 표시한다. 
		// (서버가 보낸 건 아니지만 서버가 보낸 메시지와 같은 디자인으로 표시)
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월 dd일 HH:mm");
		Date date_welcome = new Date();
		String time_welcome = format1.format(date_welcome);
		String[] welcome = {"", "채팅방에 입장하였습니다.", time_welcome, ""}; 
		bts.add(new BubbleSet(welcome, false));
		
	
		p2 = new ChatPanel();
		p2.addpanel(bts.get(0));
		p2.setBackground(Color.decode(color_chatRoom));	// 채팅방 배경색 지정
		//p2.setBackground(Color.BLUE);  // 임시
		
		scrollPane = new JScrollPane(p2, 
				 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(4,4,340,330);
		
		add(scrollPane,BorderLayout.CENTER);
	
		/* 하단 패널 : 입력칸, 전송버튼 */
		FormLayout p3_layout = new FormLayout(
				 "8px, pref:grow, 6px, pref, 8px",   // 8px는 좌우여백
		         "2px, pref, 2px"		// 2px는 상하여백
				 );
		CellConstraints cc3 = new CellConstraints();
		p3 = new JPanel(p3_layout);
		edit = new JTextField(5);
		edit.requestFocus();
		edit.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
		        String msg = ClientGui.user_type + ";" + ClientGui.nickName + ":" + edit.getText();
		        ClientBackground.sendMessage(msg);
		        edit.setText("");
		    }
		});
	
	
		btn_send = new JButton("send");
		//btn_send.addActionListener(new adjust());
		btn_send.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
		        String msg = ClientGui.user_type + ";" + ClientGui.nickName + ":" + edit.getText();
		        ClientBackground.sendMessage(msg);
		        edit.setText("");
		    }
		});
		p3.add(edit, cc3.xy(2, 2));
		p3.add(btn_send, cc3.xy(4, 2));
		
		
		// p0,p2,p3 패널을 add
	    add(p0,BorderLayout.NORTH);
	    //add(p2,BorderLayout.CENTER);	// -> add(scrollPane,BorderLayout.CENTER);
	    add(p3,BorderLayout.SOUTH);
	    setVisible(true);
	    
	}
	 
	// 읜도우 기본 세팅 
	public window() {
	    
	    this.setTitle("Client " + ClientGui.nickName);
	    this.setSize(300,500);
	    this.setLocation(600,100);	// 실행시 창이 나타나는 위치 - 서버창과 겹치지 않게 설정한다.
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.shows();
	 }

    
		
}
