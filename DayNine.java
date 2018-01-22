import java.util.List;

public class DayNine {

	public void start(String path) {
		List<String> input = AdventOfCode.readFile(path);
		for (String i : input) {
			System.out.println("Score of " + i + " :");
			parse(i);
		}
	}

	private void parse(String s) {
		int place = 0;
		int level = 0;
		int score = 0;
		int trashCount = 0;
		boolean isTrash = false;
		int group = 0;
		while (place < s.length()) {
			// Check for ignore
			if (s.charAt(place) == '!') {
				place += 2;
			} else {
				// Check for trash sequence
				if (isTrash) {
					if (s.charAt(place) == '>') {
						isTrash = false;
					} else {
						trashCount++;
					}
					place++;
					continue;
				} else {
					if (s.charAt(place) == '<') {
						place++;
						isTrash = true;
						continue;
					}
					if (s.charAt(place) == ',') {
						// if (group > 1) {
						// score += level;
						// }
						place++;
					}
					// Check for group start
					if (s.charAt(place) == '{') {
						group++;
						place++;
						if (group > 0) {
							level++;
						}
					}
					if (s.charAt(place) == '}') {
						score += level;
						group--;
						level--;
						place++;
					}
				}
			}
		}
		System.out.println("Total score : "+score); 
		System.out.println("Trash count : "+trashCount); 
	}
}
