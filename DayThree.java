
public class DayThree {
	int[][] grid;

	enum Direction {
		N, S, E, W
	}

	public void start(String input) {
		grid = new int[200][200];
		// fill grid with 0's
		reset();
		part2(Integer.parseInt(input));
	}

	private void reset() {
		for (int y = 0; y < grid.length; y++) {

			for (int x = 0; x < grid.length; x++) {
				grid[y][x] = 0;
			}
		}
	}

	public void part2(int input) {
		int y, x;
		y = x = 100;
		grid[y][x] = 1;
		// Longest amount of steps to traverse in a direction
		int steps = 1;
		// Number of times the step number needs to be repeated
		int counter = 2;
		Direction direction = Direction.E;
		while (grid[y][x] <= input) {
			//
			for (int i = 1; i <= counter; i++) {
				for (int j = 1; j <= steps; j++) {
					switch (direction) {
					case E:
						x++;
						grid[y][x] = adjacentTotals(x, y);
						break;
					case W:
						x--;

						grid[y][x] = adjacentTotals(x, y);
						break;
					case S:
						y++;

						grid[y][x] = adjacentTotals(x, y);
						break;
					case N:
						y--;

						grid[y][x] = adjacentTotals(x, y);
						break;
					}
					if (grid[y][x] > input) {
						break;
					}
				}

				if (grid[y][x] > input) {
					break;
				}
				if (direction == Direction.E) {
					direction = Direction.N;
				} else if (direction == Direction.N) {
					direction = Direction.W;
				} else if (direction == Direction.W) {
					direction = Direction.S;
				} else {
					direction = Direction.E;
				}
			}
			steps++;

		}
		System.out.println(grid[y][x]);
	}

	private int adjacentTotals(int x, int y) {
		int totals = 0;
		totals += grid[y + 1][x + 1];
		totals += grid[y][x + 1];
		totals += grid[y][x - 1];
		totals += grid[y - 1][x - 1];
		totals += grid[y + 1][x - 1];
		totals += grid[y - 1][x + 1];
		totals += grid[y + 1][x];
		totals += grid[y - 1][x];
		return totals;
	}
}
