
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

public class DayFourteen {
	List<String> instruction;
	List<String> diskRow;
	int[] numbers;
	int[][] grid;
	int gridSize = 128;

	public DayFourteen() {
		numbers = new int[256];
		grid = new int[gridSize][gridSize];
	}

	public void start(String path) {
		instruction = AdventOfCode.readFile(path);
		// Generate hash input of each row
		diskRow = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			diskRow.add(instruction.get(0) + "-" + i);
		}
		part1();
		part2();
	}

	private void part1() {
		int used = 0;
		int row = 0;
		for (String i : diskRow) {
			// In each row, create respective knot hash
			// System.out.println(i);
			String kHash = knotHash(i);
			// System.out.println(kHash);
			// Convert each hexadecimal to binary
			// BigInteger hash = new BigInteger(kHash, 16);
			// System.out.println(hash);
			// String bin = new BigInteger(kHash, 16).toString(2);
			// System.out.println(bin);
			StringBuilder sBuilder = new StringBuilder();
			for (int j = 0; j < kHash.length(); j++) {
				switch (kHash.charAt(j)) {
				case '0':
					sBuilder.append("0000");
					break;
				case '1':
					sBuilder.append("0001");
					break;
				case '2':
					sBuilder.append("0010");
					break;
				case '3':
					sBuilder.append("0011");
					break;
				case '4':
					sBuilder.append("0100");
					break;
				case '5':
					sBuilder.append("0101");
					break;
				case '6':
					sBuilder.append("0110");
					break;
				case '7':
					sBuilder.append("0111");
					break;
				case '8':
					sBuilder.append("1000");
					break;
				case '9':
					sBuilder.append("1001");
					break;
				case 'a':
					sBuilder.append("1010");
					break;
				case 'b':
					sBuilder.append("1011");
					break;
				case 'c':
					sBuilder.append("1100");
					break;
				case 'd':
					sBuilder.append("1101");
					break;
				case 'e':
					sBuilder.append("1110");
					break;
				case 'f':
					sBuilder.append("1111");
					break;
				}
			}
			// System.out.println(sBuilder);
			int counter = 0;
			// Count up each occurence of 1s in binary hash
			// for (int k = 0; k < bin.length(); k++) {
			// if (bin.charAt(k) == '1') {
			// counter++;
			// used++;
			// }
			// grid[row][k] = Integer.parseInt(String.valueOf(bin.charAt(k)));
			//
			// }
			for (int k = 0; k < sBuilder.length(); k++) {
				if (sBuilder.charAt(k) == '1') {
					counter++;
					used++;
				}
				grid[row][k] = Integer.parseInt(String.valueOf(sBuilder.charAt(k)));

			}
			// for (int k = 0; k < sBuilder.length(); k++) {
			// if (sBuilder.charAt(k) == '1') {
			// counter++;
			// used++;
			// }
			// }
			// System.out.println(counter);
			row++;
		}

		// Print out total occurence of 1s
		System.out.println(used);
	}

	private void part2() {
		int region = 2;
		// Display pre-fill grid
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
		System.out.println("");
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				if (grid[y][x] == 1) {
					// System.out.println("Region : " + region);
					region += floodFill(y, x, region);

				}
			}
		}
		// Display post-fill grid
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
		System.out.println(region - 2);

	}

	private int floodFill(int yLoc, int xLoc, int change) {

		floodFill(yLoc, xLoc, 1, change);

		return 1;
	}

	private void floodFill(int yLoc, int xLoc, int target, int change) {
		if (grid[yLoc][xLoc] != target || grid[yLoc][xLoc] == change) {
			return;
		}
		if (grid[yLoc][xLoc] == target) {
			grid[yLoc][xLoc] = change;
		}
		// System.out.println("Coord x,y : " + xLoc + "," + yLoc);
		// Left
		if (0 < xLoc) {
			floodFill(yLoc, xLoc - 1, target, change);
		}
		// Up
		if (0 < yLoc) {
			floodFill(yLoc - 1, xLoc, target, change);
		}
		// Down
		if (yLoc < gridSize - 1) {
			floodFill(yLoc + 1, xLoc, target, change);
		}
		// Right
		if (xLoc < gridSize - 1) {
			floodFill(yLoc, xLoc + 1, target, change);
		}
	}

	private String knotHash(String input) {
		for (int m = 0; m < numbers.length; m++) {
			numbers[m] = m;
		}
		int[] inputASCII = new int[input.length() + 5];
		int i = 0;
		while (i < input.length()) {
			inputASCII[i] = input.charAt(i);
			// System.out.println(input.charAt(i));
			// System.out.println(inputASCII[i]);
			i++;
		}
		inputASCII[i++] = 17;
		inputASCII[i++] = 31;
		inputASCII[i++] = 73;
		inputASCII[i++] = 47;
		inputASCII[i] = 23;

		int round = 64;
		int counter = 0;
		int skip = 0;
		int currentIndex = 0;
		while (counter < round) {
			for (int j = 0; j < inputASCII.length; j++) {
				int reversalLength = inputASCII[j];
				reverse(currentIndex, currentIndex + reversalLength - 1);
				currentIndex = (currentIndex + reversalLength + skip) % numbers.length;
				// System.out.println(Arrays.toString(numbers));
				skip++;
			}
			counter++;
		}

		// XOR Sparse -> Dense hash
		int[] denseHash = new int[16];
		for (int j = 0; j < denseHash.length; j++) {
			denseHash[j] = numbers[j * 16] ^ numbers[j * 16 + 1] ^ numbers[j * 16 + 2] ^ numbers[j * 16 + 3]
					^ numbers[j * 16 + 4] ^ numbers[j * 16 + 5] ^ numbers[j * 16 + 6] ^ numbers[j * 16 + 7]
					^ numbers[j * 16 + 8] ^ numbers[j * 16 + 9] ^ numbers[j * 16 + 10] ^ numbers[j * 16 + 11]
					^ numbers[j * 16 + 12] ^ numbers[j * 16 + 13] ^ numbers[j * 16 + 14] ^ numbers[j * 16 + 15];
			// System.out.println(denseHash[j]);
		}
		String hash = "";
		for (int j = 0; j < denseHash.length; j++) {
			String hex = Integer.toHexString(denseHash[j]);
			if (hex.length() < 2) {
				hex = "0" + hex;
			}
			hash += hex;
			// System.out.println(hex);
		}
		return hash;
	}

	private void reverse(int beginIndex, int endIndex) {
		while (beginIndex < endIndex) {
			int newEndIndex = endIndex % numbers.length;
			int newBeginIndex = beginIndex % numbers.length;
			// if (endIndex > numbers.length) {
			// newEndIndex = endIndex % numbers.length;
			//
			// }
			// if (beginIndex > numbers.length) {
			// newBeginIndex = beginIndex % numbers.length;
			// }
			swap(newBeginIndex, newEndIndex);

			beginIndex++;
			endIndex--;
		}
	}

	private void swap(int fromIndex, int toIndex) {
		int temp = numbers[fromIndex];
		numbers[fromIndex] = numbers[toIndex];
		numbers[toIndex] = temp;
	}
}
