import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import fr.srivollet.bean.Line;
import fr.srivollet.business.Calculate;

public class FRANCE {

	public static void main(String[] args) throws IOException {
		
		List<String> teams = new ArrayList<>();
		teams.add("Paris St. Germain");

		List<String> files = new ArrayList<>(2);
		files.add("/Users/srivollet/Documents/dev/workspace/results-API/src/main/resources/Ligue1_2015_2016.csv");
		files.add("/Users/srivollet/Documents/dev/workspace/results-API/src/main/resources/Ligue1_2016_2017.csv");
		files.add("/Users/srivollet/Documents/dev/workspace/results-API/src/main/resources/Ligue1_2017_2018.csv");

		for (String path : files) {

			double sommeEquipeFavorite = 0.0;
			double sommeDoubleChance = 0.0;
			double mise = 5.0;

			File file = new File(path);
			List<String> lines = Files.readLines(file, Charsets.UTF_8);

			for (String l : lines) {
				String[] a = l.split(",");

				Line line = new Line(a[0], a[1], a[2], a[3], Double.valueOf(a[4].trim()).doubleValue(),
						Double.valueOf(a[5].trim()).doubleValue(), Double.valueOf(a[6].trim()).doubleValue());

				sommeEquipeFavorite += (Calculate.calculerEquipe(line, mise, "Paris St. Germain") - mise);
				
				sommeDoubleChance += (Calculate.calculerDoubleChance(line, mise, 1.1, 4.0, teams) - mise);
				
			}
			System.out.println(String.format("Gain Total %s => team:%f / double:%f", file.getName(), sommeEquipeFavorite, sommeDoubleChance));
		}
	}

}
