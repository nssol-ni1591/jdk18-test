package util;

public interface ValidationStrategy {
	default boolean execute(String s) { return false; };
}
