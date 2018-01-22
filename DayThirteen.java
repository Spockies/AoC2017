import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayThirteen {
	List<String> instruction;

	public void start(String path) {
		instruction = AdventOfCode.readFile(path);
		part1();
		part2();

	}

	private void part1() {
		// Map the inputs' layer number and respective range
		Map<String, Integer> layerRange = new HashMap<>();
		for (int i = 0; i < instruction.size(); i++) {
			String[] tokens = instruction.get(i).split(": ");
			layerRange.put(tokens[0], Integer.valueOf(tokens[1]));
		}
		int penalty = 0;
		// Traverse each layer in a loop
		// One loop = 1 picosecond = 1 layer
		for (int layer = 0; layer < 99; layer++) {
			// Get current layer
			// Check if layer is in the mapping, otherwise it has range of zero
			Integer range = layerRange.getOrDefault(String.valueOf(layer), 0);
			// check to see if the file is caught by security
			// If so resolve the penalty score
			if (range > 0) {
				if ((layer % ((range - 1) * 2)) == 0) {
					penalty += layer * range;
				}
			}
		}
		System.out.println(penalty);

	}

	private void part2() {
		// Map the inputs' layer number and respective range
		Map<String, Integer> layerRange = new HashMap<>();
		for (int i = 0; i < instruction.size(); i++) {
			String[] tokens = instruction.get(i).split(": ");
			layerRange.put(tokens[0], Integer.valueOf(tokens[1]));
		}
		int penalty;
		int delay = 0;
		int maxLayerReached = 0;
		do {
			penalty = 0;
			// Traverse each layer in a loop
			// One loop = 1 picosecond = 1 layer
			for (int layer = 0; layer < 99; layer++) {
				// Get current layer
				// Check if layer is in the mapping, otherwise it has range of zero
				Integer range = layerRange.getOrDefault(String.valueOf(layer), 0);
				// check to see if the file is caught by security
				// If so resolve the penalty score
				if (range > 0) {
					if (((layer + delay) % ((range - 1) * 2)) == 0) {
						penalty += range;
//						System.out.println("Caught at layer " + layer);
//						System.out.println("With delay of " + delay);
//						System.out.println("Penalty at " + penalty);
						delay++;
						if (layer > maxLayerReached) {
							maxLayerReached = layer;
						}
						break;
					}
				}
			}
		//	System.out.println("End with penalty at " + penalty);
		//	System.out.println("Max layer reached is " + maxLayerReached);
			if (penalty > 0) {
			//	System.out.println("Restarting with delay of " + delay);
			}
			if (penalty == 0) {
				break;
			}
		} while (penalty > 0);
		System.out.println(delay);

	}
}
