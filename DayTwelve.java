import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DayTwelve {
	List<String> instruction;

	public void start(String path) {
		instruction = AdventOfCode.readFile(path);
		part1();
		part2();

	}

	private void part1() {
		// Record all ID and their pipes in "lookup book"
		Map<String, String[]> lookUp = new HashMap<>();
		for (int i = 0; i < instruction.size(); i++) {
			String[] ID = instruction.get(i).split(" <-> ");
			String[] children = ID[1].split(", ");
			// System.out.println(ID[0]);
			// System.out.println(ID[1]);
			// System.out.println(children[0]);
			lookUp.put(ID[0], children);
		}
		// Start at ID 0 and add each connecting IDs to the queue
		Queue<String> queue = new LinkedList<>();
		Set<String> group = new HashSet<>();
		String currentID = "0";
		String[] children;
		int moreChildren = 0;
		do {
			System.out.println(currentID);
			group.add(currentID);
			moreChildren--;
			children = lookUp.get(currentID);
			for (int i = 0; i < children.length; i++) {
				if (!group.contains(children[i])) {
					System.out.println(children[i] + " is child of " + currentID);
					queue.add(children[i]);
					moreChildren++;
				}
			}
			if (!queue.isEmpty()) {
				currentID = queue.remove();

			}
			// }
		} while (moreChildren > -1);
		// Start Loop: Remove a ID from queue and look up its pipe connections
		// Add new found pipe connections to queue
		// Cross exam with group list(Set?) of to current ID
		// Print out number of elements in group set
		System.out.println("# of IDs connected to 0: " + group.size());
	}

	private void part2() {
		// Record all ID and their pipes in "lookup book"
		Map<String, String[]> lookUp = new HashMap<>();
		List<String> checkList = new ArrayList<>();
		for (int i = 0; i < instruction.size(); i++) {
			String[] ID = instruction.get(i).split(" <-> ");
			String[] children = ID[1].split(", ");
			// System.out.println(ID[0]);
			// System.out.println(ID[1]);
			// System.out.println(children[0]);
			lookUp.put(ID[0], children);
			checkList.add(ID[0]);
		}
		// Check each house for their group set
		List<Set<String>> groupList = new ArrayList<>();
		boolean skip;
		for (String i : checkList) {
			skip = false;
			for (int j = 0; j < groupList.size(); j++) {
				if (groupList.get(j).contains(i)) {
					// If true, current checklist ID is contained in aforementioned group
					skip = true;
					break;
				}
			}
			if (skip) {
				continue;
			}
			Queue<String> queue = new LinkedList<>();
			Set<String> group = new HashSet<>();
			String currentID = i;
			String[] children;
			int moreChildren = 0;
			do {
				// System.out.println(currentID);
				group.add(currentID);
				moreChildren--;
				children = lookUp.get(currentID);
				for (int k = 0; k < children.length; k++) {
					if (!group.contains(children[k])) {
						// System.out.println(children[k] + " is child of " + currentID);
						queue.add(children[k]);
						moreChildren++;
					}
				}
				if (!queue.isEmpty()) {
					currentID = queue.remove();

				}
			} while (moreChildren > -1);
			System.out.println("Adding group of size : " + group.size());

			groupList.add(group);
		}
		System.out.println("# of groups interconnected: " + groupList.size());

	}
}
