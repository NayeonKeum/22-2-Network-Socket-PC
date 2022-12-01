package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

// ����Ʈ ���� Ŭ����
class adjust extends JPanel {
    String editstring; // �ؽ�Ʈ �ʵ忡 ���� ��Ʈ��
    ArrayList<BubbleSet> bts; // ���� ��ư �г� ��ü�� ��ҷ� ��� ���� �迭 
    ArrayList<JLabel> find; // ���� ��ư�� ��ҷ� ��� ���� ���� �迭  
    static int curr=window.curr; // ���� ���� �ؿ� �ִ� ��ġ�� �г��� �ε��� 
    int cset; // �� ������ ���� ����
    ChatPanel p2;
    final static int limitByteLength = 37;
    static String sender = "";
    static String msg = "";
    static String time = "";
    static String aSenderlikes="", likedsender = "", likedcontent = "";

    public adjust(String msg, String time) {
        p2=window.p2;
        bts=window.bts;
        find=BubbleSet.find;
        this.msg=msg;
        this.time=time;
        
        if (!getMsgContent(msg).equals("")) {
        	if (isNotification(msg)) {  // �ȳ��޽���
        		sender = "����";
        		addBubblePanel(false);	// ���� �߰�
        	} else {
        		/* �޽��� �м� �� ���� ���� */
	        	sender = whoIsSender(msg);
		        // �� �޽���
		        if (sender.equals(ClientGui.nickName)) {
		        	addBubblePanel(true);	// ���� �߰�
		        	
		        } else if (sender.equals("�ο���")) {
		        	// ���� �߰����� ����.
		        	// ����г��� �ο����� ������Ʈ - ppCount, lb_ppCount
		        	String content = strConvert(getMsgContent(msg));
		        	window.lb_ppCount.setText(content+"��\t");
		        	
		        } //else if (isLikeMsg(msg)) {
		        
		        	//��:ooo���� ������ ����մϴ�:sender:content:time
		        	//0:sender, 1: ooo���� ������ ����մϴ�, 2: ��������sender, 3: ����������, 4: ��������ð�
		        	
		        	//addBubblePanel(false);
		        //}
		        else {  // ���� �޽���
		        	addBubblePanel(false);	// ���� �߰�
		        }
        	}
        }

    }
    
    // �� ���� �޼ҵ� 
    public void selectColor(int idx, boolean isMe) {
        // ���� ���� �κи� ��� �� ����
        // addBubblePanel()���� curr++�� ��, setBubbleColor(curr);�� ȣ���Ѵ�.
    	if (isMe)
    		bts.get(idx).subbt.get(1).setBackground(Color.yellow);
    	else 
    		bts.get(idx).subbt.get(1).setBackground(Color.white);
    	
        bts.get(idx).subbt.get(1).setOpaque(true);
        
        // ���� �� ����
        /* sender ���� ��
         * �߾Ӱ������� : �ʷϻ� #47a877
         * �����ҹ溻�� : ��Ȳ�� #d98209
         * �ҹ��� : ������ #ba3d3d
         * �׿� : ȸ�� #9aaab8 */
        if (typeOfSender(msg).equals("1"))
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#47a877"));	// sender
        else if (typeOfSender(msg).equals("2"))
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#d98209"));	// sender
        else if (typeOfSender(msg).equals("3"))
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#ba3d3d"));	// sender
        else
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#9aaab8"));	// sender
        //bts.get(idx).subbt.get(0).setForeground(Color.decode("#9aaab8"));	// sender - ȸ�� #9aaab8
        //bts.get(idx).subbt.get(0).setFont(new Font("Serif", Font.PLAIN, 10));
        bts.get(idx).subbt.get(1).setForeground(Color.black);	// �޽��� ���� - ������
        //bts.get(idx).subbt.get(1).setFont(new Font("����", Font.PLAIN, 13));
        bts.get(idx).subbt.get(2).setForeground(Color.decode("#8a9aa8"));	// ���� �ð� - ȸ�� #8a9aa8
        //bts.get(idx).subbt.get(2).setFont(new Font("Serif", Font.PLAIN, 10));
        bts.get(idx).subbt.get(3).setForeground(Color.decode("#d6546e"));	// ���ƿ� - ������ #d6546e
        //bts.get(idx).subbt.get(3).setFont(new Font("Serif", Font.PLAIN, 10));
        
        if (sender.equals("����")) {
        	bts.get(idx).subbt.get(1).setBackground(Color.blue);
        	bts.get(idx).subbt.get(1).setForeground(Color.white);	// �޽��� ���� - ���
        	bts.get(idx).subbt.get(3).setText("");
        } else if (sender.equals("��")) {
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#ba3d3d"));	// ���� #ba3d3d  ��ũ #fa7fa2
        	bts.get(idx).subbt.get(1).setBackground(Color.decode("#ba3d3d"));	// ���� #ba3d3d  ��ũ #fa7fa2
        	bts.get(idx).subbt.get(1).setForeground(Color.white);	// �޽��� ���� - ���
        } else if (sender.equals("��ġ")) {
        	bts.get(idx).subbt.get(1).setBackground(Color.decode("#ccdeeb"));  // ä�ù� ����� ����
        	bts.get(idx).subbt.get(1).setForeground(Color.blue);	// �޽��� ���� - �Ķ�
        	bts.get(idx).subbt.get(3).setText("");
        }
        
    }
    
