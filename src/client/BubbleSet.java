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

//서브 버튼 패널 클래스
class BubbleSet extends JPanel{
 ArrayList<JLabel> subbt=new ArrayList<>(); // 서브 버튼들 배열
 static ArrayList<JLabel> find=new ArrayList<>(); // 서브 버튼들 정적 동적 배열  
 private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 10);
 static String color_chatRoom = window.color_chatRoom;
 
 public BubbleSet(String[] str, boolean isMe) {
	 /*
	  * str = {"sender이름", "메시지내용", "전송시각", "like"};
	  */

     JPanel inBubble = new JPanel();
	 
	 int[] fontSize = {10, 13, 10, 9};
	 //String[] fonts = {"KoPubWorld돋움체", "나눔스퀘어_ac", "나눔바른고딕", "돋움", "Serif"};
	 
     for(int i=0;i<4;i++) {
         subbt.add(new JLabel());	// add : subbt 배열에 새 버튼을 추가한다.
         subbt.get(i).setText(str[i]);
         subbt.get(i).setFont(loadFont(fontSize[i]));
         find.add(subbt.get(i));		// add : JPanel에 subbt.get(i)를 붙인다.
         }
     
     if (isMe) {
    	 for(int i=3;i>=0;i--) {
	    	 inBubble.add(subbt.get(i));
	     }
     } else {  // 다른 사람이 보낸 버블에는 좋아요를 누를 수 있다.
    	 subbt.get(3).setText("♡");
    	 inBubble.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
            	 if (e.getClickCount() == 2) {
            		 JPanel like = (JPanel) e.getSource();
                 	 String likeID = makeBubbleID(like);
                 	 
                 	 subbt.get(3).setText("♥");
                 	 ClientBackground.sendMessage("4;♥:"+ ClientGui.nickName + "님이 출동합니다.:" + likeID);
                }
             }
         });
    	 
	     for(int i=0;i<4;i++) {
	    	 inBubble.add(subbt.get(i));
	     }
     }
     
     inBubble.setBackground(Color.decode(color_chatRoom));	// 채팅방 배경색과 같은 색으로
     //inBubble.setBackground(Color.RED);	// 임시
     setBackground(Color.decode(color_chatRoom));	// 채팅방 배경색과 같은 색으로
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
		//나눔바른고딕 - http://cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf
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
    	// <html> </html> <br>을 모두 remove한다.
    	StringBuffer buffer = new StringBuffer(str);
    	str = str.replaceAll("<html>", "");
    	str = str.replaceAll("</html>", "");
    	str = str.replaceAll("<br>", "");
    	str = str.replaceAll("<br/>", "");
    	return str;
    	
    }
	
}