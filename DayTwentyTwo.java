import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayTwentyTwo {
	enum Face {
		Up, Down, Left, Right
	};

	private char[][] grid;
	List<String> gridMap;
	Sporifica virus;
	int mapSize = 25000;

	private void init() {
		grid = new char[mapSize][mapSize];
		int center = mapSize / 2;
		// Clean the grid
		cleanGrid(grid);
		int width = gridMap.get(0).length();
		int length = gridMap.size();
		int x = center - (width / 2);
		int y = center - (length / 2);
		fillMap(x, y, width, length);
		virus = new Sporifica(center, center);
	}

	private void expandGrid() {
		int newSize = mapSize * 2;
		char[][] newGrid = new char[newSize][newSize];
		cleanGrid(newGrid);
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				newGrid[i + mapSize][j + mapSize] = grid[i][j];
			}
		}
		virus.posX += mapSize;
		virus.posY += mapSize;
		mapSize = newSize;
		grid = newGrid;
	}

	private void cleanGrid(char[][] g) {
		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g.length; j++) {
				g[i][j] = '.';
			}
		}
	}

	private void printMap(char[][] p) {
		for (int i = 0; i < p.length; i++) {
			String str = "";
			for (int j = 0; j < p.length; j++) {
				str += p[i][j];
			}
			System.out.println(str);
		}
	}

	private void fillMap(int x, int y, int width, int length) {
		// System.out.println("X coord: " + x);
		// System.out.println("Y coord: " + y);
		// System.out.println("Width: " + width);
		// System.out.println("Length: " + length);
		for (int i = y; i < length + y; i++) {
			for (int j = x; j < width + x; j++) {
				char c = gridMap.get(i - y).charAt(j - x);
				grid[i][j] = c;
			}
		}
	}

	public void start(String path) {
		gridMap = AdventOfCode.readFile(path);
		init();
		// test();
		 part1();
		//part2();
	}

	private void test() {

	}

	private void part1() {
		for (int i = 0; i < 10000; i++) {

			burst();

		}
		System.out.println(virus.getInfectionCounter());
	}

	private void part2() {
		virus.resetInfectionCounter();
		for (int i = 0; i < 10000000; i++) {
			burst2();
		}
		System.out.println(virus.getInfectionCounter());
	}

	private void burst() {
		virus.infect();
		virus.move();
		// printMap(grid);
	}

	private void burst2() {
		virus.infect2();
		virus.move();
		// printMap(grid);
	}

	private class Sporifica {

		private int posX, posY;
		private int infectionCounter;
		private Face facing;

		public Sporifica(int x, int y) {
			// TODO Auto-generated constructor stub
			facing = Face.Up;
			infectionCounter = 0;
			posX = x;
			posY = y;
		}

		public int getInfectionCounter() {
			return infectionCounter;
		}

		public void resetInfectionCounter() {
			this.infectionCounter = 0;
		}

		public void infect() {
			if (grid[posY][posX] == '.') {
				// Infect
				// Turn left/counterclockwise
				facing = counterClockwise(facing);
				grid[posY][posX] = '#';
				infectionCounter++;
			} else {
				// Clean
				facing = clockwise(facing);
				grid[posY][posX] = '.';
			}
		}

		public void infect2() {
			if (grid[posY][posX] == '.') {
				// Clean-> Weaken
				facing = counterClockwise(facing);
				grid[posY][posX] = 'W';
			} else if (grid[posY][posX] == 'W') {
				// Weaken -> Infect
				grid[posY][posX] = '#';
				infectionCounter++;
			} else if (grid[posY][posX] == '#') {
				// Infect -> Flag
				facing = clockwise(facing);
				grid[posY][posX] = 'F';
			} else if (grid[posY][posX] == 'F') {
				// Flagged -> Clean
				facing = aboutFace(facing);
				grid[posY][posX] = '.';
			}
		}

		public void move() {
			switch (facing) {
			case Up:
				if (posY - 1 <= 0) {
					expandGrid();
				}
				posY--;
				break;
			case Down:
				if (posY + 1 >= mapSize - 1) {
					expandGrid();
				}
				posY++;
				break;
			case Right:
				if (posX + 1 >= mapSize - 1) {
					expandGrid();
				}
				posX++;
				break;
			case Left:
				if (posX - 1 <= 0) {
					expandGrid();
				}
				posX--;
				break;

			}
		}

		private Face clockwise(Face c) {
			switch (c) {
			case Up:
				return Face.Right;
			case Right:
				return Face.Down;
			case Down:
				return Face.Left;
			case Left:
				return Face.Up;
			}
			return c;
		}

		private Face counterClockwise(Face c) {
			switch (c) {
			case Up:
				return Face.Left;
			case Right:
				return Face.Up;
			case Down:
				return Face.Right;
			case Left:
				return Face.Down;
			}
			return c;
		}

		private Face aboutFace(Face c) {
			switch (c) {
			case Up:
				return Face.Down;
			case Right:
				return Face.Left;
			case Down:
				return Face.Up;
			case Left:
				return Face.Right;
			}
			return c;
		}
	}

}
