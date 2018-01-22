import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.crypto.Cipher;

import junit.framework.Test;

//Fractal Art
public class DayTwentyOne {
	List<String> rulebook;
	final String startPixel = ".#./..#/###";
	Map<String, String> ruleMap;
	char[][] pixels;
	char[][] buffer;
	int size;

	public void start(String path) {

		rulebook = AdventOfCode.readFile(path);
		init();
		// test();
		//part1();
		part2();
	}

	private void test() {
		String str = convertString(pixels);
		String matching = findMatchingRule(".../.../...");
		// System.out.println(matching);
		boolean match = false;
		String current = str;
		int turn = 0;
		while (!match) {
			printPixel(parseString(current));
			if (findMatchingRule(current) != null) {
				System.out.println(findMatchingRule(current));
				match = true;
			} else {
				switch (turn) {
				case 0: // to 90
					System.out.println("90");
					current = rotateRight(current);
					turn++;
					break;
				case 1: // to 180
					System.out.println("180");
					current = rotateRight(current);
					turn++;
					break;
				case 2: // to 270
					System.out.println("270");
					current = rotateRight(current);
					turn++;
					break;
				case 3: // to 360
					System.out.println("360");
					current = rotateRight(current);
					turn++;
					break;
				case 4: // Original orientation flipped vertical
					System.out.println("Vertical");
					current = flipVertical(current);
					turn++;
					break;
				case 5:
					System.out.println("Vertical 90");
					current = rotateRight(current);
					turn++;
					break;
				case 6: // equivalent to flip horizontal on case 4
					System.out.println("Horizontal");
					current = rotateRight(current);
					turn++;
					break;
				case 7:
					System.out.println("Horizontal 90");
					current = rotateRight(current);
					turn++;
					break;
				default:
					break;
				}
			}
		}

		System.out.println("End test");
		System.out.println("");
	}

	private void init() {
		size = findSize(startPixel);
		pixels = new char[size][size];
		pixels = parseString(startPixel);

		//System.out.println("Printing pixel");
		//printPixel(pixels);

		System.out.println("");
		ruleMap = new HashMap<>();
		for (int i = 0; i < rulebook.size(); i++) {
			String[] rule = rulebook.get(i).split(" => ");
			ruleMap.put(rule[0], rule[1]);
		}
	}

	private void part1() {
		iterate(5);
	}
	private void part2() {
		iterate(18);
	}

	private int findSize(String s) {
		int n = 0;
		n = s.indexOf("/");
		return n;
	}

