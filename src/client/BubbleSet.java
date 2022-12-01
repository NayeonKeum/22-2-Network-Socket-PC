package client;

import javax.swing.*;
import javax.swing.border.*;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.*;

//���� ��ư �г� Ŭ����
class BubbleSet extends JPanel{
 ArrayList<JLabel> subbt=new ArrayList<>(); // ���� ��ư�� �迭
 static ArrayList<JLabel> find=new ArrayList<>(); // ���� ��ư�� ���� ���� �迭  
 private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 10);
 static String color_chatRoom = window.color_chatRoom;
 
 public BubbleSet(String[] str, boolean isMe) {
	 /*
	  * str = {"sender�̸�", "�޽�������", "���۽ð�", "like"};
	  */

     JPanel inBubble = new JPanel();
	 
	 int[] fontSize = {10, 13, 10, 9};
	 //String[] fonts = {"KoPubWorld����ü", "����������_ac", "�����ٸ����", "����", "Serif"};
	 
     for(int i=0;i<4;i++) {
         subbt.add(new JLabel());	// add : subbt �迭�� �� ��ư�� �߰��Ѵ�.
         subbt.get(i).setText(str[i]);
         subbt.get(i).setFont(loadFont(fontSize[i]));
         find.add(subbt.get(i));		// add : JPanel�� subbt.get(i)�� ���δ�.
         }
     
     if (isMe) {
    	 for(int i=3;i>=0;i--) {
	    	 inBubble.add(subbt.get(i));
	     }
     } else {  // �ٸ� ����� ���� ������ ���ƿ並 ���� �� �ִ�.
    	 subbt.get(3).setText("��");
    	 inBubble.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
            	 if (e.getClickCount() == 2) {
            		 JPanel like = (JPanel) e.getSource();
                 	 String likeID = makeBubbleID(like);
                 	 
                 	 subbt.get(3).setText("��");
                 	 ClientBackground.sendMessage("4;��:"+ ClientGui.nickName + "���� �⵿�մϴ�.:" + likeID);
                }
             }
         });
    	 
	     for(int i=0;i<4;i++) {
	    	 inBubble.add(subbt.get(i));
	     }
     }
     
     inBubble.setBackground(Color.decode(color_chatRoom));	// ä�ù� ������ ���� ������
     //inBubble.setBackground(Color.RED);	// �ӽ�
     setBackground(Color.decode(color_chatRoom));	// ä�ù� ������ ���� ������
     subbt.get(1).setBorder(new EmptyBorder(4,6,4,6));//top,left,bottom,right
     
     BorderLayout borderLayout = new BorderLayout();
     setLayout(borderLayout);
     if (isMe)
    	 add(inBubble, BorderLayout.EAST);
     else
    	 add(inBubble, BorderLayout.WEST);
     
     
     addHyperlink(inBubble);
     
 	}
 

 	static String makeBubbleID(JPanel bb) {
 		String ID = "";
 		Component[] labels = (Component[]) bb.getComponents();
 		
 		ID += ":" + ((JLabel) labels[0]).getText().toString();	//sender
 		ID += ":" + ((JLabel) labels[1]).getText().toString();	//content
 		ID += ":" + ((JLabel) labels[2]).getText().toString();	//time
 		
 		return ID;
 	}
 
	
	static Font loadFont(int fontSize) {
		
		return SERIF_FONT.deriveFont(fontSize);
		//�����ٸ���� - http://cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf
	}
	
	static void addHyperlink(JPanel bb) {
		Component[] labels = (Component[]) bb.getComponents();
 		String content = ((JLabel) labels[1]).getText().toString();
 		if (content.contains("http"))
 			((JLabel) labels[1]).addMouseListener(new MouseAdapter() {
 	             @Override
 	             public void mouseClicked(MouseEvent e) {
 	            	if (Desktop.isDesktopSupported()) {
 	                   Desktop desktop = Desktop.getDesktop();
 	                   try {
 	                       URI uri = new URI(strUnConvert(content));
 	                       desktop.browse(uri);
 	                   } catch (IOException ex) {
 	                       ex.printStackTrace();
 	                   } catch (URISyntaxException ex) {
 	                       ex.printStackTrace();
 	                   }
 	            	}
 	             }
 	         });
	}
	
	private static String strUnConvert(String str) {
    	// <html> </html> <br>�� ��� remove�Ѵ�.
    	StringBuffer buffer = new StringBuffer(str);
    	str = str.replaceAll("<html>", "");
    	str = str.replaceAll("</html>", "");
    	str = str.replaceAll("<br>", "");
    	str = str.replaceAll("<br/>", "");
    	return str;
    	
    }
	
}