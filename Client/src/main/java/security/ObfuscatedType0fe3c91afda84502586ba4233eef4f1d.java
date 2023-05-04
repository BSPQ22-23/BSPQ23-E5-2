package security;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import remote.APIUtils;

/**
 * This object it's obfuscated, no documentation will be made
 * @author a-rasines
 * @author MD5
 *
 * @param <T> type parameter for the class
 */
public class ObfuscatedType0fe3c91afda84502586ba4233eef4f1d<T> extends DefaultListModel<T>{

	private static final long serialVersionUID = -5101391278049785596L;
	
	public static <T> Component func0df7a7a455832c3082cfc11dccf10ee5(Class<T> typeParam) {
		try {
			Class<?> c = Class.forName(APIUtils.decode("amF2YXguc3dpbmcuSkxpc3Q="));
			return (Component) c.getConstructor((Class<?>[]) null).newInstance((T[]) null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//                                             CLASS FUNCTIONS
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public void funca0f848942ce863cf53c0fa6cc684007d(Component comp) {
		try {
			comp.getClass().getDeclaredMethod(APIUtils.decode("c2V0TW9kZWw="), ListModel.class).invoke(comp, this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Method func8520a54f128504d9a338c55fcf9e3644(String arg0, int arg1) {
		for(Method m : super.getClass().getMethods())
			if(m.getName().equals(arg0) && m.getParameterCount() == arg1)
				return m;
		return null;
	}
	public void func34ec78fcc91ffb1e54cd85e4a0924332(T objA) {
		try {
			func8520a54f128504d9a338c55fcf9e3644(APIUtils.decode("YWRkRWxlbWVudA=="), 1).invoke(this, objA);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public void func0f6969d7052da9261e31ddb6e88c136e(T objR) {
		try {
			func8520a54f128504d9a338c55fcf9e3644(APIUtils.decode("cmVtb3ZlRWxlbWVudA=="), 1).invoke(this, objR);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public void func78b82d3ed1c7fe9767dff1d97983dc9b(int iR) {
		try {
			func8520a54f128504d9a338c55fcf9e3644(APIUtils.decode("cmVtb3ZlRWxlbWVudEF0"), 1).invoke(this, iR);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
