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
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // �������� ���������� CENTER��
	    dtcr.setForeground(new Color(130, 108, 94));
	    table.getColumnModel().getColumn(0).setCellRenderer(dtcr);  
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		if (model != null)
			init_model();
		
		table.setBackground(Color.decode("#fff7f2"));
		scrollPane.getViewport().setBackground(Color.decode("#fff7f2"));	// ���� ����
		
		
		
		// ��Ʈ ���� ��ư
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.decode("#fcddca"));	
//		JButton btnReload = new JButton("���ΰ�ħ");
//		JButton btnIn = new JButton("����");
		RoundedButton btnReload = new RoundedButton("���ΰ�ħ");
		RoundedButton btnIn = new RoundedButton("����");
		panel2.add(btnReload);
		panel2.add(btnIn);
		
		add(panel2, BorderLayout.SOUTH);
		setVisible(true);
		
//		// MainPage ����� ��Ʈ ����Ʈ �ʱ�ȭ/���α׷� ����
//		addWindowListener(new WindowAdapter() {
//        	public void windowClosing(WindowEvent e) {
//        		try {
//        			PrintWriter pw = new PrintWriter("portlist.txt");
//        			pw.close();
//        			System.out.println("��Ʈ�� ��� �����մϴ�.");
//        			System.out.println("���α׷��� �����մϴ�.");
//        		} catch(IOException e1) {
//        			e1.printStackTrace();
//        		}
//        		System.exit(0);
//        	}
//        });
		
		// ���ΰ�ħ ��ư �׼�
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				
				init_model();
			}
		});
		
		
		// ���� ��ư �׼�
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