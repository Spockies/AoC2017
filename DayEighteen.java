
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//Duet
public class DayEighteen {
	private List<String> sample;

	public void start(String path) {
		sample = AdventOfCode.readFile(path);
		Duet duet1 = new Duet(sample, BigInteger.valueOf(0));
		//duet1.part1();

		part2();

	}

	public void part2() {
		// Initialize
		Duet duetA = new Duet(sample, BigInteger.valueOf(0L));
		Duet duetB = new Duet(sample, BigInteger.valueOf(1L));
		// Pair
		duetA.setPartner(duetB);
		duetB.setPartner(duetA);
		int send = 0;
		while (!duetA.getWaiting() || !duetB.getWaiting()) {

			if (!duetA.getWaiting()) {
				duetA.next();
			}
			if (!duetB.getWaiting()) {
				send = duetB.next();
			}

		}
		System.out.println(send);
	}

}
