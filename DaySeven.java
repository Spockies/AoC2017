import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.Line;

public class DaySeven {
	Set<String> roots;
	Set<String> childs;
	List<String> instructions;
	ArrayList<Disc> discs;

	public DaySeven() {
		// TODO Auto-generated constructor stub
		roots = new HashSet<>();
		childs = new HashSet<>();
		discs = new ArrayList<>();
	}

	public void start(String path) {
		instructions = AdventOfCode.readFile(path);
		part1();
		part2();
	}

	private void part1() {
		// Add inputs
		for (int i = 0; i < instructions.size(); i++) {
			System.out.println("Adding");
			parse(instructions.get(i));
		}
		// Remove children from roots
		for (String child : childs) {
			if (roots.contains(child)) {
				roots.remove(child);
			}
		}
		// Only one root remain
		System.out.println(roots.toString());
	}

	private void part2() {
		for (String i : instructions) {
			parseDiscs(i);
		}
		parseChildrenDisc();
		
		ArrayList<Disc> mismatchedWeight = new ArrayList<>();
		for (Disc disc : discs) {
			if (disc.getChildren() != null) {
				ArrayList<Disc> children = disc.getChildren();
				int weight = children.get(0).totalWeight();
				for (int i = 1; i < children.size(); i++) {
					int newweight = children.get(i).totalWeight();
					if (newweight != weight) {
						mismatchedWeight.add(disc);
						break;
					}
				}
			}
		}
		for (int i = 0; i < mismatchedWeight.size(); i++) {
			Boolean hasChildren = false;
			for (int j = 0; j < mismatchedWeight.size(); j++) {
				if (i != j) {
					if (mismatchedWeight.get(i).hasChild(mismatchedWeight.get(j))) {
						hasChildren = true;
					}
				}
			}
			if (hasChildren == false) {
				Disc offBalance = mismatchedWeight.get(i);
				for (Disc disc : offBalance.getChildren()) {
					System.out.println(disc.totalWeight() + ": " + disc);
				}
			}
		}
	}

	// Splits up parent node and child nodes
	private void parse(String part) {

		String[] token;
		String[] parent;
		if (part.contains("->")) {
			token = part.split(" -> ");
			parent = token[0].split(" ");

			String[] children = token[1].split(", ");
			roots.add(parent[0]);

			for (int i = 0; i < children.length; i++) {
				childs.add(children[i]);
			}

		} else {
			parent = token = part.split(" ");
			roots.add(parent[0]);

		}

		// weights.put(parent[0], Integer.parseInt(heavy));

	}

	private void parseDiscs(String input) {
		String name = input.substring(0, input.indexOf(" "));
		String weight = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
		Disc disc = new Disc();
		disc.setName(name);
		disc.setWeight(new Integer(weight));
		discs.add(disc);
	}

	private void parseChildrenDisc() {
		for (String s : instructions) {
			if (s.contains("->")) {
				String name = s.substring(0, s.indexOf(" "));
				Disc parent = null;
				for (int i = 0; i < discs.size(); i++) {
					if (discs.get(i).getName().equals(name)) {
						parent = discs.get(i);
					}
				}
				String supportingDiscs = s.substring(s.indexOf("->") + 3);
				String[] supportingDiscsArray = supportingDiscs.split(", ");
				ArrayList<Disc> children = new ArrayList<>();
				for (String supportingDisc : supportingDiscsArray) {
					for (int i = 0; i < discs.size(); i++) {
						if (discs.get(i).getName().equals(supportingDisc)) {
							Disc child = discs.get(i);
							children.add(child);
						}
					}
				}
				parent.setChildren(children);
			}
		}
	}

	private static class Disc {
		private String name = "";
		private int weight = 0;
		private ArrayList<Disc> children;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the weight
		 */
		public int getWeight() {
			return weight;
		}

		/**
		 * @param weight
		 *            the weight to set
		 */
		public void setWeight(int weight) {
			this.weight = weight;
		}

		/**
		 * @return the children
		 */
		public ArrayList<Disc> getChildren() {
			return children;
		}

		/**
		 * @param children
		 *            the children to set
		 */
		public void setChildren(ArrayList<Disc> children) {
			this.children = children;
		}

		public int totalWeight() {
			int weight = this.getWeight();
			if (this.getChildren() != null) {
				for (Disc child : this.getChildren()) {
					weight += child.totalWeight();
				}
			}
			return weight;
		}

		public Boolean hasChild(Disc disc) {
			Boolean hasChild = false;
			if (this.getChildren() != null) {
				for (Disc child : this.getChildren()) {
					if (disc.getName().equals(child.getName())) {
						hasChild = true;
					}
				}
			}
			return hasChild;
		}

		@Override
		public String toString() {
			String returnString = Integer.toString(this.getWeight());
			if (this.getChildren() != null) {
				for (Disc child : this.getChildren()) {
					returnString += " + ";
					returnString += child.toString();
				}
			}
			return returnString;
		}

	}
}