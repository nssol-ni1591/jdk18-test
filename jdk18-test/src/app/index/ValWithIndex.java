package app.index;
public class ValWithIndex<T> {
	private final T val;
	private final int index;

	public ValWithIndex(T val, int index) {
		this.val = val;
		this.index = index;
	}

	public T getVal() {
		return val;
	}

	public int getIndex() {
		return index;
	}
}
