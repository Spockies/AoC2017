import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayTwentyThree {
	Map<Character, Integer> registers;
	List<String> step;
	int multiCounter;

	public void start(String path) {
		step = AdventOfCode.readFile(path);
		init();
		// part1();
		part3();
	}

	private void part1() {
		int instructionPointer = 0;
		String[] instructions;
		while (instructionPointer < step.size()) {
			instructions = step.get(instructionPointer).split(" ");

			switch (instructions[0]) {
			case "set": // Set Value
				if (registers.containsKey(instructions[2].charAt(0))) {
					int n = registers.get(instructions[2].charAt(0));
					set(instructions[1], n);
				} else {
					set(instructions[1], Integer.parseInt(instructions[2]));
				}
				instructionPointer++;
				break;
			case "sub": // Decrease Value
				if (registers.containsKey(instructions[2].charAt(0))) {
					int n = registers.get(instructions[2].charAt(0));
					sub(instructions[1], n);
				} else {
					sub(instructions[1], Integer.parseInt(instructions[2]));
				}
				instructionPointer++;
				break;
			case "mul": // Multiply Value
				if (registers.containsKey(instructions[2].charAt(0))) {
					int n = registers.get(instructions[2].charAt(0));
					mul(instructions[1], n);
				} else {
					mul(instructions[1], Integer.parseInt(instructions[2]));
				}
				instructionPointer++;
				break;
			case "jnz": // Jump
				instructionPointer += jump(instructions[1], Integer.parseInt(instructions[2]));
				break;

			default:
				break;
			}

		}

		System.out.println(multiCounter);
	}

	private void part3() {
		int a, b, c, d, e, f, g, h;

		b = c = d = e = f = g = h = 0;

		h = 0;
		b = (57 * 100) + 100000;
		c = b + 17000;
		for (; b != c; b += 17) {
			f = 1;
			for (d = 2; d != b; d++) {
				for (e = 2; e != b; e++) {
					if ((d * e) == b) {
						f = 0;
					}
				}
			}
			if (f == 0) {
				h++;
			}
		}

		System.out.println(h);
	}

	private void part4() {
		int a, b, c, f, h;

		b = c = f = h = 0;

		h = 0;
		b = (57 * 100) + 100000;
		c = b + 17000;
		for (int i = 0; i < 1001; i++) {
			f = 1;
			for (int d = 2; d * d < b; d++) {
				if (b % d == 0) {
					f = 0;
					break;
				}
			}
			if (f == 0) {
				h++;
			}
			b += 17;
		}
		System.out.println(h);
	}

	private void part2() {// set b 57
		// set c b
		// jnz a 2 to Alpha
		// jnz 1 5 to Beta
		// Alpha
		// mul b 100
		// sub b -100000
		// set c b
		// sub c -17000
		// Beta
		// set f 1
		// set d 2
		// Epsilon
		// set e 2
		// Delta
		// set g d
		// mul g e
		// sub g b
		// jnz g 2 To Codex
		// set f 0
		// Codex
		// sub e -1
		// set g e
		// sub g b
		// jnz g -8 to Delta
		// sub d -1
		// set g d
		// sub g b
		// jnz g -13 to Epsilon
		// jnz f 2 to Foxtrot
		// sub h -1
		// Foxtrot
		// set g b
		// sub g c
		// jnz g 2 to Giga
		// jnz 1 3 to Hearth
		// Giga
		// sub b -17
		// jnz 1 -23 to Beta
		// Hearth
		int a, b, c, d, e, f, g, h;

		b = c = d = e = f = g = h = 0;
		a = 1;
		b = 57;
		c = b;
		if (a != 0) {
			b *= 100;
			b -= -100000;
			c = b;
			c -= -17000;
		}
		do {
			// Beta
			f = 1;
			d = 2;
			// Epsilon
			do {
				e = 2;
				do {
					// Delta
					g = d;
					g *= e;
					g -= b;

					if (g == 0) {
						f = 0;
					}
					// Codex
					e -= -1;
					g = e;
					g -= b;
				} while (g != 0);
				d -= -1;
				g = d;
				g -= b;
			} while (g != 0);
			if (f == 0) {
				h++;
			}
			g = b;
			g -= c;
			if (g == 0) {
				break;
			} else {
				b -= -17;
			}
		} while (g != 0);
		System.out.println(h);
	}

	private void init() {
		registers = new HashMap<>();
		// Add registers A-H
		for (int i = 0; i < 8; i++) {
			Character c = Character.valueOf((char) ('a' + i));
			System.out.println(c);
			registers.put(c, 0);
		}
		registers.put('a', 1);
		multiCounter = 0;
	}

	private void set(String c, int val) {
		if (registers.containsKey(c.charAt(0))) {
			char ch = c.charAt(0);
			registers.put(ch, val);
		}
		// System.out.println("Setting register " + c + " to " + val);
	}

	private void sub(String c, int val) {
		if (registers.containsKey(c.charAt(0))) {

			char ch = c.charAt(0);
			int s = registers.get(ch) - val;
			registers.put(ch, s);
		}

		// System.out.println("Subtracting register " + c + " by " + val);
	}

	private void mul(String c, int val) {
		if (registers.containsKey(c.charAt(0))) {
			char ch = c.charAt(0);
			int m = registers.get(ch) * val;
			registers.put(ch, m);
		}
		// System.out.println("Multiplying register " + c + " by " + val);
		multiCounter++;
	}

	private int jump(String c, int val) {
		if (registers.containsKey(c.charAt(0))) {
			char ch = c.charAt(0);
			int n = registers.get(ch);
			if (n != 0) {
				// System.out.println("Jumping by " + val);
				return val;
			}
		} else if (Integer.parseInt(c) != 0) {
			// System.out.println("Jumping by " + val);
			return val;
		}
		return 1;
	}
}

/*
 * PsuedoCode: Initial setup of registers a -> h set to zero via Map data
 * structure
 * 
 * Fetch instructions from file input Follow instructions
 * 
 */