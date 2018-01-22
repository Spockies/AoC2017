import java.util.*;

public class DaySix {
	Set<String> hashMap = new HashSet<String>();

	public void start(String path) {
		List<String> input = AdventOfCode.readFile(path);

		part1(input.get(0));
		part2(input.get(0));
	}

	public void part1(String input) {
		System.out.println("Part 1:");
		int cycle = 0;
		Integer[] memoryBank = new Integer[16];
		// Trim whitespace and tokenized
		String[] tokens = input.replaceAll("\\s+", " ").split(" ");
		for (int i = 0; i < tokens.length; i++) {
			memoryBank[i] = Integer.valueOf(tokens[i]);
		}
		System.out.println(bankToString(memoryBank));
		Set<String> set = new HashSet<String>();

		while (!checkBalance(memoryBank)) {
			memoryBank = reallocate(memoryBank);
			cycle++;

			// System.out.println(bankToString(memoryBank));
			if (set.add(bankToString(memoryBank)) == false) {
				if (set.contains(bankToString(memoryBank))) {
					System.out.println(bankToString(memoryBank));
				}
				System.out.println(cycle);
				return;
			}
		}
		System.out.println(cycle);

	}

	public void part2(String input) {
		System.out.println("Part 2:");
		int cycle = 0;
		Integer[] memoryBank = new Integer[16];
		// Trim whitespace and tokenized
		String[] tokens = input.replaceAll("\\s+", " ").split(" ");
		for (int i = 0; i < tokens.length; i++) {
			memoryBank[i] = Integer.valueOf(tokens[i]);
		}
		System.out.println(bankToString(memoryBank));
		Set<String> set = new HashSet<String>();

		while (!checkBalance(memoryBank)) {
			memoryBank = reallocate(memoryBank);
			cycle++;

			// System.out.println(bankToString(memoryBank));
			if (set.add(bankToString(memoryBank)) == false) {
				if (set.contains(bankToString(memoryBank))) {
					System.out.println(bankToString(memoryBank));
				}
				System.out.println(cycle);
				break;
			}
		}
		set.clear();
		set.add(bankToString(memoryBank));
		cycle = 0;
		while (!checkBalance(memoryBank)) {
			memoryBank = reallocate(memoryBank);
			cycle++;

			// System.out.println(bankToString(memoryBank));
			if (set.add(bankToString(memoryBank)) == false) {
				if (set.contains(bankToString(memoryBank))) {
					System.out.println(bankToString(memoryBank));
				}
				System.out.println(cycle);
				break;
			}
		}
		System.out.println(cycle);

	}

	private String bankToString(Integer[] bank) {
		String concat = "";
		for (int i = 0; i < bank.length; i++) {
			if (bank[i] < 10) {
				concat += "0" + bank[i];
			} else {

				concat += bank[i];
			}
		}
		return concat;
	}

	private String debugBankToString(Integer[] bank) {
		String concat = "";
		for (int i = 0; i < bank.length; i++) {
			concat += " " + bank[i];
		}
		return concat;
	}

	private boolean checkBalance(Integer[] blocks) {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[0] == blocks[i]) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	private Integer[] reallocate(Integer[] bank) {
		List<Integer> ints = Arrays.asList(bank);
		// System.out.println("Max: "+Collections.max(ints));
		int index = ints.indexOf(Collections.max(ints));

		int block = bank[index];
		// System.out.println("Block count = "+block);
		bank[index] = 0;
		for (int i = index + 1; block != 0; i++) {

			// System.out.println(debugBankToString(bank));
			bank[i % 16]++;
			block--;
		}
		// System.out.println("Done reallocating");
		return bank;
	}

}
