package app.validator;

public class Validator {

//	private String pattern;
	private final ValidationStrategy strategy;

//	public Validator() { }
//	public Validator(String pattern) {
//		this.pattern = pattern;
//	}
	public Validator(ValidationStrategy strategy) {
		this.strategy = strategy;
	}

	public boolean validate(String s) {
		return strategy.execute(s);
	}
//	public String getPattern() {
//		return pattern;
//	}

}
