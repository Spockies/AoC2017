import java.util.*;

public class AdventDayFive {
	private List<String> list;

	public void start(String file) {
		list = AdventOfCode.readFile(file);
		System.out.println(part1());
		System.out.println(part2());
	}

	private int part1() {
		List<Integer> instruction = new ArrayList<Integer>();
		for (String i : list) {
			instruction.add(Integer.valueOf(i));
		}
		int step = 0;
		int currentPosition = 0;
		int move = 0;
		while (currentPosition < instruction.size()) {
			move = instruction.get(currentPosition);
			instruction.set(currentPosition, move + 1);
			currentPosition += move;
			step++;
		}
		return step;
	}

	private int part2() {
		List<Integer> instruction = new ArrayList<Integer>();
		for (String i : list) {
			instruction.add(Integer.valueOf(i));
		}
		int step = 0;
		int currentPosition = 0;
		int move = 0;
		while (currentPosition < instruction.size()) {
			move = instruction.get(currentPosition);
			if (move > 2) {
				instruction.set(currentPosition, move - 1);
			} else {
				instruction.set(currentPosition, move + 1);
			}
			currentPosition += move;
			step++;
		}
		return step;
	}
}
