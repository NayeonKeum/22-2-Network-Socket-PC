package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientMainPage extends JFrame {
	
    static int client_port = 0;

	DefaultTableModel model;
	
	public static String ports = "";
	
	public ClientMainPage() {
		
		setTitle("Client : PortList");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(200, 100, 300, 300);
		setLocation(600, 100);
		
		
		String[] colNames = new String[] {"PortNumber"};
		model = new DefaultTableModel(colNames, 0);
		
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setHeaderRenderer(new myColorRenderer());
		table.setRowHeight(23);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
	    dtcr.setForeground(new Color(130, 108, 94));
	    table.getColumnModel().getColumn(0).setCellRenderer(dtcr);  
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		if (model != null)
			init_model();
		
		table.setBackground(Color.decode("#fff7f2"));
		scrollPane.getViewport().setBackground(Color.decode("#fff7f2"));	// 배경색 지정
		
		
		
		// 포트 들어가기 버튼
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.decode("#fcddca"));	
//		JButton btnReload = new JButton("새로고침");
//		JButton btnIn = new JButton("들어가기");
		RoundedButton btnReload = new RoundedButton("새로고침");
		RoundedButton btnIn = new RoundedButton("들어가기");
		panel2.add(btnReload);
		panel2.add(btnIn);
		
		add(panel2, BorderLayout.SOUTH);
		setVisible(true);
		
//		// MainPage 종료시 포트 리스트 초기화/프로그램 종료
//		addWindowListener(new WindowAdapter() {
//        	public void windowClosing(WindowEvent e) {
//        		try {
//        			PrintWriter pw = new PrintWriter("portlist.txt");
//        			pw.close();
//        			System.out.println("포트를 모두 삭제합니다.");
//        			System.out.println("프로그램을 종료합니다.");
//        		} catch(IOException e1) {
//        			e1.printStackTrace();
//        		}
//        		System.exit(0);
//        	}
//        });
		
		// 새로고침 버튼 액션
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				
				init_model();
			}
		});
		
		
		// 들어가기 버튼 액션
		btnIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				client_port = Integer.parseInt((String) model.getValueAt(rowIndex,0));
				
				new pre_window(client_port);
				dispose();
			}
		});
		
	}
	
	public static void main(String args[]) {
		ClientReadyBackground cready = new ClientReadyBackground();
		cready.connet();
		
		Thread thread = new Thread(cready);
        thread.start();
		
		new ClientMainPage();
	}
	
	private void init_model() {
		//String[] ports = getPortlist();
		String[] ports = getPortlistFromServer();
		
		for (String port : ports) {
			String[] rows = new String[1];
			rows[0] = port;
			model.addRow(rows);
		}
	}
	
	private static String[] getPortlistFromServer() {
		ports = ports.replace("ports:", "");
		String[] portslist = ports.split(",");
    	
    	return portslist;
	}
	   
    
}