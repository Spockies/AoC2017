
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class DayEightSample {

	private static Map<String, BiPredicate<Integer, Integer>> comparisons = new HashMap<>();
	static {
		comparisons.put("==", (x, y) -> x.equals(y));
		comparisons.put("!=", (x, y) -> !x.equals(y));
		comparisons.put("<", (x, y) -> x < y);
		comparisons.put(">", (x, y) -> x > y);
		comparisons.put("<=", (x, y) -> x <= y);
		comparisons.put(">=", (x, y) -> x >= y);

	}

	private static Map<String, ToIntBiFunction<Integer, Integer>> commands = new HashMap<>();
	static {
		commands.put("inc", (x, y) -> x + y);
		commands.put("dec", (x, y) -> x - y);
	}

	public static void main(String[] args) {
		List<String> input = AdventOfCode.readFile("day8.txt");
		Map<String, Integer> registers = new HashMap<>();
		int highest = 0;

		for (String each : input) {
			String[] token = each.split(" ");
			String name = token[0];
			String command = token[1];
			int amount = Integer.parseInt(token[2]);
			String testReg = token[4];
			String comp = token[5];
			int testAmt = Integer.parseInt(token[6]);

			registers.putIfAbsent(name, 0);

			if (comparisons.get(comp).test(registers.getOrDefault(testReg, 0), testAmt)) {

				int current = registers.get(name);
				registers.put(name, commands.get(command).applyAsInt(current, amount));

				if (registers.get(name) > highest) {
					highest = registers.get(name);
				}
			}
		}

		System.out.println("Part 1: " + Collections.max(registers.values()));
		System.out.println("Part 2: " + highest);

	}
}