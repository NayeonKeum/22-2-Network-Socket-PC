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

import java.awt.EventQueue;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

//������ ��ġ �� ��̸���Ʈ ��� 
class window extends JFrame {
	
	static ArrayList<BubbleSet> bts; // ���� ��ư�� �г� ��ü�� ��ҷ� ��� �迭
	static JScrollPane scrollPane = null;
	static ChatPanel p2;	// p2: �߰��г�
	private JPanel p0, p3;	// p0: ����г�, p3: �ϴ��г�
	static JTextField edit;
	static JButton btn_exit, btn_send;
	static int curr=0; // ����Ʈ ���� Ŭ�������� ���̴� ����
	static String color_chatRoom = "#ccdeeb";
    static JLabel lb_ppCount;

	// ����ڿ��� ���̰� �ϴ� �޼ҵ� 
	public void shows() {
	/* ��� �г� : ������ ��ư, �ο���, ä�ù� ����  */
	
	
	FormLayout p0_layout = new FormLayout(
			 "10px, pref, 30px, pref, 30px, pref, 10px",
	         "pref"	
			 );
	CellConstraints cc0 = new CellConstraints();
	p0 = new JPanel(p0_layout);
	

	// �ο��� ����
	int ppCount = 1;	// �ӽ� - ������ Ŭ���̾�Ʈ �� ����ؾ���!!
	lb_ppCount = new JLabel(ppCount+"��\t");
	p0.add(lb_ppCount, cc0.xy(6, 1));
	
	// ������ ��
	JLabel lb_roomTitle = new JLabel(ClientGui.client_port + "��° ��");
	p0.add(lb_roomTitle, cc0.xy(4, 1));
	
	// ������ ��ư ����
	btn_exit = new JButton("exit");
	btn_exit.setFont(new Font("Serif", Font.PLAIN, 11));
	btn_exit.setSize(getMinimumSize());
	btn_exit.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
				System.out.println("Ŭ���̾�Ʈ�� ä�ù��� �����ϴ�.");
				System.exit(0);
				/* �ӽ� - ä�ù� �����⸦ ������ ���α׷� ��ü�� ������ ���� ������ �ƿ� ����µ�,
				 * ä�ù��� ���� �� ����� ä�ù� ������ �� ���� ȭ���� ���� �ִٸ�
				 * ������ ��ư�� ������ �� ���α׷��� �����ϴ� �� �ƴ϶� ���� ȭ������ ���ư����Ѵ�.
				 */
		}
	});
	p0.add(btn_exit, cc0.xy(2, 1));

	
	/* �߰� �г� : ä�� ����� ǥ�� */
	bts =new ArrayList<>(); // ���� ���� �迭 ����
	
	// ä�ù� ����� ���ÿ� ���� �ȳ� �޽����� ǥ���Ѵ�. 
	// (������ ���� �� �ƴ����� ������ ���� �޽����� ���� ���������� ǥ��)
	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy�� MM�� dd�� HH:mm");
	Date date_welcome = new Date();
	String time_welcome = format1.format(date_welcome);
	String[] welcome = {"", "ä�ù濡 �����Ͽ����ϴ�.", time_welcome, ""}; 
	bts.add(new BubbleSet(welcome, false));
	

	p2 = new ChatPanel();
	p2.addpanel(bts.get(0));
	p2.setBackground(Color.decode(color_chatRoom));	// ä�ù� ���� ����
	//p2.setBackground(Color.BLUE);  // �ӽ�
	
	scrollPane = new JScrollPane(p2, 
			 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane.setBounds(4,4,340,330);
	
	add(scrollPane,BorderLayout.CENTER);

	/* �ϴ� �г� : �Է�ĭ, ���۹�ư */
	FormLayout p3_layout = new FormLayout(
			 "8px, pref:grow, 6px, pref, 8px",   // 8px�� �¿쿩��
	         "2px, pref, 2px"		// 2px�� ���Ͽ���
			 );
	CellConstraints cc3 = new CellConstraints();
	p3 = new JPanel(p3_layout);
	edit = new JTextField(5);
	edit.requestFocus();
	edit.addActionListener(new ActionListener() {
		@Override
	    public void actionPerformed(ActionEvent e) {
	        String msg = ClientGui.user_type + ";" + ClientGui.nickName + ":" + edit.getText();
	        ClientGui.client.sendMessage(msg);
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

	
	// p0,p2,p3 �г��� add
    add(p0,BorderLayout.NORTH);
    //add(p2,BorderLayout.CENTER);	// -> add(scrollPane,BorderLayout.CENTER);
    add(p3,BorderLayout.SOUTH);
    setVisible(true);
	    
	}
	 
	// �ȵ��� �⺻ ���� 
	public window() {
	    setTitle("Client " + ClientGui.nickName);
	    setSize(300,500);
	    setLocation(500,100);	// ����� â�� ��Ÿ���� ��ġ - ����â�� ��ġ�� �ʰ� �����Ѵ�.
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    this.shows();
	    
	 }

    
		
}
