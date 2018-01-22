import java.time.format.TextStyle;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

// Dueling Generators
public class DayFifteen {
	public static final int MULT_A = 16807;
	public static final int MULT_B = 48271;
	List<String> input;
	Generator a;
	Generator b;
	Judge judge;

	public void start(String path) {
		input = AdventOfCode.readFile(path);
		String[] token0 = input.get(0).split(" ");
		String[] token1 = input.get(1).split(" ");
		judge = new Judge();
		a = new Generator(MULT_A, Integer.parseInt(token0[4]), 4);
		b = new Generator(MULT_B, Integer.parseInt(token1[4]), 8);
		test();
		test2();
		 part1(40000000);
		part2(5000000);
	}

	private void test() {
		Generator testA = new Generator(MULT_A, 65);
		Generator testB = new Generator(MULT_B, 8921);
		Judge testJudge = new Judge();
		for (int i = 0; i < 40000000; i++) {
			long testValA = testA.nextValue();
			long testValB = testB.nextValue();
			// System.out.println(testValA + " " + testValB);
			testJudge.evaluate(testValA, testValB);
		}
		System.out.println(testJudge.getCounter());
	}

	private void test2() {
		Generator testA = new Generator(MULT_A, 65, 4);
		Generator testB = new Generator(MULT_B, 8921, 8);
		Judge testJudge = new Judge();
		for (int i = 0; i < 5000000; i++) {
			long testValA = testA.nextLimiterValue();
			long testValB = testB.nextLimiterValue();
			// System.out.println(testValA + " " + testValB);
			testJudge.evaluate(testValA, testValB);
		}
		System.out.println(testJudge.getCounter());
	}

	private void part1(int numberOfPairings) {

		for (int i = 0; i < numberOfPairings; i++) {
			judge.evaluate(a.nextValue(), b.nextValue());
		}
		System.out.println(judge.getCounter());
	}

	private void part2(int numberOfPairings) {
		for (int i = 0; i < numberOfPairings; i++) {
			judge.evaluate(a.nextLimiterValue(), b.nextLimiterValue());
		}
		System.out.println(judge.getCounter());
	}

	// Class to hold each generator's status
	public class Generator {
		private int multi;
		private long value;
		int limiter;

		public Generator(int multiplier, long startValue) {
			multi = multiplier;
			value = startValue;
			limiter = 1;
		}

		public Generator(int multiplier, long startValue, int limit) {
			multi = multiplier;
			value = startValue;
			limiter = limit;
		}

		public long getValue() {
			return value;
		}

		public long nextValue() {
			long newValue = 0;
			newValue = (value * multi) % Integer.MAX_VALUE;
			// System.out.println(newValue);
			// System.out.println(value * multi);
			value = newValue;
			return newValue;
		}

		public long nextLimiterValue() {
			long newValue = 0;
			newValue = (value * multi) % Integer.MAX_VALUE;
			// System.out.println(newValue);
			// System.out.println(value * multi);
			value = newValue;
			if ((value % limiter) == 0) {
				return newValue;
			} else {
				return nextLimiterValue();
			}
		}
	}

	// Keeps track of matches between generator outputs
	public class Judge {
		private static final int mask_16 = 65535;
		private int counter;

		public Judge() {
			counter = 0;
		}

		// Uses the 16bit MASK to AND both generator values and compare their results
		public void evaluate(long genA, long genB) {
			if (genA == -1 || genB == -1) {
				return;
			}
			if ((genA & mask_16) == (genB & mask_16)) {
				counter++;
			}
		}

		public int getCounter() {
			return counter;
		}
	}
}
