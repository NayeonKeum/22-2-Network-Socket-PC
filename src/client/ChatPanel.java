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

//�г� Ŭ���� (�׸��� ���ƾƿ�)
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
	 /* sp ���� �� ���� : builder.getPanel() */
	 setSize(500, 500);
	 
	 add(sp_builder.getPanel());
	 
 }
 public void addpanel(JPanel ob) {
	 /* �� 1�� �ø��� */
	 sp_layout.appendRow(new RowSpec(RowSpec.CENTER, Sizes.DEFAULT, RowSpec.DEFAULT_GROW));
	
	 // object ���̱�
	 sp_builder.add(ob, cc.xy(1, sp_layout.getRowCount()));
	 
	 // ��ũ�� ������ ������
	 if (window.scrollPane != null) {
		 JScrollBar sb = window.scrollPane.getVerticalScrollBar();
		 sb.setValue( window.scrollPane.getHeight() );
	 }
	 
	 
	 
	 //window.scrollPane.getVerticalScrollBar().setValue(window.scrollPane.getVerticalScrollBar().getMaximum());	// �ڵ���ũ��
 }
}