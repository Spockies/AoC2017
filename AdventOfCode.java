import java.io.*;
import java.util.*;

public class AdventOfCode {

	public static void main(String args[]) {
		System.out.println("Hello World!");
		// DayThree day = new DayThree();
		// day.start("325489");
		// day.start("25");
		// AdventDayFive dayFive = new AdventDayFive();
		// AdventDayFour dayFour = new AdventDayFour();
		// DaySix daySix = new DaySix();
		// dayFive.start("day5.txt");
		// dayFour.start("day4.txt");
		// daySix.start("day6.txt");
		// DaySeven day = new DaySeven();
		// day.start("input\\day7.txt");
		// DayEight dayEight = new DayEight();
		// dayEight.start("input\\day8.txt");
		// dayEight.start("test.txt");
		// DayNine day9 = new DayNine();
		// day9.start("input\\test.txt");
		// // day9.start("input\\day9.txt");
		// DayTen day10 = new DayTen();
		// day10.start("input\\day10.txt");
		// day10.start("input\\test.txt");
		// DayEleven day = new DayEleven();
		// day.start("input\\day11.txt");
		// day.start("input\\test.txt");
		// DayTwelve day = new DayTwelve();
		// day.start("input\\day12.txt");
		// DayThirteen day = new DayThirteen();
		// day.start("input\\day13.txt");
		// DayFourteen day = new DayFourteen();
		//// day.start("input\\day14.txt");
		// DayFifteen day =new DayFifteen();
		// day.start("input\\day15.txt");
		// DaySixteen day = new DaySixteen();
		// day.start("input\\day16.txt");
		// DaySeventeen day = new DaySeventeen();
		// day.start();
		// DayEighteen day = new DayEighteen();
		// day.start("input\\test.txt");
		// day.start("input\\day18.txt");
		// DayNineteen day = new DayNineteen();
		// day.start("input\\day19.txt");
		// DayTwenty day = new DayTwenty();
		// day.start("input\\day20.txt");
		// DayTwentyOne day = new DayTwentyOne();
		// day.start("input\\day21.txt");
		// DayTwentyTwo day = new DayTwentyTwo();
		// day.start("input\\day22.txt");
		// DayTwentyThree day = new DayTwentyThree();
		// day.start("input\\day23.txt");
//		DayTwentyFour day = new DayTwentyFour();
	//	day.start("input\\test.txt");
//		day.start("input\\day24.txt");
		 DayTwentyFive day = new DayTwentyFive();
		 day.start();

	}

	public static List<String> readFile(String stringPath) {
		List<String> records = new ArrayList<String>();
		File fileName = new File(stringPath);
		try {

			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occured trying to read '%s'.", stringPath);
			e.printStackTrace();
			return null;
		}
	}
}
