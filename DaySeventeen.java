import java.util.ArrayList;
import java.util.List;

//Spinlock
public class DaySeventeen {
	int rounds;
	int input;

	public void start() {
		input = 356;
		rounds = 50000000;
		// part1(input);
		part2(input);
	}

	private void part1(int step) {
		List<Integer> buffer = new ArrayList<>();
		buffer.add(0);
		int currentPosition = 0;
		for (int i = 1; i <= rounds; i++) {
			currentPosition = ((currentPosition + step) % buffer.size()) + 1;
			buffer.add(currentPosition, i);
			System.out.println(buffer.toString());
		}
		System.out.println(buffer.get(currentPosition + 1));
	}

	private void part2(int step) {
		int buffer = 1;
		int currentPosition = 1;
		for (int i = 1; i <= rounds; i++) {
			currentPosition = ((currentPosition + step) % buffer) + 1;
			buffer++;
			if (currentPosition == 1) {
				System.out.println(i);
	
			}
			// System.out.println(buffer.toString());
		}
	}
}
