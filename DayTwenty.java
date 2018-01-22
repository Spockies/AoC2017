import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DayTwenty {
	List<String> input;
	ArrayList<Particle> particles;

	public void start(String path) {
		input = AdventOfCode.readFile(path);
		// test();
		part2();
	}

	private void test() {
		input = new ArrayList<>();
		input.add("p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>");
		input.add("p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>");
		particles = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			particles.add(new Particle(input.get(i), i));
		}
		// Iterate a "long term"
		for (int j = 0; j < 4; j++) {
			System.out.println(particles.get(0));
			System.out.println(particles.get(1));
			for (Particle p : particles) {
				p.tick();
			}
		}
		// Sort by distance
		Collections.sort(particles);
		System.out.println(particles.get(0));
		System.out.println(particles.get(1));

	}

	private void part1() {
		// Add particles to the array
		particles = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			particles.add(new Particle(input.get(i), i));
		}
		// Iterate a "long term"
		for (int j = 0; j < 500; j++) {

			for (Particle p : particles) {
				p.tick();
			}
		}
		// Sort by distance
		Collections.sort(particles);
		System.out.println(particles.get(0).getParticleNumber());
	}

	private void part2() {
		// Add particles to the array
		particles = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			particles.add(new Particle(input.get(i), i));
		}
		// Iterate a "long term"

		for (int j = 0; j < 500; j++) {
			for (int k = 0; k < particles.size(); k++) {
				if (particles.get(k).annihilated) {
					continue;
				}
				for (int l = k + 1; l < particles.size(); l++) {
					if (particles.get(l).annihilated) {
						continue;
					}
					if (particles.get(k).collision(particles.get(l))) {
						particles.get(k).setAnnihilated(true);
						particles.get(l).setAnnihilated(true);
					}
				}
			}
			// Remove collided particles
			for (int m = 0; m < particles.size(); m++) {
				if (particles.get(m).getAnnihilated()) {
					particles.remove(m);
				}
			}
			for (Particle p : particles) {
				p.tick();
			}
		}
		// Return amount of particles left
		System.out.println(particles.size());
	}

	private class Particle implements Comparable {
		int px, py, pz; // position
		int vx, vy, vz; // velocity
		int ax, ay, az; // acceleration
		String particle;
		int num;
		boolean annihilated;

		public Particle(String s, int i) {
			particle = s;
			convertString();
			num = i;
			annihilated = false;
		}

		private void convertString() {
			int indexOfP, indexOfV, indexOfA;
			indexOfP = particle.indexOf("p");
			indexOfV = particle.indexOf("v");
			indexOfA = particle.indexOf("a");
			String position, velocity, acceleration;
			position = particle.substring(indexOfP + 3, indexOfV - 3);
			velocity = particle.substring(indexOfV + 3, indexOfA - 3);
			acceleration = particle.substring(indexOfA + 3, particle.length() - 1);
			String[] positionXYZ, velocityXYZ, accelerationXYZ;
			positionXYZ = position.split(",");
			velocityXYZ = velocity.split(",");
			accelerationXYZ = acceleration.split(",");

			px = Integer.parseInt(positionXYZ[0]);
			py = Integer.parseInt(positionXYZ[1]);
			pz = Integer.parseInt(positionXYZ[2]);
			vx = Integer.parseInt(velocityXYZ[0]);
			vy = Integer.parseInt(velocityXYZ[1]);
			vz = Integer.parseInt(velocityXYZ[2]);
			ax = Integer.parseInt(accelerationXYZ[0]);
			ay = Integer.parseInt(accelerationXYZ[1]);
			az = Integer.parseInt(accelerationXYZ[2]);

		}

		private int distance() {
			return Math.abs(px) + Math.abs(py) + Math.abs(pz);
		}

		private void tick() {
			vx += ax;
			vy += ay;
			vz += az;

			px += vx;
			py += vy;
			pz += vz;
		}

		private String particleName() {

			return particle;
		}

		public int getParticleNumber() {
			return num;
		}

		public int getPX() {
			return px;
		}

		public int getPY() {
			return py;
		}

		public int getPZ() {
			return pz;
		}

		public boolean collision(Particle p) {
			if (this.px == p.getPX())
				if (this.py == p.getPY())
					if (this.pz == p.getPZ())
						return true;
			return false;
		}

		public void setAnnihilated(boolean annihilated) {
			this.annihilated = annihilated;
		}

		public boolean getAnnihilated() {
			return annihilated;
		}

		@Override
		public int compareTo(Object o) {
			if (this.distance() < ((Particle) o).distance())
				return -1;
			if (this.distance() == ((Particle) o).distance())
				return 0;
			return 1;
		}

		@Override
		public String toString() {
			return particleName() + " : " + distance();
		}
	}
}
