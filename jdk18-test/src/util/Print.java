package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Print {

	static private <T> T wrapper(final Object o, final String f, final T arg) {
		System.out.println(">>>> " + f);
		try {
			Method m;
			if (arg == null) {
				m = o.getClass().getMethod(f, new Class[0]);
				return (T) m.invoke(o, null);
			}
			else {
				m = o.getClass().getMethod(f, arg.getClass());
				return (T) m.invoke(o, arg);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static public void print(final Object o, final String f) {
		wrapper(o, f, null);
		System.out.println();
	}

	static public <T> T print(final Object o, final String f, final T arg) {
		T rc = wrapper(o, f, arg);
		return rc;
	}

	static public void array(final Object o, final String f, final Object[] array) {
		//Object ret = print(o, f, array);
		//String[] array2 = (String[])ret;

		Object[] ret = wrapper(o, f, array);
		for (Object s : ret) {
			System.out.println(s.toString());
		}
		System.out.println();
	}
	static public <T extends List<?>> T list(final Object o, final String f, final T array) {
		T rc = wrapper(o, f, array);
		if (rc != null) {
			rc.forEach(System.out::println);
		}
		System.out.println("<<<<");
		return rc;
	}
}
