package app.interceptor;

import java.util.List;

public interface RunIF<T extends List<? extends String>> {

	//List<String> p3_jdk18(List<String> list)
	//List<? extends String> p3_jdk18(List<? extends String> list)

	@PrintCall.MethodAnnotation
	T p3Jdk18(T list);

	List<? extends String> p4Jdk18(List<? extends String> list);

}
