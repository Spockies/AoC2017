import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.*;

public class DayEleven {
	List<String> instructions;

	public void start(String path) {
		instructions = AdventOfCode.readFile(path);
		part1();
	}

	private void part1() {
		String[] tokens = instructions.get(0).split(",");
		int x, y, z;
		x = y = z = 0;
		int i = 0;
		List<Integer> distances = new ArrayList<>();
		while (i < tokens.length) {
			switch (tokens[i]) {
			case "n":
				y++;
				z--;
				break;
			case "nw":
				x--;
				y++;
				break;
			case "ne":
				x++;
				z--;
				break;
			case "s":
				z++;
				y--;
				break;
			case "se":
				x++;
				y--;
				break;
			case "sw":
				x--;
				z++;
				break;
			}
			distances.add((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2);
			i++;
		}

		System.out.println((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2);
		System.out.println(Collections.max(distances));
	}
}
