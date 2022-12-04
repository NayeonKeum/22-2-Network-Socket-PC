package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class myColorRenderer extends JLabel implements TableCellRenderer {
	
	public myColorRenderer() {
		setOpaque(true);
		setBackground(Color.decode("#fcddca"));  //Color.decode("#fcddca")  new Color(247,99,12)
		setForeground(new Color(247,99,12));  //new Color(255,247,242)
		setBorder(BorderFactory.createEtchedBorder());
		setHorizontalAlignment(JLabel.CENTER);
		setPreferredSize(new Dimension(100, 29));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected,
                                                boolean hasFocus, int row, int column) {
		setText(value.toString());
		return this;
	}
}