    // �г� �߰�
    public void addBubblePanel(boolean isMe) {	// ������ ĭ�� �� ������ �����.
    	curr++;
        // �� ĭ �� ����� 
        // index curr�� �� �г��� �����.
        // ���� �迭 �߰�
        String content = getMsgContent(msg);
        String time_sent = time;
        String sender0 = sender;
        if (isMe)
        	sender0 = "";
        else if (sender.equals("��")) {
        	aSenderlikes = getLikeSegment(msg, 1);	//ooo���� ������ ����մϴ�.
        	likedsender = getLikeSegment(msg, 3);  	// ���� ���� sender
        	likedcontent = getLikeSegment(msg, 4);  // ���� ���� ����
        	likedcontent = strUnConvert(likedcontent);
        	/*
        	 *  else if (sender.equals("��")) {
        	aSenderlikes = getLikeSegment(msg, 1);	//ooo���� ������ ����մϴ�.
        	likedsender = getLikeSegment(msg, 2);  	// ���� ���� sender
        	likedcontent = getLikeSegment(msg, 3);  // ���� ���� ����
        	likedcontent = strUnConvert(likedcontent);
        	String likedtime = getLikeSegment(msg, 4);  // ���� ���� ���۽ð�
        	String likedID = likedsender+likedcontent+likedtime;
        	BubbleSet bbs = findBubbleByID(likedID);
        	JLabel likeLabel = (JLabel) ((JPanel) bbs.getComponent(0)).getComponent(3);	// == subbt.get(3)
        	likeLabel.setText("���ƿ�");
        	*/
        	
        	content = aSenderlikes + " [ " + likedsender + " : " + likedcontent + " ]";  // ooo���� ������ ����մϴ�. [ likedsender : likedcontent]
        
        }
        content = strConvert(content);
        
        /*
        else if (isLikeMsg(msg)) {
    		content = aSenderlikes + " [ " + likedsender + " : " + likedcontent + " ]";  // ooo���� ������ ����մϴ�. [ likedsender : likedcontent]
    	} */
        
        String[] msg_set = {sender0, content, time_sent, ""};
        bts.add(new BubbleSet(msg_set, isMe));
        
        // panel�� �г� ��ü �Ѱ��ְ� �ű⼭ �߰���Ŵ
        p2.addpanel(bts.get(curr));
        
        // �޽��� �Է�ĭ �ʱ�ȭ 
        window.edit.setText("");
        window.edit.requestFocus();
        
        // �� ���� �� ���� 
        selectColor(curr, isMe);
        
        
        // GUI â ������Ʈ
        p2.revalidate();
        p2.repaint();
    }
    
    
    
