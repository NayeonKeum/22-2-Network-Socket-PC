package client;

import javax.swing.*;
import javax.swing.border.Border;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//패널 클래스 (그리드 레아아웃)
class ChatPanel extends JPanel {
	static FormLayout sp_layout;
	static CellConstraints cc;
	static PanelBuilder sp_builder;
	
 public ChatPanel() {

	 sp_layout = new FormLayout(
			 "pref",
			 "fill:0:grow(0.9)"
 			);
	 sp_builder = new PanelBuilder(sp_layout);
	 cc = new CellConstraints();
	 /* sp 생성 및 구성 : builder.getPanel() */
	 setSize(500, 500);
	 
	 add(sp_builder.getPanel());
	 
 }
 public void addpanel(JPanel ob) {
	 /* 열 1개 늘리기 */
	 sp_layout.appendRow(new RowSpec(RowSpec.CENTER, Sizes.DEFAULT, RowSpec.DEFAULT_GROW));
	
	 // object 붙이기
	 sp_builder.add(ob, cc.xy(1, sp_layout.getRowCount()));
	 
	 // 스크롤 밑으로 내리기
	 if (window.scrollPane != null) {
		 JScrollBar sb = window.scrollPane.getVerticalScrollBar();
		 sb.setValue( window.scrollPane.getHeight() );
	 }
	 
	 
	 
	 //window.scrollPane.getVerticalScrollBar().setValue(window.scrollPane.getVerticalScrollBar().getMaximum());	// 자동스크롤
 }
}