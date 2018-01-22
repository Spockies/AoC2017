import java.util.List;

public class DayNineteen {
	List<String> input;
	boolean end;

	enum Direction {
		North, East, South, West;
	}

	public void start(String path) {
		end = false;
		input = AdventOfCode.readFile(path);
		part1();
	}

	private void part1() {
		StringBuilder sb = new StringBuilder("");
		Direction dir = Direction.South;
		int y = 0;
		int step = 1;
		int x = input.get(y).indexOf("|");
		y++;
		char c;
		while (!end) {
			c = input.get(y).charAt(x);
			System.out.println(c);
			switch (c) {
			case '|': // y-axis movement
				if (dir == Direction.South) {
					y++;
				} else if (dir == Direction.North) {
					y--;
				} else if (dir == Direction.West) {
					x--;
				} else if (dir == Direction.East) {
					x++;
				}
				step++;
				break;
			case '-': // x-axis movement
				if (dir == Direction.South) {
					y++;
				} else if (dir == Direction.North) {
					y--;
				} else if (dir == Direction.West) {
					x--;
				} else if (dir == Direction.East) {
					x++;
				}
				step++;
				break;
			case '+':
				if (dir == Direction.North || dir == Direction.South) {
					char d = input.get(y).charAt(x + 1);
					System.out.println(d);
					if (d == '-') {
						System.out.println("Moving right");
						dir = Direction.East;
						x++;
					} else {
						System.out.println("Moving left");
						dir = Direction.West;
						x--;
					}
					step++;
					break;
				}
				if (dir == Direction.West || dir == Direction.East) {
					char d = input.get(y + 1).charAt(x);
					if (d == '|') {
						System.out.println("Moving down");
						dir = Direction.South;
						y++;
					} else {
						System.out.println("Moving up");
						dir = Direction.North;
						y--;
					}
					step++;
					break;
				}
				break;
			case ' ':// end of the path
				end = true;
				break;
			default:
				// letter
				sb.append(c);
				if (dir == Direction.South) {
					y++;
				} else if (dir == Direction.North) {
					y--;
				} else if (dir == Direction.West) {
					x--;
				} else if (dir == Direction.East) {
					x++;
				}
				step++;
				break;
			}

		}
		System.out.println(sb);
		System.out.println(step);
	}
}
