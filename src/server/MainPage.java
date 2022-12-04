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
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
	    dtcr.setForeground(new Color(130, 108, 94));
	    table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
	    table.getColumnModel().getColumn(1).setCellRenderer(dtcr);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		table.setBackground(Color.decode("#fff7f2"));
		scrollPane.getViewport().setBackground(Color.decode("#fff7f2"));	// 배경색 지정
		
		// 하단 데이터 입력 패널
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2,1));
		bottomPanel.setBackground(Color.decode("#fcddca"));
		// 포트 추가하기
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#fcddca"));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		RoundJTextField tfArea = new RoundJTextField(7);
		RoundJTextField tfPortnumber = new RoundJTextField(5);
		RoundedButton btnAdd = new RoundedButton("추가하기");
		panel.add(new JLabel("지역 :"));
		panel.add(tfArea);
		panel.add(new JLabel("포트번호 :"));
		panel.add(tfPortnumber);
		panel.add(btnAdd);
		bottomPanel.add(panel);
		// 포트 삭제하기, 열기 버튼
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.decode("#fcddca"));
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		RoundedButton btnDel = new RoundedButton("삭제하기");
		RoundedButton btnIn = new RoundedButton("열기");
		panel2.add(btnDel);
		panel2.add(btnIn);
		bottomPanel.add(panel2);
		
		add(bottomPanel, BorderLayout.SOUTH);
		setVisible(true);
		
		// MainPage 종료시 포트 리스트 초기화/프로그램 종료
		addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		try {
        			PrintWriter pw = new PrintWriter("portlist.txt");
        			pw.close();
        			System.out.println("포트를 모두 삭제합니다.");
        			System.out.println("프로그램을 종료합니다.");
        		} catch(IOException e1) {
        			e1.printStackTrace();
        		}
        		System.exit(0);
        	}
        });
		
		// 추가하기 버튼 액션
		btnAdd.addActionListener(new ActionListener() {
			@Override
			// 입력된 값 테이블에 추가하기 - 입력 값들 한 배열로 만들기
			public void actionPerformed(ActionEvent e) {
				String[] rows = new String[2];
				rows[0] = tfArea.getText();
				rows[1] = tfPortnumber.getText();
				server_port = Integer.parseInt(rows[1]);
				if (server_port < 5000 || server_port > 65000 || isAlreadyExist(server_port)) {
					tfArea.setText("");
					tfPortnumber.setText("");
					JOptionPane.showMessageDialog(null, "올바르지 않은 포트번호입니다.");
				}
				else {
				model.addRow(rows);
				add_port(server_port);
				}
				// 입력 후 텍스트 필드 값 제거
				tfArea.setText("");
				tfPortnumber.setText("");												
			}
		});
		// 삭제하기 버튼 액션
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
		// 열기 버튼 액션
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
	// portlist.txt에 이미 있는지 검사
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
    
    // 포트 테이블에 추가하기
    private void add_port(int port_num) {
    	try{
    		String data = server_port + ",";
 
    		File file =new File("portlist.txt");
 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file.getName(),true); // 뒤에 덧붙이기
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
    
    // 포트 테이블에서 삭제하기
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
    	    		System.out.println("port 번호 " + ports[i] + "삭제");
    	    		ports[i] = "";
    	    	}
        	}
			
	    	try { 
		    	BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
		        // 파일 쓰기
		    	for (String s : ports) {
		    		if (!s.equals(""))
		    			buffWrite.write(s+",", 0, (s+",").length());
		    	}
		        // 파일 닫기
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