	private String convertString(char[][] p) {
		StringBuilder stream = new StringBuilder();

		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p[i].length; j++) {
				stream.append(p[i][j]);
			}
			stream.append("/");
		}
		int n = stream.length();
		String convert = stream.subSequence(0, n - 1).toString();
		// System.out.println(convert);
		return convert;
	}

	private char[][] parseString(String s) {
		String[] rows = s.split("/");
		int n = findSize(s);
		char[][] p = new char[n][n];
		for (int i = 0; i < rows.length; i++) {
			String r = rows[i];
			for (int j = 0; j < r.length(); j++) {
				p[i][j] = r.charAt(j);
			}
		}
		return p;
	}

	private String rotateRight(String s) {

		char[][] enhance;
		int n = findSize(s);
		if (n == 2) {
			enhance = parseString(s);
			char temp = enhance[0][0];
			enhance[0][0] = enhance[1][0];
			enhance[1][0] = enhance[1][1];
			enhance[1][1] = enhance[0][1];
			enhance[0][1] = temp;
		} else {
			enhance = parseString(s);
			char temp = enhance[0][0];
			// Rotate corners
			enhance[0][0] = enhance[2][0];
			enhance[2][0] = enhance[2][2];
			enhance[2][2] = enhance[0][2];
			enhance[0][2] = temp;
			// Rotate edges
			temp = enhance[0][1];
			enhance[0][1] = enhance[1][0];
			enhance[1][0] = enhance[2][1];
			enhance[2][1] = enhance[1][2];
			enhance[1][2] = temp;

		}
		return convertString(enhance);
	}

	private String flipVertical(String s) {

		char[][] enhance;
		int n = findSize(s);
		if (n == 2) {
			enhance = parseString(s);
			char temp = enhance[0][0];
			enhance[0][0] = enhance[1][0];
			enhance[1][0] = temp;
			temp = enhance[0][1];
			enhance[0][1] = enhance[1][1];
			enhance[1][1] = temp;
		} else {

			enhance = parseString(s);
			char temp1 = enhance[0][0];
			char temp2 = enhance[0][1];
			char temp3 = enhance[0][2];

			enhance[0][0] = enhance[2][0];
			enhance[0][1] = enhance[2][1];
			enhance[0][2] = enhance[2][2];

			enhance[2][0] = temp1;
			enhance[2][1] = temp2;
			enhance[2][2] = temp3;
		}
		return convertString(enhance);
	}

	private String flipHorizontal(String s) {

		char[][] enhance;
		int n = findSize(s);
		if (n == 2) {
			enhance = parseString(s);
			char temp1 = enhance[0][0];
			char temp2 = enhance[1][0];

			enhance[0][0] = enhance[0][1];
			enhance[0][0] = enhance[1][1];

			enhance[0][1] = temp1;
			enhance[1][1] = temp2;
		} else {
			enhance = parseString(s);
			char temp1 = enhance[0][0];
			char temp2 = enhance[1][0];
			char temp3 = enhance[2][0];

			enhance[0][0] = enhance[0][2];
			enhance[1][0] = enhance[1][2];
			enhance[2][0] = enhance[2][2];

			enhance[0][2] = temp1;
			enhance[1][2] = temp2;
			enhance[2][2] = temp3;

		}
		return convertString(enhance);
	}

	private void rotateRight2x2(int x, int y) {
		char temp = pixels[y][x];
		pixels[y][x] = pixels[y + 1][x];
		pixels[y + 1][x] = pixels[y + 1][x + 1];
		pixels[y + 1][x + 1] = pixels[y][x + 1];
		pixels[y][x + 1] = temp;
	}

	private void flipVertical2x2(int x, int y) {
		char temp = pixels[y][x];
		pixels[y][x] = pixels[y + 1][x];
		pixels[y + 1][x] = temp;
		temp = pixels[y][x + 1];
		pixels[y][x + 1] = pixels[y + 1][x + 1];
		pixels[y + 1][x + 1] = temp;
	}

	private void flipHorizontal2x2(int x, int y) {
		char temp1 = pixels[y][x];
		char temp2 = pixels[y + 1][x];

		pixels[y][x] = pixels[y][x + 1];
		pixels[y][x] = pixels[y + 1][x + 1];

		pixels[y][x + 1] = temp1;
		pixels[y + 1][x + 1] = temp2;
	}

	private void flipVertical3x3(int x, int y) {
		char temp1 = pixels[y][x];
		char temp2 = pixels[y][x + 1];
		char temp3 = pixels[y][x + 2];

		pixels[y][x] = pixels[y + 2][x];
		pixels[y][x + 1] = pixels[y + 2][x + 1];
		pixels[y][x + 2] = pixels[y + 2][x + 2];

		pixels[y + 2][x] = temp1;
		pixels[y + 2][x + 1] = temp2;
		pixels[y + 2][x + 2] = temp3;
	}

	private void flipHorizontal3x3(int x, int y) {
		char temp1 = pixels[y][x];
		char temp2 = pixels[y + 1][x];
		char temp3 = pixels[y + 2][x];

		pixels[y][x] = pixels[y][x + 2];
		pixels[y + 1][x] = pixels[y + 1][x + 2];
		pixels[y + 2][x] = pixels[y + 2][x + 2];

		pixels[y][x + 2] = temp1;
		pixels[y + 1][x + 2] = temp2;
		pixels[y + 2][x + 2] = temp3;
	}

	private void rotateRight3x3(int x, int y) {
		char temp = pixels[y][x];
		// Rotate corners
		pixels[y][x] = pixels[y + 2][x];
		pixels[y + 2][x] = pixels[y + 2][x + 2];
		pixels[y + 2][x + 2] = pixels[y][x + 2];
		pixels[y][x + 2] = temp;
		// Rotate edges
		temp = pixels[y][x + 1];
		pixels[y][x + 1] = pixels[y + 1][x];
		pixels[y + 1][x] = pixels[y + 2][x + 1];
		pixels[y + 2][x + 1] = pixels[y + 1][x + 2];
		pixels[y + 1][x + 2] = temp;
	}

	private List<String> partialDivisions(int subSize) {
		List<String> partials = new ArrayList<>();

		for (int y = 0; y < size; y += subSize) {
			for (int x = 0; x < size; x += subSize) {
				StringBuilder s = new StringBuilder();
				for (int i = y; i < subSize + y; i++) {
					for (int j = x; j < subSize + x; j++) {
						s.append(pixels[i][j]);
					}
					s.append("/");
				}
				int n = s.length() - 1;
				String str = s.subSequence(0, n).toString();
//				System.out.println("Partial:");
//
//				System.out.println(str);
				partials.add(str);
			}
		}

		return partials;
	}

	private String findMatchingRule(String string) {

		return ruleMap.get(string);
	}

	private void iterate(int n) {
		for (int i = 0; i < n; i++) {
			List<String> partials;
			int endRow = 0;
			if (size % 2 == 0) {
				endRow = size / 2;
				partials = partialDivisions(2);
			} else {
				endRow = size / 3;
				partials = partialDivisions(3);
			}
			Queue<String> q = new LinkedList<>();
			for (int j = 0; j < partials.size(); j++) {
				boolean match = false;
				String current = partials.get(j);
				int turn = 0;
				while (!match) {
					//System.out.println(turn + " " + current);

					if (findMatchingRule(current) != null) {
					//	System.out.println("Found match " + current + " -> " + findMatchingRule(current));
						q.add(findMatchingRule(current));
						match = true;
					} else {
						switch (turn) {
						case 0: // to 90
							current = rotateRight(current);
							turn++;
							break;
						case 1: // to 180
							current = rotateRight(current);
							turn++;
							break;
						case 2: // to 270
							current = rotateRight(current);
							turn++;
							break;
						case 3: // to 360
							current = rotateRight(current);
							turn++;
							break;
						case 4: // Original orientation flipped vertical
							current = flipVertical(current);
							turn++;
							break;
						case 5:
							current = rotateRight(current);
							turn++;
							break;
						case 6: // equivalent to flip horizontal on case 4
							current = rotateRight(current);
							turn++;
							break;
						case 7:
							current = rotateRight(current);
							turn++;
							break;
						default:
							try {
								Thread.sleep(10000);
							} catch (Exception e) {
								// TODO: handle exception
							}
							break;
						}
					}
				}
			}
			// Found all matching patterns for partials
			createBuffer(endRow, q);
			//System.out.println("Printing new Buffer");
			//printPixel(buffer);
			pixels = buffer;
			size = findSize(convertString(pixels));

			//System.out.println("Printing new Pixels");
			//printPixel(pixels);
		}

		// After iteration on bits count:
		System.out.println(countOn(convertString(pixels)));
	}

	private int countOn(String s) {
		int n = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '#') {
				n++;
			}
		}
		return n;
	}

	private void printPixel(char[][] p) {
		int n = findSize(convertString(p));
		for (int i = 0; i < n; i++) {
			String str = "";
			for (int j = 0; j < n; j++) {
				str += p[i][j];
			}
			System.out.println(str);
		}
	}

	private void createBuffer(int div, Queue<String> q) {
		System.out.println("Queue Size: " + q.size());
		int patternSize = findSize(q.peek());
		// System.out.println("Pattern Size: " + patternSize);
		// System.out.println("Divisions: " + div);
		int len = div * patternSize;
		// System.out.println("Length of Buffer: " + len);
		buffer = new char[len][len];
		for (int n = 0; n < q.size(); n++) {

			for (int i = 0; i < len; i += patternSize) {
				for (int j = 0; j < len; j += patternSize) {
					String s = q.remove();
					char[][] temp = parseString(s);
//					System.out.println("Temp");
//					printPixel(temp);
					for (int y = 0; y < patternSize; y++) {
						for (int x = 0; x < patternSize; x++) {
							// System.out.println("Adding " + temp[y][x] + " to buffer.");
							buffer[i + y][j + x] = temp[y][x];

						}
					}
				}
			}
		}

	}
}
