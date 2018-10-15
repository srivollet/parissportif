package fr.srivollet.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.srivollet.bean.BetType;
import fr.srivollet.bean.Line;

public class CalculateTest {
	
	private List<String> teams = new ArrayList<>();
	
	@Before
	public void before() {
		teams.add("PSG");
	}
	
	@Test
	public void getGain() {
		Line ONE = new Line("Bordeaux", "Lyon", "1 - 0", "10/10/2018", 1.2, 2.0, 3.0);
		Assert.assertEquals(Calculate.calculer1N2(ONE, 5.0, BetType.ONE), 6.0, 0);
		
		Line NULL = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.2, 2.0, 3.0);
		Assert.assertEquals(Calculate.calculer1N2(NULL, 5.0, BetType.NULL), 10.0, 0);
		
		Line TWO = new Line("Bordeaux", "Lyon", "0 - 1", "10/10/2018", 1.2, 2.0, 3.0);
		Assert.assertEquals(Calculate.calculer1N2(TWO, 5.0, BetType.TWO), 15.0, 0);
		
		Line ZERO = new Line("Bordeaux", "Lyon", "3 - 1", "10/10/2018", 1.2, 2.0, 3.0);
		Assert.assertEquals(Calculate.calculer1N2(ZERO, 5.0, BetType.TWO), 0, 0);
		
		Line BEST_ONE = new Line("Bordeaux", "Lyon", "2 - 1", "10/10/2018", 1.6, 2.5, 6.0);
		Assert.assertEquals(Calculate.calculerCoteBasse(BEST_ONE, 1.0), 1.6, 0);
		
