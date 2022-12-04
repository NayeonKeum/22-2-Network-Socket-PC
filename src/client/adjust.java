package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

// 리스트 조정 클래스
class adjust extends JPanel {
    String editstring; // 텍스트 필드에 들어가는 스트링
    ArrayList<BubbleSet> bts; // 서브 버튼 패널 객체를 요소로 담는 동적 배열 
    ArrayList<JLabel> find; // 서브 버튼을 요소로 담는 정적 동적 배열  
    static int curr=window.curr; // 현재 가장 밑에 있는 위치한 패널의 인덱스 
    int cset; // 색 설정에 관한 변수
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
        	if (isNotification(msg)) {  // 안내메시지
        		sender = "서버";
        		addBubblePanel(false);	// 버블 추가
        	} else {
        		/* 메시지 분석 후 버블 생성 */
	        	sender = whoIsSender(msg);
		        // 내 메시지
		        if (sender.equals(ClientGui.nickName)) {
		        	addBubblePanel(true);	// 버블 추가
		        } else if (sender.equals("인원수")) {
		        	// 버블 추가하지 않음.
		        	// 상단패널의 인원수를 업데이트 - ppCount, lb_ppCount
		        	String content = strConvert(getMsgContent(msg));
		        	window.lb_ppCount.setText(content+"명\t");
		        	
		        } //else if (isLikeMsg(msg)) {
		        
		        	//♥:ooo님이 마음에 들어합니다:sender:content:time
		        	//0:sender, 1: ooo님이 마음에 들어합니다, 2: 눌린버블sender, 3: 눌린버블내용, 4: 눌린버블시각
		        	
		        	//addBubblePanel(false);
		        //}
		        else {  // 상대방 메시지
		        	addBubblePanel(false);	// 버블 추가
		        }
        	}
        }

    }
    
    // 색 지정 메소드 
    public void selectColor(int idx, boolean isMe) {
        // 버블 내용 부분만 배경 색 지정
        // addBubblePanel()에서 curr++한 후, setBubbleColor(curr);로 호출한다.
    	if (isMe)
    		bts.get(idx).subbt.get(1).setBackground(Color.yellow);
    	else 
    		bts.get(idx).subbt.get(1).setBackground(Color.white);
    	
        bts.get(idx).subbt.get(1).setOpaque(true);
        
        // 글자 색 지정
        /* sender 글자 색
         * 중앙관리본부 : 초록색 #47a877
         * 지역소방본부 : 주황색 #d98209
         * 소방대원 : 빨간색 #ba3d3d
         * 그외 : 회색 #9aaab8 */
        if (typeOfSender(msg).equals("1"))
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#47a877"));	// sender
        else if (typeOfSender(msg).equals("2"))
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#d98209"));	// sender
        else if (typeOfSender(msg).equals("3"))
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#ba3d3d"));	// sender
        else
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#9aaab8"));	// sender
        //bts.get(idx).subbt.get(0).setForeground(Color.decode("#9aaab8"));	// sender - 회색 #9aaab8
        //bts.get(idx).subbt.get(0).setFont(new Font("Serif", Font.PLAIN, 10));
        bts.get(idx).subbt.get(1).setForeground(Color.black);	// 메시지 내용 - 검은색
        //bts.get(idx).subbt.get(1).setFont(new Font("돋움", Font.PLAIN, 13));
        bts.get(idx).subbt.get(2).setForeground(Color.decode("#8a9aa8"));	// 보낸 시각 - 회색 #8a9aa8
        //bts.get(idx).subbt.get(2).setFont(new Font("Serif", Font.PLAIN, 10));
        bts.get(idx).subbt.get(3).setForeground(Color.decode("#d6546e"));	// 좋아요 - 붉은색 #d6546e
        //bts.get(idx).subbt.get(3).setFont(new Font("Serif", Font.PLAIN, 10));
        
        if (sender.equals("서버")) {
        	bts.get(idx).subbt.get(1).setBackground(Color.blue);
        	bts.get(idx).subbt.get(1).setForeground(Color.white);	// 메시지 내용 - 흰색
        	bts.get(idx).subbt.get(3).setText("");
        } else if (sender.equals("♥")) {
        	bts.get(idx).subbt.get(0).setForeground(Color.decode("#ba3d3d"));	// 빨강 #ba3d3d  핑크 #fa7fa2
        	bts.get(idx).subbt.get(1).setBackground(Color.decode("#ba3d3d"));	// 빨강 #ba3d3d  핑크 #fa7fa2
        	bts.get(idx).subbt.get(1).setForeground(Color.white);	// 메시지 내용 - 흰색
        } else if (sender.equals("위치")) {
        	bts.get(idx).subbt.get(1).setBackground(Color.decode(window.color_chatRoom));  // 채팅방 색깔과 동일
        	bts.get(idx).subbt.get(1).setForeground(Color.blue);	// 메시지 내용 - 파랑
        	bts.get(idx).subbt.get(3).setText("");
        }
        
    }
    
    // 패널 추가
    public void addBubblePanel(boolean isMe) {	// 마지막 칸에 새 버블을 만든다.
    	curr++;
        // 한 칸 더 만들기 
        // index curr에 새 패널을 만든다.
        // 동적 배열 추가
        String content = getMsgContent(msg);
        String time_sent = time;
        String sender0 = sender;
        if (isMe)
        	sender0 = "";
        else if (sender.equals("♥")) {
        	aSenderlikes = getLikeSegment(msg, 1);	//ooo님이 마음에 들어합니다.
        	likedsender = getLikeSegment(msg, 3);  	// 눌린 버블 sender
        	likedcontent = getLikeSegment(msg, 4);  // 눌린 버블 내용
        	likedcontent = strUnConvert(likedcontent);
        	/*
        	 *  else if (sender.equals("♥")) {
        	aSenderlikes = getLikeSegment(msg, 1);	//ooo님이 마음에 들어합니다.
        	likedsender = getLikeSegment(msg, 2);  	// 눌린 버블 sender
        	likedcontent = getLikeSegment(msg, 3);  // 눌린 버블 내용
        	likedcontent = strUnConvert(likedcontent);
        	String likedtime = getLikeSegment(msg, 4);  // 눌린 버블 전송시각
        	String likedID = likedsender+likedcontent+likedtime;
        	BubbleSet bbs = findBubbleByID(likedID);
        	JLabel likeLabel = (JLabel) ((JPanel) bbs.getComponent(0)).getComponent(3);	// == subbt.get(3)
        	likeLabel.setText("좋아요");
        	*/
        	
        	content = aSenderlikes + " [ " + likedsender + " : " + likedcontent + " ]";  // ooo님이 마음에 들어합니다. [ likedsender : likedcontent]
        
        }
        content = strConvert(content);
        
        /*
        else if (isLikeMsg(msg)) {
    		content = aSenderlikes + " [ " + likedsender + " : " + likedcontent + " ]";  // ooo님이 마음에 들어합니다. [ likedsender : likedcontent]
    	} */
        
        String[] msg_set = {sender0, content, time_sent, ""};
        bts.add(new BubbleSet(msg_set, isMe));
        
        // panel에 패널 객체 넘겨주고 거기서 추가시킴
        p2.addpanel(bts.get(curr));
        
        // 메시지 입력칸 초기화 
        window.edit.setText("");
        window.edit.requestFocus();
        
        // 새 버블에 색 지정 
        selectColor(curr, isMe);
       
        // GUI 창 업데이트
        p2.revalidate();
        p2.repaint();
        
    }
    
    
    
    private String strConvert(String str) {
    	// 글자 수가 limitByteLength를 넘어갈 때마다 <br>을 추가한다. 
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
    	// <html> </html> <br>을 모두 remove한다.
    	StringBuffer buffer = new StringBuffer(str);
    	str = str.replaceAll("<html>", "");
    	str = str.replaceAll("</html>", "");
    	str = str.replaceAll("<br>", "");
    	str = str.replaceAll("<br/>", "");
    	return str;
    	
    }
    
    
    
    private Boolean isNotification(String msg) {
        /*
            채팅이 아니라 정보알림일 경우, true
            "***님이 접속하셨습니다."
            "***님이 나가셨습니다."
        */
        return !msg.contains(":");
    }
    
    private Boolean isLikeMsg(String msg1) {
    	/* like 메시지 형식 
    	 * ClientBackground.sendMessage("♥:" + "ooo님이 마음에 들어합니다:" + likeID);
    	 * likeID = sender:content:time
    	 * 즉, ♥:ooo님이 마음에 들어합니다:sender:content:time
    	 */
    	String[] seg = msg1.split(":");
    	if (whoIsSender(msg1).equals("♥") && seg.length == 5) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private String getLikeSegment(String msg2, int index) {
    	//♥:ooo님이 마음에 들어합니다:sender:content:time
    	//0:sender, 1: ooo님이 마음에 들어합니다, 2: 눌린버블sender, 3: 눌린버블내용, 4: 눌린버블시각
    	String[] seg = msg2.split(":");
    	
    	return seg[index];
    	
    }
    
    private BubbleSet findBubbleByID(String bbID) {
    	String id;
    	// BubbleSet들의 리스트인 bts에서 bbID와 일치하는 걸 찾으면 됨.
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
        int indexColon = msg3.indexOf(":");  // msg에서 가장 먼저 나오는 : => 닉네임 규칙 - :를 포함하면 안됨!

        return msg3.substring(2, indexColon);
    }

    private String getMsgContent(String msg4) {
        int indexColon = msg4.indexOf(":");  // msg에서 가장 먼저 나오는 : => 닉네임 규칙 - :를 포함하면 안됨!

        return msg4.substring(indexColon+1);
    }

    private String removeLastEnter(String msg5) {
        if (msg5.charAt(msg5.length()-1) == '\n') {
            return msg5.substring(0, msg5.length()-1);
        } else return msg5;
    }
}