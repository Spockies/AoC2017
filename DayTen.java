import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;

public class DayTen {
	List<String> instructions;
	int[] numbers;

	public DayTen() {
		numbers = new int[256];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i;
		}
	}

	public void start(String path) {
		instructions = AdventOfCode.readFile(path);
		//System.out.println(part1());
		Arrays.sort(numbers);
		System.out.println(part2());
	}

	private int part1() {
		String[] tokens = instructions.get(0).split(",");
		int currentIndex = 0;
		for (int i = 0; i < tokens.length; i++) {
			int reversalLength = Integer.parseInt(tokens[i]);
			reverse(currentIndex, currentIndex + reversalLength - 1);
			currentIndex = (currentIndex + reversalLength + i) % numbers.length;
			// System.out.println(Arrays.toString(numbers));
		}

		return numbers[0] * numbers[1];
	}

	private String part2() {
		String input = instructions.get(0);
		int[] inputASCII = new int[input.length() + 5];
		int i = 0;
		while (i < input.length()) {
			inputASCII[i] = input.charAt(i);
//			System.out.println(input.charAt(i));
//			System.out.println(inputASCII[i]);
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
		System.out.println(denseHash[j]);
		}
		String hash = "";
		for (int j = 0; j < denseHash.length; j++) {
			String hex = Integer.toHexString(denseHash[j]);
			if(hex.length()< 2) {
				hex = "0"+hex;
			}
			hash += hex;
			System.out.println(hex);
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
