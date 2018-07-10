package app.strategy;

import java.util.function.BiPredicate;

import util.Print;

public class Run2 {

	public Run2() {
		Human h1 = new Human("A", 100, 100, 25);
		Human h2 = new Human("A", 100, 101, 25);
		Human h3 = new Human("C", 100, 100, 25);

		Print.println("equals: " + h1.equals(h2));
		Print.println("equals: " + h1.equals(h3));

		// 途中 ... 考え方は納得できるものの、便利さがイマイチわからないので

	}

	public static void main(String[] args) {
		// Design pattern
		// (1) ストラテジ・パターン
		new Run2();
	}

	public class NameComparator implements BiPredicate<Human, Human> {
		@Override
		public boolean test(Human t, Human u) {
			return t.getName().equals(u.getName());
		}
	}
	public class HeightComparator implements BiPredicate<Human, Human> {
		@Override
		public boolean test(Human t, Human u) {
			return t.getHeight() == u.getHeight();
		}
	}
	public class WeightComparator implements BiPredicate<Human, Human> {
		@Override
		public boolean test(Human t, Human u) {
			return t.getWeight() == u.getWeight();
		}
	}
	public class AgeComparator implements BiPredicate<Human, Human> {
		@Override
		public boolean test(Human t, Human u) {
			return t.getAge() == u.getAge();
		}
	}

	public class Human {
		 
	    public final String name;
	    public final int height;
	    public final int weight;
	    public final int age;
	 
	    public Human(String name, int height, int weight, int age){
	        this.name = name;
	        this.height = height;
	        this.weight = weight;
	        this.age = age;
	    }

		public String getName() {
			return name;
		}
		public int getHeight() {
			return height;
		}
		public int getWeight() {
			return weight;
		}
		public int getAge() {
			return age;
		}
		@Override
	    public int hashCode() {
	    	return super.hashCode();
	    }
	    @Override
	    public boolean equals(Object o) {
	    	if (o == null) {
	    		return false;
	    	}
	    	if (o instanceof Human) {
	    		Human human = (Human)o;
	    		if (name.equals(human.getName())
	    				&& height == human.getHeight()
	    				&& weight == human.getWeight()
	    				&& age == human.getAge()
	    				) {
	    			return true;
	    		}
	    				
	    	}
	    	return false;
	    }

	}

}