		Line BEST_NULL = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 3.0, 2.5, 6.0);
		Assert.assertEquals(Calculate.calculerCoteBasse(BEST_NULL, 1.0), 2.5, 0);
		
		Line BEST_LOST = new Line("Bordeaux", "Lyon", "2 - 1", "10/10/2018", 3.0, 2.5, 6.0);
		Assert.assertEquals(Calculate.calculerCoteBasse(BEST_LOST, 1.0), 0, 0);
		
		Line BEST_GRTH = new Line("Bordeaux", "Lyon", "2 - 1", "10/10/2018", 1.4, 2.5, 6.0);
		Assert.assertEquals(Calculate.calculerCoteBasseMinimum(BEST_GRTH, 1.0, 1.3), 1.4, 0);
		
		Line BEST_GRTH_0 = new Line("Bordeaux", "Lyon", "2 - 1", "10/10/2018", 1.2, 2.5, 6.0);
		Assert.assertEquals(Calculate.calculerCoteBasseMinimum(BEST_GRTH_0, 1.0, 1.4), 1.0, 0);
		
		Line BEST_GRTH_1 = new Line("Bordeaux", "Lyon", "0 - 1", "10/10/2018", 1.5, 2.5, 1.4);
		Assert.assertEquals(Calculate.calculerCoteBasseMinimum(BEST_GRTH_1, 1.0, 1.3), 1.4, 0);
		
		Line PSG = new Line("PSG", "Lyon", "3 - 1", "10/10/2018", 1.3, 2.5, 1.4);
		Assert.assertEquals(Calculate.calculerEquipe(PSG, 1.0, "PSG"), 1.3, 0);
	
		Line NO_PSG = new Line("Bordeaux", "Lyon", "0 - 1", "10/10/2018", 1.5, 2.5, 1.4);
		Assert.assertEquals(Calculate.calculerEquipe(NO_PSG, 1.0, "PSG"), 1, 0);
		
		Line PSG_LOST = new Line("PSG", "Lyon", "0 - 1", "10/10/2018", 1.5, 2.5, 1.4);
		Assert.assertEquals(Calculate.calculerEquipe(PSG_LOST, 1.0, "PSG"), 0, 0);
	}
	
	@Test
	public void calculerDoubleChance() {
		Line LOSER = new Line("AS Monaco", "lens", "4 - 3", "30/08/15",4.25, 3.26, 1.93);
		Assert.assertEquals(Calculate.calculerDoubleChance(LOSER, 1.0, 1.5, 2.0, teams), 0, 0);
		
		Line LENS = new Line("AS Monaco", "Lens", "0 - 3", "30/08/15", 4.0, 3.0, 1.2);
		Assert.assertEquals(Calculate.calculerDoubleChance(LENS, 1.0, 1.5, 1.4, teams), 0, 0);
		
		Line WINNER = new Line("AS Monaco", "Lens", "0 - 3", "30/08/15", 5.0, 4.0, 1.6);
		Assert.assertEquals(Calculate.calculerDoubleChance(WINNER, 10.0, 1.5, 2.0, teams), 13.5, 0);
		
		Line PSG = new Line("AS Monaco", "PSG", "0 - 3", "30/08/15", 5.0, 4.0, 1.6);
		Assert.assertEquals(Calculate.calculerDoubleChance(PSG, 10.0, 1.5, 2.0, teams), 10, 0);
		
		//10 / 4.0 = 2.5
		//10 * 1.6 = 16 - 2.5 / 10 = 1.35 * 10 = 13.5
	}
	
	@Test
	public void getWinner() {
		Assert.assertEquals(Calculate.getWinner("1 - 0"), BetType.ONE);
		Assert.assertEquals(Calculate.getWinner("1 - 1"), BetType.NULL);
		Assert.assertEquals(Calculate.getWinner("1 - 2"), BetType.TWO);
	}
	
	@Test
	public void calculate() {
		Assert.assertEquals(Calculate.calculer(5.0, 1.2), 6.0, 0);
		Assert.assertEquals(Calculate.calculer(5.0, 2.0), 10.0, 0);
		Assert.assertEquals(Calculate.calculer(5.0, 3.0), 15.0, 0);
	}
	
	@Test
	public void getMonEquipe() {
		Line PSG_ONE = new Line("PSG", "Lyon", "3 - 1", "10/10/2018", 1.3, 2.5, 1.4);
		Assert.assertEquals(Calculate.getMonEquipe(PSG_ONE, "PSG"), BetType.ONE);
		
		Line PSG_TWO = new Line("Lyon", "PSG", "3 - 1", "10/10/2018", 1.3, 2.5, 1.4);
		Assert.assertEquals(Calculate.getMonEquipe(PSG_TWO, "PSG"), BetType.TWO);
		
		Line PSG_NO = new Line("Lyon", "Bordeaux", "3 - 1", "10/10/2018", 1.3, 2.5, 1.4);
		Assert.assertEquals(Calculate.getMonEquipe(PSG_NO, "PSG"), null);
	}
	
	@Test
	public void getEquipeCoteBasse() {
		Line ONE = new Line("Bordeaux", "Lyon", "1 - 0", "10/10/2018", 1.2, 2.0, 3.0);
		Assert.assertEquals(Calculate.getEquipeCoteBasse(ONE), BetType.ONE);
		
		Line TWO = new Line("Bordeaux", "Lyon", "0 - 1", "10/10/2018", 2.5, 2.0, 1.3);
		Assert.assertEquals(Calculate.getEquipeCoteBasse(TWO), BetType.TWO);
		
		Line NULL = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.2, 1.1, 1.3);
		Assert.assertEquals(Calculate.getEquipeCoteBasse(NULL), BetType.NULL);
		
		Line NULL_ONE = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.2, 1.2, 1.3);
		Assert.assertEquals(Calculate.getEquipeCoteBasse(NULL_ONE), BetType.ONE);
		
		Line NULL_TWO = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.8, 1.2, 1.2);
		Assert.assertEquals(Calculate.getEquipeCoteBasse(NULL_TWO), BetType.TWO);
	}
	
	@Test
	public void getCoteBasse() {
		Line ONE = new Line("Bordeaux", "Lyon", "1 - 0", "10/10/2018", 1.2, 2.0, 3.0);
		Assert.assertEquals(Calculate.getCoteBasse(ONE), 1.2, 0);
		
		Line TWO = new Line("Bordeaux", "Lyon", "0 - 1", "10/10/2018", 2.5, 2.0, 1.3);
		Assert.assertEquals(Calculate.getCoteBasse(TWO), 1.3, 0);
		
		Line NULL = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.2, 1.1, 1.3);
		Assert.assertEquals(Calculate.getCoteBasse(NULL), 1.1, 0);
		
		Line NULL_ONE = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.2, 1.2, 1.3);
		Assert.assertEquals(Calculate.getCoteBasse(NULL_ONE), 1.2, 0);
		
		Line NULL_TWO = new Line("Bordeaux", "Lyon", "1 - 1", "10/10/2018", 1.8, 1.2, 1.2);
		Assert.assertEquals(Calculate.getCoteBasse(NULL_TWO), 1.2, 0);
	}
	
	@Test
	public void calculerCoteBasseMin() {
		Line NO = new Line("Bordeaux", "Lyon", "1 - 0", "10/10/2018", 1.2, 1.3, 1.4);
		Assert.assertEquals(Calculate.calculerCoteAvecEcart(NO, 1.0, 1.0), 1.0, 0);
		
		Line ONE = new Line("Bordeaux", "Lyon", "1 - 0", "10/10/2018", 1.2, 1.3, 2.3);
		Assert.assertEquals(Calculate.calculerCoteAvecEcart(ONE, 1.0, 1.0), 1.2, 0);
		
		Line TWO = new Line("Bordeaux", "Lyon", "1 - 3", "10/10/2018", 3.5, 1.3, 2.3);
		Assert.assertEquals(Calculate.calculerCoteAvecEcart(TWO, 1.0, 1.0), 2.3, 0);
		
		Line ZERO = new Line("Bordeaux", "Lyon", "1 - 0", "10/10/2018", 3.5, 1.3, 2.3);
		Assert.assertEquals(Calculate.calculerCoteAvecEcart(ZERO, 1.0, 1.0), 0, 0);
	}
	
	public void launch() {
		List<String> lines = new ArrayList<>(4);
		lines.add("04/08/17 20:45,AS Monaco,3 - 2,Toulouse FC,1.29,5.32,10.08,5");
		lines.add("05/08/17 17:15,Paris St. Germain,2 - 0,Amiens AC,1.08,10.49,24.79");
		lines.add("05/08/17 20:00,AS St. Etienne,1 - 1,OGC Nice,2.34,3.09,3.19");
		lines.add("30/08/15 21:00,AS Monaco,0 - 3,Paris St. Germain,4.25,3.26,1.93");
		
		Double sumOne = 0.0;
		Double sumNull = 0.0;
		Double sumTwo = 0.0;
		Double sumBest = 0.0;
		Double sumTeam = 0.0;
		Double mise = 10.0;

		for (String l : lines) {
			String[] a = l.split(",");

			Line line = new Line(a[0], a[1], a[2], a[3], Double.valueOf(a[4].trim()).doubleValue(),
					Double.valueOf(a[5].trim()).doubleValue(), Double.valueOf(a[6].trim()).doubleValue());

			Double gainOne = Calculate.calculer1N2(line, mise, BetType.ONE);
			sumOne += (gainOne - mise);

			Double gainNull = Calculate.calculer1N2(line, mise, BetType.NULL);
			sumNull += (gainNull - mise);

			Double gainTwo = Calculate.calculer1N2(line, mise, BetType.TWO);
			sumTwo += (gainTwo - mise);

			Double gainBest = Calculate.calculer1N2(line, mise, BetType.COTE_BASSE);
			sumBest += (gainBest - mise);
			
			Double gainTeam = Calculate.calculerEquipe(line, mise, "Liverpool FC");
			sumTeam += (gainTeam - mise);

			// System.out.println(String.format("%s => one:%f / null:%f / two:%f / best:%f",
			// line, gainOne, gainNull, gainTwo, gainBest));
		}
		System.out.println(String.format("Gain Total => one:%f / null:%f / two:%f / best:%f / team:%f", sumOne, sumNull, sumTwo, sumBest, sumTeam));
	}

}