    private String strConvert(String str) {
    	// ���� ���� limitByteLength�� �Ѿ ������ <br>�� �߰��Ѵ�. 
    	StringBuffer buffer = new StringBuffer(str);
    	
    	byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
    	if (bytes.length <= limitByteLength) { return str; }
    	else {
    		int lineByteCount = 0;
    		for (int i = 0, j = 0; i < str.length(); ) {
    			int cp = str.codePointAt(i);
    			int n = Character.charCount(cp);
    			int byteCount = str.substring(i, i + n).getBytes(StandardCharsets.UTF_8).length;
    			if ((lineByteCount + byteCount) > limitByteLength) { 
    				buffer.insert(j+1, "<br>");
    				i += n; j += n+4;
    				lineByteCount = byteCount;
    			} 
    			else {
    				lineByteCount += byteCount;
    				i += n; j+=n;
    			}
    		}
    	}
    	
    	(buffer.toString()).replaceAll("\n", "<br>");
    	
	
    	return "<html>"+ buffer.toString()+"</html>";
    }
    
    
    private String strUnConvert(String str) {
    	// <html> </html> <br>�� ��� remove�Ѵ�.
    	StringBuffer buffer = new StringBuffer(str);
    	str = str.replaceAll("<html>", "");
    	str = str.replaceAll("</html>", "");
    	str = str.replaceAll("<br>", "");
    	str = str.replaceAll("<br/>", "");
    	return str;
    	
    }
    
    
    
    private Boolean isNotification(String msg) {
        /*
            ä���� �ƴ϶� �����˸��� ���, true
            "***���� �����ϼ̽��ϴ�."
            "***���� �����̽��ϴ�."
        */
        return !msg.contains(":");
    }
    
    private Boolean isLikeMsg(String msg1) {
    	/* like �޽��� ���� 
    	 * ClientBackground.sendMessage("��:" + "ooo���� ������ ����մϴ�:" + likeID);
    	 * likeID = sender:content:time
    	 * ��, ��:ooo���� ������ ����մϴ�:sender:content:time
    	 */
    	String[] seg = msg1.split(":");
    	if (whoIsSender(msg1).equals("��") && seg.length == 5) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private String getLikeSegment(String msg2, int index) {
    	//��:ooo���� ������ ����մϴ�:sender:content:time
    	//0:sender, 1: ooo���� ������ ����մϴ�, 2: ��������sender, 3: ����������, 4: ��������ð�
    	String[] seg = msg2.split(":");
    	
    	return seg[index];
    	
    }
    
    private BubbleSet findBubbleByID(String bbID) {
    	String id;
    	// BubbleSet���� ����Ʈ�� bts���� bbID�� ��ġ�ϴ� �� ã���� ��.
    	for (BubbleSet bs : bts) {
    		id = BubbleSet.makeBubbleID((JPanel)(bs.getComponent(0)));
    		if (id.equals(bbID))
    			return bs;
    	}
    	return null;
    }
    
    private String typeOfSender(String msg6) {
        return msg6.substring(0, 1);
    }
    
    private String whoIsSender(String msg3) {
        int indexColon = msg3.indexOf(":");  // msg���� ���� ���� ������ : => �г��� ��Ģ - :�� �����ϸ� �ȵ�!

        return msg3.substring(2, indexColon);
    }

    private String getMsgContent(String msg4) {
        int indexColon = msg4.indexOf(":");  // msg���� ���� ���� ������ : => �г��� ��Ģ - :�� �����ϸ� �ȵ�!

        return msg4.substring(indexColon+1);
    }

    private String removeLastEnter(String msg5) {
        if (msg5.charAt(msg5.length()-1) == '\n') {
            return msg5.substring(0, msg5.length()-1);
        } else return msg5;
    }
}