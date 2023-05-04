package security;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ObfuscatedType7a1380c69f4e3fdbaed4cbc7f00858b2<T> implements ListCellRenderer<T>{
	@Override
	public Component getListCellRendererComponent(JList<? extends T> arg0, T arg1, int arg2, boolean arg3,
			boolean arg4) {
		
		return new JLabel(arg1.toString());
	}
}
