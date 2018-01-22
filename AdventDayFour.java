import java.util.*;

public class AdventDayFour {
	private List<String> passphraseBank;

	public void start(String path) {
		passphraseBank = AdventOfCode.readFile(path);
		System.out.println(part1());
		System.out.println(part2());
	}

	private int part1() {
		int invalid = 0;
		for (int i = 0; i < passphraseBank.size(); i++) {
			String[] tokens = passphraseBank.get(i).split(" ");
			boolean matched = false;
			for (int j = 0; j < tokens.length; j++) {
				for (int k = j + 1; k < tokens.length; k++) {
					if (tokens[j].compareTo(tokens[k]) == 0) {
						invalid++;
						System.out.println("Matched: " + tokens[j] + " and " + tokens[k]);
						System.out.println("Invalid counter: " + invalid);
						matched = true;
						break;
					}
				}
				if (matched) {
					break;
				}
			}
		}
		System.out.println("Max passphrases : " + passphraseBank.size());
		System.out.println("# of invalids : " + invalid);
		return passphraseBank.size() - invalid;
	}

	private int part2() {
		int invalid = 0;
		for (int i = 0; i < passphraseBank.size(); i++) {
			String[] tokens = passphraseBank.get(i).split(" ");
			boolean anagramFound = false;
			for (int j = 0; j < tokens.length; j++) {
				for (int k = j + 1; k < tokens.length; k++) {
					if (isAnagram(tokens[j], tokens[k])) {
						invalid++;
						anagramFound = true;
						break;
					}

				}
				if (anagramFound) {
					break;
				}
			}
		}
		return passphraseBank.size() - invalid;
	}

	private boolean isAnagram(String s, String t) {
		char[] a = s.toCharArray();
		char[] b = t.toCharArray();
		Arrays.sort(a);
		Arrays.sort(b);
		return Arrays.equals(a, b);
	}
}
