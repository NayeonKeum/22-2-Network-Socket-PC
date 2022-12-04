package server;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import client.RoundJTextField;
import client.RoundedButton;
import client.myColorRenderer;

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

public class MainPage extends JFrame {
	
	private ServerGui gui = null;
    static int server_port = 0;

	DefaultTableModel model;
	
	static ServerGui main_server_gui;
	
	public MainPage() {
		
		setTitle("Server : PortList");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(200, 100, 400, 600);
		
		String[] colNames = new String[] {"Area", "PortNumber"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		
		JTable table = new JTable(model);
		
		table.getColumnModel().getColumn(0).setHeaderRenderer(new myColorRenderer());
		table.getColumnModel().getColumn(1).setHeaderRenderer(new myColorRenderer());
		table.setRowHeight(23);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // �������� ���������� CENTER��
	    dtcr.setForeground(new Color(130, 108, 94));
	    table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
	    table.getColumnModel().getColumn(1).setCellRenderer(dtcr);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		table.setBackground(Color.decode("#fff7f2"));
		scrollPane.getViewport().setBackground(Color.decode("#fff7f2"));	// ���� ����
		
		// �ϴ� ������ �Է� �г�
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2,1));
		bottomPanel.setBackground(Color.decode("#fcddca"));
		// ��Ʈ �߰��ϱ�
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#fcddca"));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		RoundJTextField tfArea = new RoundJTextField(7);
		RoundJTextField tfPortnumber = new RoundJTextField(5);
		RoundedButton btnAdd = new RoundedButton("�߰��ϱ�");
		panel.add(new JLabel("���� :"));
		panel.add(tfArea);
		panel.add(new JLabel("��Ʈ��ȣ :"));
		panel.add(tfPortnumber);
		panel.add(btnAdd);
		bottomPanel.add(panel);
		// ��Ʈ �����ϱ�, ���� ��ư
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.decode("#fcddca"));
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		RoundedButton btnDel = new RoundedButton("�����ϱ�");
		RoundedButton btnIn = new RoundedButton("����");
		panel2.add(btnDel);
		panel2.add(btnIn);
		bottomPanel.add(panel2);
		
		add(bottomPanel, BorderLayout.SOUTH);
		setVisible(true);
		
		// MainPage ����� ��Ʈ ����Ʈ �ʱ�ȭ/���α׷� ����
		addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		try {
        			PrintWriter pw = new PrintWriter("portlist.txt");
        			pw.close();
        			System.out.println("��Ʈ�� ��� �����մϴ�.");
        			System.out.println("���α׷��� �����մϴ�.");
        		} catch(IOException e1) {
        			e1.printStackTrace();
        		}
        		System.exit(0);
        	}
        });
		
		// �߰��ϱ� ��ư �׼�
		btnAdd.addActionListener(new ActionListener() {
			@Override
			// �Էµ� �� ���̺� �߰��ϱ� - �Է� ���� �� �迭�� �����
			public void actionPerformed(ActionEvent e) {
				String[] rows = new String[2];
				rows[0] = tfArea.getText();
				rows[1] = tfPortnumber.getText();
				server_port = Integer.parseInt(rows[1]);
				if (server_port < 5000 || server_port > 65000 || isAlreadyExist(server_port)) {
					tfArea.setText("");
					tfPortnumber.setText("");
					JOptionPane.showMessageDialog(null, "�ùٸ��� ���� ��Ʈ��ȣ�Դϴ�.");
				}
				else {
				model.addRow(rows);
				add_port(server_port);
				}
				// �Է� �� �ؽ�Ʈ �ʵ� �� ����
				tfArea.setText("");
				tfPortnumber.setText("");												
			}
		});
		// �����ϱ� ��ư �׼�
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				server_port = Integer.parseInt((String) model.getValueAt(rowIndex,1));
				if (rowIndex == -1) return;
				model.removeRow(rowIndex);
				del_port(server_port);
			}
		});
		// ���� ��ư �׼�
		btnIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				server_port = Integer.parseInt((String) model.getValueAt(rowIndex,1));
				try {
					gui = new ServerGui(server_port);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	public static void main(String args[]) {
		try {
			main_server_gui = new ServerGui(65001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new MainPage();
	}
	// portlist.txt�� �̹� �ִ��� �˻�
    private static boolean isAlreadyExist(int port_num) {
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
        	return false;
    	} else {
    		return false;
    	}
    	
    }    
    
    // ��Ʈ ���̺� �߰��ϱ�
    private void add_port(int port_num) {
    	try{
    		String data = server_port + ",";
 
    		File file =new File("portlist.txt");
 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file.getName(),true); // �ڿ� �����̱�
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(data);
    	        bufferWritter.close();
 
	        System.out.println("ADD PORT TO FILE Done");
	        
	        try {
		        if (main_server_gui.server.out_main != null)
		        	main_server_gui.server.sendPortlist();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    // ��Ʈ ���̺��� �����ϱ�
    private void del_port(int port_num) {
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
    	    		System.out.println("port ��ȣ " + ports[i] + "����");
    	    		ports[i] = "";
    	    	}
        	}
			
	    	try { 
		    	BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
		        // ���� ����
		    	for (String s : ports) {
		    		if (!s.equals(""))
		    			buffWrite.write(s+",", 0, (s+",").length());
		    	}
		        // ���� �ݱ�
		        buffWrite.flush();  
		        buffWrite.close();
	        } catch (IOException ex) {
	        	System.out.println(ex.getMessage());
	        }
    	}
    	
    	try {
	        if (main_server_gui.server.out_main != null)
	        	main_server_gui.server.sendPortlist();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    } 
 
}