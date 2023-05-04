package security;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import remote.APIUtils;

public class ObfuscatedType7a1380c69f4e3fdbaed4cbc7f00858b2<T> implements ListCellRenderer<T>{
	@SuppressWarnings("unchecked")
	@Override
	public Component getListCellRendererComponent(JList<? extends T> arg0, T arg1, int arg2, boolean arg3,
			boolean arg4) {
		try {
			Class<? extends Component>  c = (Class<? extends Component>) Class.forName(APIUtils.decode("amF2YXguc3dpbmcuSlBhbmVs"));
			Object l = Class.forName(APIUtils.decode("amF2YS5hd3QuRmxvd0xheW91dA==")).getConstructor().newInstance();
			Component comp = (Component) c.getConstructor(l.getClass().getInterfaces()[0]).newInstance(l);
			return comp;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static void main(String[] args) {
		ObfuscatedType7a1380c69f4e3fdbaed4cbc7f00858b2<String> s = new ObfuscatedType7a1380c69f4e3fdbaed4cbc7f00858b2<>();
		s.getListCellRendererComponent(null, null, 0, false, false);
	}
}
