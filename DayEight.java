import java.util.*;

import javax.print.attribute.standard.RequestingUserName;

public class DayEight {
	List<String> instructions;

	public void start(String path) {
		instructions = AdventOfCode.readFile(path);
		System.out.println(part1and2());
	}

	private int part1and2() {
		HashMap<String, Integer> registers = new HashMap<String, Integer>();

		Iterator<String> i = instructions.iterator();
		String currentInstruction = "";
		int highest = 0;
		while (i.hasNext()) {
			currentInstruction = i.next();
			String[] instructTokens = currentInstruction.split(" ");

			String register = instructTokens[0];
			String op = instructTokens[1];
			Integer modValue = Integer.valueOf(instructTokens[2]);
			String condRegister = instructTokens[4];
			String condition = instructTokens[5];
			Integer condValue = Integer.valueOf(instructTokens[6]);

			// registers.putIfAbsent(condRegister, 0);
			registers.putIfAbsent(register, 0);
			Integer condRegisterVal = registers.getOrDefault(condRegister, 0);
			Integer registerVal = registers.get(register);
			boolean modify = false;
			switch (condition) {
			case ">":
				if (condRegisterVal.intValue() > condValue.intValue()) {
					modify = true;
				}
				break;
			case "<":
				if (condRegisterVal.intValue() < condValue.intValue()) {
					modify = true;
				}
				break;
			case "==":
				if (condRegisterVal.intValue() == condValue.intValue()) {
					modify = true;
				}
				break;
			case "<=":
				if (condRegisterVal.intValue() <= condValue.intValue()) {
					modify = true;
				}
				break;
			case ">=":
				if (condRegisterVal.intValue() >= condValue.intValue()) {
					modify = true;
				}
				break;
			case "!=":
				if (condRegisterVal.intValue() != condValue.intValue()) {
					modify = true;
				}
				break;

			default:
				break;
			}

			System.out.println("Instruction: " + currentInstruction);
			int newRegisterVal = 0;
			if (modify) {
				System.out.println("Modify: Yes");
				if (op.compareTo("inc") == 0) {
					newRegisterVal = registerVal + modValue;
					registers.put(register, newRegisterVal);
				} else {
					newRegisterVal = registerVal - modValue;
					registers.put(register, newRegisterVal);
				}
				if (highest < newRegisterVal) {
					highest = newRegisterVal;
				}
			} else {
				System.out.println("Modify: No");
				newRegisterVal = registerVal;
			}
			System.out.println("Register " + register + " changed from " + registerVal + " to " + newRegisterVal);
			System.out.println("Highest value : " + highest);
		}
		return Collections.max(registers.values());
	}
}