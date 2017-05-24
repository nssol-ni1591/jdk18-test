package app.strategy;

public class Validator {

	private String pattern;
	private ValidationStrategy strategy;

	public Validator() { }
	public Validator(String pattern) {
		this.pattern = pattern;
	}

	public Validator(ValidationStrategy strategy) {
		this.strategy = strategy;
	}
	public boolean validate(String s) {
		return strategy.execute(s);
	}
	public String getPattern() {
		return pattern;
	}
	/*
	public int length(String s) {
		return strategy.length(s);
	}
	*/
}
