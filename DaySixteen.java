import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Permutation Promenade
public class DaySixteen {
	List<String> programPosition;
	List<String> input;

	public void start(String path) {
		input = AdventOfCode.readFile(path);
		String[] sequence = input.get(0).split(",");

		programPosition = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p");
		// test();
		// part1(sequence);
		part2(sequence);
	}

	private void test() {
		programPosition = Arrays.asList("a", "b", "c", "d", "e");
		System.out.println(programPosition.toString());
		decodeDance("s1");
		System.out.println(programPosition.toString());
		decodeDance("x3/4");
		System.out.println(programPosition.toString());
		decodeDance("pe/b");
		System.out.println(programPosition.toString());
	}

	private void part1(String[] sequence) {
		for (String seq : sequence) {
			decodeDance(seq);
		}
		// System.out.println(programPosition.toString());
	}

	private void part2(String[] sequence) {
		List<String> dances = new ArrayList<>();
		boolean stopDance = false;
		for (int i = 0; i < 1000000001; i++) {

			if (!stopDance) {
				for (String seq : sequence) {
					decodeDance(seq);
				}
				StringBuilder dance = new StringBuilder();
				for (String s : programPosition) {
					dance.append(s);
				}
				if (!dances.contains(dance.toString())) {
					dances.add(dance.toString());
				} else {
					stopDance = true;
				}
			} else {
				break;
			}
		}
		System.out.println(dances.get(1000000000 % dances.size() - 1));

	}

	public void decodeDance(String maneuver) {
		switch (maneuver.charAt(0)) {
		case 's':
			String shift = maneuver.substring(1);
			spin(Integer.parseInt(shift));
			break;
		case 'x':
			String ex = maneuver.substring(1);
			String[] positionSwap = ex.split("/");
			exchange(Integer.parseInt(positionSwap[0]), Integer.parseInt(positionSwap[1]));
			break;
		case 'p':
			String partner = maneuver.substring(1);
			String[] partnerSwap = partner.split("/");
			partner(partnerSwap[0], partnerSwap[1]);
			break;

		default:
			break;
		}
	}

	private void spin(int x) {
		Queue<String> queue = new LinkedList<String>();
		// System.out.println(programPosition.toString());
		for (int i = programPosition.size() - 1; i > programPosition.size() - 1 - x; i--) {
			// System.out.println(programPosition.get(i));
			queue.add(programPosition.get(i));
		}
		for (int i = programPosition.size() - 1; i > x - 1; i--) {
			programPosition.set(i, programPosition.get(i - x));
			// System.out.println(programPosition.toString());
		}
		for (int i = x - 1; i >= 0; i--) {
			// System.out.println(queue.peek());
			programPosition.set(i, queue.remove());
		}
	}

	private void exchange(int pos1, int pos2) {
		swap(pos1, pos2);
	}

	private void partner(String a, String b) {
		int pos1 = programPosition.indexOf(a);
		int pos2 = programPosition.indexOf(b);
		swap(pos1, pos2);
	}

	private void swap(int a, int b) {
		String temp = programPosition.get(a);
		programPosition.set(a, programPosition.get(b));
		programPosition.set(b, temp);
	}
}
