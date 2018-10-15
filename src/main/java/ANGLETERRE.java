import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import fr.srivollet.bean.BetType;
import fr.srivollet.bean.Line;
import fr.srivollet.business.Calculate;

public class ANGLETERRE {

	public static void main(String[] args) throws IOException {

		List<String> files = new ArrayList<>(2);
		files.add("/Users/srivollet/Documents/dev/workspace/results-API/src/main/resources/Premier_League_2016_2017.csv");
		files.add("/Users/srivollet/Documents/dev/workspace/results-API/src/main/resources/Premier_League_2017_2018.csv");

		for (String path : files) {

			double sommeEquipeDomcile = 0.0;
			double sommeNull = 0.0;
			double sommeEquipeExterieure = 0.0;
			double sommeCoteBasse = 0.0;
			double sommeCoteBasseMin = 0.0;
			double sommeEquipeFavorite = 0.0;
			double sommeCoteAvecEcart = 0.0;
			double mise = 10.0;

			File file = new File(path);
			List<String> lines = Files.readLines(file, Charsets.UTF_8);

			for (String l : lines) {
				String[] a = l.split(",");

				Line line = new Line(a[0], a[1], a[2], a[3], Double.valueOf(a[4].trim()).doubleValue(),
						Double.valueOf(a[5].trim()).doubleValue(), Double.valueOf(a[6].trim()).doubleValue());

				sommeEquipeDomcile += (Calculate.calculer1N2(line, mise, BetType.ONE) - mise);

				sommeNull += (Calculate.calculer1N2(line, mise, BetType.NULL) - mise);

				sommeEquipeExterieure += (Calculate.calculer1N2(line, mise, BetType.TWO) - mise);

				sommeCoteBasse += (Calculate.calculerCoteBasse(line, mise) - mise);

				sommeCoteBasseMin += (Calculate.calculerCoteBasseMinimum(line, mise, 2.0) - mise);
				
				sommeCoteAvecEcart += (Calculate.calculerCoteAvecEcart(line, mise, 3.0) - mise);

				sommeEquipeFavorite += (Calculate.calculerEquipe(line, mise, "Paris St. Germain") - mise);

			}
			System.out.println(String.format("Gain Total %s => one:%f / null:%f / two:%f / basse:%f / basseMin:%f / ecart:%f / team:%f", file.getName(), sommeEquipeDomcile,
					sommeNull, sommeEquipeExterieure, sommeCoteBasse, sommeCoteBasseMin, sommeCoteAvecEcart, sommeEquipeFavorite));
		}
	}

}
