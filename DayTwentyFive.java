
public class DayTwentyFive {
	boolean[] tape;
	Turing turingMachine;
	int size;
	final static int ADD = 100;

	public void start() {
		init();
		part1(12586542);
		// part1(10);
		// test();
	}

	private void test() {
		tape[0] = true;
		printTape();
		int cursor = tape.length - 1;
		tape[cursor] = true;
		printTape();
		expandTapeRight();
		printTape();
		expandTapeLeft();
		printTape();

	}

	private void part1(int step) {
		// printTape();
		for (int i = 0; i < step; i++) {
			int cursor = turingMachine.getCursorPosition();
			tape[cursor] = turingMachine.move(tape[cursor]);
			// printTape();
		}
		// Checksum
		int total = 0;
		for (int i = 0; i < tape.length; i++) {
			if (tape[i]) {
				total++;
			}
		}
		System.out.println("Total 1's = " + total);
	}

	private void init() {
		size = 100;
		tape = new boolean[size];
		for (int i = 0; i < tape.length; i++) {
			tape[i] = false;
		}
		turingMachine = new Turing();
	}

	private void expandTapeLeft() {
		int add = ADD;
		size = size + add;
		turingMachine.setCursorPosition(add);
		boolean[] extended = new boolean[size];
		for (int i = 0; i < add; i++) {
			extended[i] = false;
		}
		for (int j = add; j < tape.length; j++) {
			extended[j] = tape[j - add];
		}
		tape = extended;
	}

	private void expandTapeRight() {
		int add = ADD;
		// turingMachine.setCursorPosition(size - 1);
		size = size + add;
		boolean[] extended = new boolean[size];
		for (int i = turingMachine.getCursorPosition() + 1; i < size; i++) {
			extended[i] = false;
		}
		for (int j = 0; j < tape.length; j++) {
			extended[j] = tape[j];
		}
		tape = extended;
	}

	private void printTape() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tape.length; i++) {
			if (i == turingMachine.getCursorPosition()) {
				sb.append("[");
			}
			if (tape[i]) {
				sb.append(" 1 ");
			} else {
				sb.append(" 0 ");
			}
			if (i == turingMachine.getCursorPosition()) {
				sb.append("]");
			}
		}
		System.out.println(sb.toString());
	}

	class Turing {
		private int cursorPosition;
		private int state;

		public Turing() {
			// TODO Auto-generated constructor stub
			cursorPosition = size / 2;
			state = 0;// State A
		}

		public int getCursorPosition() {
			return cursorPosition;
		}

		public void setCursorPosition(int cursorPosition) {
			this.cursorPosition = cursorPosition;
		}

		private void moveCursorLeft() {
			if (cursorPosition == 0) {
				expandTapeLeft();
			} 
				cursorPosition--;
			
		}

		private void moveCursorRight() {
			if (cursorPosition == (size - 1)) {
				expandTapeRight();
			} 
				cursorPosition++;
			
		}

		public boolean move(boolean value) {
			switch (state) {
			case 0:// A
				if (value) {
					moveCursorLeft();
					state++;
					return !value; // 0
				} else {
					moveCursorRight();
					state++;
					return !value; // 1
				}
			case 1:// B
				if (value) {
					moveCursorLeft();
					return value;
				} else {
					moveCursorRight();
					state++;
					return value;
				}
			case 2:// C
				if (value) {
					moveCursorLeft();
					state = 0;
					return !value;
				} else {
					moveCursorRight();
					state++;
					return !value;
				}
			case 3:// D
				if (value) {
					moveCursorLeft();
					state = 5;
					return value;
				} else {
					moveCursorLeft();
					state++;
					return !value;
				}
			case 4:// E
				if (value) {
					moveCursorLeft();
					state--;
					return !value;
				} else {
					moveCursorLeft();
					state = 0;
					return !value;
				}
			case 5:// F
				if (value) {
					moveCursorLeft();
					state--;
					return value;
				} else {
					moveCursorRight();
					state = 0;
					return !value;
				}
			default:
				return true;
			}
		}
	}
}
