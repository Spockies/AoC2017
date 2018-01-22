import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DayTwentyFour {
	List<String> componentsInput;
	List<Component> components;
	int max;
	int length;
	int maxSandL;

	public void start(String path) {
		componentsInput = AdventOfCode.readFile(path);
		init();
		part1();
	}

	private void part1() {
		buildBridge("0");
		System.out.println("Maximum bridge strength: " + max + " And length = " + length);
		System.out.println("Max bridge length strength = " + maxSandL);
	}

	private void init() {
		components = new ArrayList<>();
		for (int i = 0; i < componentsInput.size(); i++) {
			components.add(new Component(componentsInput.get(i)));
		}
		max = 0;
		length = 0;
		maxSandL = 0;
	}

	private void buildBridge(String portType) {

		List<Component> copy = new ArrayList<>();
		copy.addAll(components);
		for (int i = 0; i < copy.size(); i++) {
			Component part = copy.get(i);
			if (part.checkFreePort(portType)) {
				String[] ports = part.getBeam().split("/");
				if (ports[0].compareTo(portType) == 0) {
					part.setOpenPort(ports[1]);
				} else {
					part.setOpenPort(ports[0]);
				}

				List<Component> shorterCopy = new ArrayList<>();
				shorterCopy.addAll(copy);
				shorterCopy.remove(part);

				String bridge = part.getBeam() + "/";
				beamAttach(part.getOpenPort(), bridge, shorterCopy, 1);
			}
		}
	}

	private int calculateTotalStrength(String s) {
		// s.replaceAll("-", "/");
		String[] sp = s.split("/");
		int total = 0;
		for (int i = 0; i < sp.length; i++) {
			total += Integer.parseInt(sp[i]);
		}
		return total;
	}

	private void beamAttach(String portType, String bridge, List<Component> remainingComponents, int len) {
		if (remainingComponents.size() == 0) {
			System.out.println("No ports left!");
			int currentStrength = calculateTotalStrength(bridge);
			if (currentStrength > max) {
				max = currentStrength;
			}
			if (len >= length) {
				length = len;
				if (currentStrength >= maxSandL)
					maxSandL = currentStrength;
			}
			System.out.println(bridge + " = " + currentStrength + " *" + len);

			return;
		}
		boolean attachmentFound = false;
		for (int i = 0; i < remainingComponents.size(); i++) {
			Component part = remainingComponents.get(i);
			if (part.checkFreePort(portType)) {
				String[] ports = part.getBeam().split("/");
				if (ports[0].compareTo(portType) == 0) {
					part.setOpenPort(ports[1]);
				} else {
					part.setOpenPort(ports[0]);
				}
				attachmentFound = true;
				List<Component> shorterCopy = new ArrayList<>();
				shorterCopy.addAll(remainingComponents);
				shorterCopy.remove(part);
				String bridgePlus = bridge + part.getBeam() + "/";
				int l = len + 1;

				beamAttach(part.getOpenPort(), bridgePlus, shorterCopy, l);

				part.setOpenPort(ports[0] + "/" + ports[1]);
			}
		}
		if (!attachmentFound) {
			// System.out.println("No more matching ports");
			int currentStrength = calculateTotalStrength(bridge);
			if (currentStrength > max) {
				max = currentStrength;
			}

			if (len >= length) {
				length = len;
				if (currentStrength >= maxSandL)
					maxSandL = currentStrength;
			}
			System.out.println(bridge + " = " + currentStrength);
			// System.out.println(" Remaining ports");
			// System.out.println(remainingComponents.toString());
			return;
		}
	}

	class Component {
		private String beam;
		private int strength;
		private String openPort;
		private int portOne, portTwo;

		public Component(String s) {
			// TODO Auto-generated constructor stub
			beam = s;
			openPort = s;
			calculateStrength();
			String[] ports = beam.split("/");
			portOne = Integer.parseInt(ports[0]);
			portTwo = Integer.parseInt(ports[1]);
		}

		public boolean checkFreePort(String port) {
			String[] ports = openPort.split("/");
			for (int i = 0; i < ports.length; i++) {
				if (port.compareTo(ports[i]) == 0) {
					return true;
				}
			}
			return false;

		}

		private void calculateStrength() {
			String[] twoEnds = beam.split("/");
			strength = Integer.parseInt(twoEnds[0]) + Integer.parseInt(twoEnds[1]);
		}

		public int getStrength() {
			return strength;
		}

		public String getBeam() {
			return beam;
		}

		public String getOpenPort() {
			return openPort;
		}

		public void setOpenPort(String port) {
			openPort = port;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return beam;
		}
	}
}
