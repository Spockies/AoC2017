import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Duet {
	private Map<String, BigInteger> registers;
	private BigInteger lastSound;
	private Deque<BigInteger> comms;
	private int sendCounter;
	private boolean waiting;
	private boolean stopProgram;
	private List<String> input;
	private Duet partner;
	private int programID;
	private int currentPointer;

	public Duet(List<String> inputList, BigInteger programNumber) {
		stopProgram = false;
		waiting = false;
		currentPointer = 0;
		comms = new ArrayDeque<>();
		input = inputList;
		registers = new HashMap<String, BigInteger>();
		lastSound = BigInteger.valueOf(0);
		registers.put("p", programNumber);
		sendCounter = 0;
		programID = programNumber.intValue();
		for (char i = 'a'; i <= 'z'; i++) {
			registers.put(String.valueOf(i), BigInteger.valueOf(0L));
		}
		registers.put("p", programNumber);
	}

	public boolean getWaiting() {
		return waiting;
	}

	public int getSendCounter() {
		return sendCounter;
	}

	public void stopProgram(boolean value) {
		stopProgram = value;
	}

	public boolean getStopProgram() {
		return stopProgram;
	}

	public void setPartner(Duet program) {

		this.partner = program;
	}

	public void part1() {
		// Traverse list of instructions
		// Depending on instruction do as follows
		int currentInstruction = 0;
		boolean rec = false, jmp;
		while (!rec) {
			jmp = false;
			System.out.println("Instruction: " + input.get(currentInstruction));
			String[] tokenize = input.get(currentInstruction).split(" ");
			// registers.putIfAbsent(tokenize[1], BigInteger.valueOf(0));
			switch (tokenize[0]) {

			case "set":
				// // Check if string is a value or register name
				// if (!isANumber(tokenize[2])) {
				// // Check if register is recorded, otherwise set to 0;
				// if (!registers.containsKey(tokenize[2])) {
				// registers.put(tokenize[2], BigInteger.valueOf(0));
				// }
				// }
				set(tokenize[1], tokenize[2]);
				break;

			case "add":
				// // Check if string is a value or register name
				// if (!isANumber(tokenize[2])) {
				// // Check if register is recorded, otherwise set to 0;
				// if (!registers.containsKey(tokenize[2])) {
				// registers.put(tokenize[2], BigInteger.valueOf(0));
				// }
				// }
				add(tokenize[1], tokenize[2]);
				break;

			case "mod":
				// // Check if string is a value or register name
				// if (!isANumber(tokenize[2])) {
				// // Check if register is recorded, otherwise set to 0;
				// if (!registers.containsKey(tokenize[2])) {
				// registers.put(tokenize[2], BigInteger.valueOf(0));
				// }
				// }
				mod(tokenize[1], tokenize[2]);
				break;

			case "mul":
				// // Check if string is a value or register name
				// if (!isANumber(tokenize[2])) {
				// // Check if register is recorded, otherwise set to 0;
				// if (!registers.containsKey(tokenize[2])) {
				// registers.put(tokenize[2], BigInteger.valueOf(0));
				// }
				// }
				multiply(tokenize[1], tokenize[2]);
				break;

			case "snd":
				sound(tokenize[1]);
				break;
			case "rcv":
				rec = recover(tokenize[1]);
				// First recover
				break;

			case "jgz":
				// // Check if string is a value or register name
				// if (!isANumber(tokenize[2])) {
				// // Check if register is recorded, otherwise set to 0;
				// if (!registers.containsKey(tokenize[2])) {
				// registers.put(tokenize[2], BigInteger.valueOf(0));
				// }
				// }
				currentInstruction += jump(tokenize[1], tokenize[2]);
				jmp = true;
				break;
			default:
				break;
			}

			// Break when first RCV is used
			// otherwise increase counter
			if (!jmp) {
				currentInstruction++;
			}
		}

		System.out.println(lastSound);
	}

	private void set(String register, String value) {
		// Set(X,Y): Check if register X exist in register list
		// Check if Y is integer or register
		if (registers.containsKey(value)) {
			// Value is a register so find its value and add it to current register
			registers.put(register, registers.get(value));
		} else {
			// else, set register to Y
			// Value is a number
			registers.put(register, BigInteger.valueOf(Long.parseLong(value)));
		}
		System.out.println("Register " + register + " = " + registers.get(register));

	}

	private void add(String register, String value) {
		// Add(X,Y):Add to register X by amount Y
		// Determine value of Y before adding to X
		BigInteger regVal = registers.get(register);
		if (registers.containsKey(value)) {
			// Value is a register and found
			registers.put(register, registers.get(value).add(regVal));
		} else {
			// Value is a number
			registers.put(register, BigInteger.valueOf(Long.parseLong(value)).add(regVal));
		}
		System.out.println("Register " + register + " + " + value + " = " + registers.get(register));

	}

	private void multiply(String register, String value) {

		// Mul(X,Y): Multiply to register X by amount Y
		// Determine value of Y before multiplying to X
		BigInteger regVal = registers.get(register);
		if (registers.containsKey(value)) {
			// Value is a register and found
			registers.put(register, registers.get(value).multiply(regVal));
		} else {
			// Value is a number
			registers.put(register, BigInteger.valueOf(Long.parseLong(value)).multiply(regVal));
		}

		System.out.println("Register " + register + " * " + value + " = " + registers.get(register));

	}

	private void mod(String register, String value) {

		// Mod(X,Y): Modulo to X by Y
		// Determine value of Y before mod X
		BigInteger regVal = registers.get(register);
		if (registers.containsKey(value)) {
			// Value is a register and found
			registers.put(register, regVal.mod(registers.get(value)));
		} else {
			// Value is a number
			registers.put(register, regVal.mod(BigInteger.valueOf(Long.parseLong(value))));
		}

		System.out.println("Register " + register + " % " + value + " = " + registers.get(register));

	}

	private int jump(String register, String value) {
		int regVal;
		// JGZ(X,Y): if X less than or equal to 0, return 0 for no skip
		if (registers.containsKey(register)) {
			regVal = registers.get(register).intValue();
		} else {
			regVal = Integer.parseInt(register);
		}
		if (regVal <= 0) {
			System.out.println("Jump by 1");
			return 1;
		} else {
			// else, return jump amount of Y
			// Check if Y is a register
			if (registers.containsKey(value)) {
				System.out.println("Jump by " + registers.get(value).intValue());
				return registers.get(value).intValue();
			}
			// Otherwise return value
			System.out.println("Jump by " + Integer.parseInt(value));
			return Integer.parseInt(value);
		}
	}

	private boolean recover(String register) {
		// RCV(X): if X is > 0, return true and reciever recovers lastSound
		// else return false
		BigInteger val = registers.get(register);
		if (val != null && val.longValue() != 0) {
			return true;
		}
		return false;
	}

	private void sound(String register) {
		// SND (X): Sound out value of X
		// Sets lastSound variable to X
		BigInteger val = registers.get(register);
		if (val != null) {
			lastSound = val;
		}
	}

	private void send(String register) {
		this.sendCounter++;
		if (!isANumber(register)) {
			BigInteger val = registers.get(register);
			if (val != null) {
				partner.queue(val);
			}
		} else {
			partner.queue(BigInteger.valueOf(Long.parseLong(register)));
		}

	}

	private void recieve(String register) {
		registers.put(register, comms.remove());

	}

	public void queue(BigInteger num) {
		if (getWaiting()) {
			setWaiting(false);
		}
		comms.addLast(num);
	}

	private void setWaiting(boolean set) {
		waiting = set;
	}

	private boolean isANumber(String s) {
		try {
			double d = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public int next() {

		// Traverse list of instructions
		// Depending on instruction do as follows

		System.out.println("Instruction: " + input.get(currentPointer));
		String[] tokenize = input.get(currentPointer).split(" ");
		// registers.putIfAbsent(tokenize[1], BigInteger.valueOf(0));
		switch (tokenize[0]) {

		case "set":
			// // Check if string is a value or register name
			// if (!isANumber(tokenize[2])) {
			// // Check if register is recorded, otherwise set to 0;
			// if (!registers.containsKey(tokenize[2])) {
			// registers.put(tokenize[2], BigInteger.valueOf(0));
			// }
			// }
			set(tokenize[1], tokenize[2]);
			currentPointer++;
			break;

		case "add":
			// // Check if string is a value or register name
			// if (!isANumber(tokenize[2])) {
			// // Check if register is recorded, otherwise set to 0;
			// if (!registers.containsKey(tokenize[2])) {
			// registers.put(tokenize[2], BigInteger.valueOf(0));
			// }
			// }
			add(tokenize[1], tokenize[2]);
			currentPointer++;
			break;

		case "mod":
			// // Check if string is a value or register name
			// if (!isANumber(tokenize[2])) {
			// // Check if register is recorded, otherwise set to 0;
			// if (!registers.containsKey(tokenize[2])) {
			// registers.put(tokenize[2], BigInteger.valueOf(0));
			// }
			// }
			mod(tokenize[1], tokenize[2]);
			currentPointer++;
			break;

		case "mul":
			multiply(tokenize[1], tokenize[2]);
			currentPointer++;
			break;

		case "snd":
			send(tokenize[1]);
			currentPointer++;
			break;
		case "rcv":
			if (comms.isEmpty()) {
				waiting = true;
				return getSendCounter();
			} else {
				recieve(tokenize[1]);
				currentPointer++;
			}
			break;

		case "jgz":
			// Check if string is a value or register name
			if (!isANumber(tokenize[2])) {
				// Check if register is recorded, otherwise set to 0;
				if (!registers.containsKey(tokenize[2])) {
					registers.put(tokenize[2], BigInteger.valueOf(0));
				}
			}
			currentPointer += jump(tokenize[1], tokenize[2]);
			break;
		default:
			break;
		}

		if (currentPointer > input.size()) {
			this.stopProgram = true;
			this.waiting = true;
		}

		return 0;
	}
}
