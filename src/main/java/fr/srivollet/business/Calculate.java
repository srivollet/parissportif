package fr.srivollet.business;

import java.util.List;

import fr.srivollet.bean.BetType;
import fr.srivollet.bean.Line;

public class Calculate {
	
	public static Double calculerDoubleChance(Line line, Double mise, Double coteBasseMinimum, Double coteBasseMaximum, List<String> teams) {
		
		BetType equipeCoteBasse = getEquipeCoteBasse(line);
		BetType equipeWinner = getWinner(line.getResultat());
		Double coteBasse = getCoteBasse(line);
		
		if(teams.contains(line.getEquipeDomicile()) || teams.contains(line.getEquipeExterieure()))
			return mise;
		
		if(coteBasse.compareTo(coteBasseMinimum) < 0 || coteBasse.compareTo(coteBasseMaximum) > 0)
			return mise;
		
		//forcement equipe à docmicile
		//if(coteBasse.compareTo(line.getCote1())!= 0)
		//	return mise;
		
		Double coteNull = line.getCoteN();
		
		if(equipeWinner == BetType.NULL)
			return mise;
		
		if(equipeWinner != equipeCoteBasse)
			return Double.valueOf(0);
		
		Double miseNull = mise / coteNull;
		Double gainGagnant = mise * coteBasse - miseNull;
		Double coteDoubleChance =  gainGagnant / mise;
		
		return calculer(mise, coteDoubleChance); 
	}

	public static Double calculerEquipe(Line line, Double mise, String team) {
		BetType winner = getWinner(line.getResultat());
		BetType monequipe = getMonEquipe(line, team);
		
		if(monequipe == null)
			return mise;
		
		if(winner.equals(monequipe)) {
			if(monequipe.equals(BetType.ONE))
				return calculer(mise, line.getCote1());
			
			if(monequipe.equals(BetType.TWO))
				return calculer(mise, line.getCote2());
		}
		return Double.valueOf(0);
	}
	
	public static Double calculerCoteAvecEcart(Line line, Double mise, Double ecartCote) {
		
		if((line.getCote2() - line.getCote1()) > ecartCote) {
			return calculer1N2(line, mise, BetType.ONE);
			
		} else if ((line.getCote1() - line.getCote2()) > ecartCote){
			return calculer1N2(line, mise, BetType.TWO);
		}
		return mise;
	}
	
	public static Double calculerCoteBasseMinimum(Line line, Double mise, Double coteMinimum) {
		
		if(Double.compare(getCoteBasse(line), coteMinimum) < 0)
			return mise;
		
		return calculerCoteBasse(line, mise);
	}
	
	public static Double calculerCoteBasse(Line line, Double mise) {
		BetType winner = getWinner(line.getResultat());
		BetType coteBasse = getEquipeCoteBasse(line);
		
		if(winner.equals(coteBasse)) {
			if(BetType.ONE.equals(coteBasse))
				return calculer(mise, line.getCote1());
			
			if(BetType.TWO.equals(coteBasse))
				return calculer(mise, line.getCote2());
			
			return calculer(mise, line.getCoteN());
		}
		return Double.valueOf(0);
	}
	
	public static Double calculer1N2(Line line, Double mise, BetType bet) {
		BetType winner = getWinner(line.getResultat());
		
		if(winner.equals(BetType.ONE) && bet.equals(BetType.ONE)) {
			return calculer(mise, line.getCote1());
		
		} else if (winner.equals(BetType.TWO) && bet.equals(BetType.TWO)) {
			return calculer(mise, line.getCote2());
		
		} else if(winner.equals(BetType.NULL) && bet.equals(BetType.NULL)) {
			return calculer(mise, line.getCoteN());
		}
		return Double.valueOf(0);
	}
	
	public static BetType getWinner(String resultat){
		String[] r = resultat.split("-");
		
		Integer one = Integer.valueOf(r[0].trim());
		Integer two = Integer.valueOf(r[1].trim());
		
		if(one>two)
			return BetType.ONE;
		
		if(two>one)
			return BetType.TWO;
		
		return BetType.NULL;
	}
	
	public static BetType getMonEquipe(Line line, String team){
	
		if(line.getEquipeDomicile().equalsIgnoreCase(team))
			return BetType.ONE;
		
		if(line.getEquipeExterieure().equalsIgnoreCase(team))
			return BetType.TWO;
		
		return null;
	}
	
	public static Double getCoteBasse(Line line) {
		if(line.getCote1() < line.getCote2() && line.getCote1() < line.getCoteN())
			return line.getCote1();
		
		if(line.getCote2() < line.getCote1() && line.getCote2() < line.getCoteN())
			return line.getCote2();
		
		if(line.getCote1() == line.getCoteN())
			return line.getCote1();
		
		if(line.getCote2() == line.getCoteN())
			return line.getCote2();
		
		return line.getCoteN();
	}
	
	public static BetType getEquipeCoteBasse(Line line) {
		if(line.getCote1() < line.getCote2() && line.getCote1() < line.getCoteN())
			return BetType.ONE;
		
		if(line.getCote2() < line.getCote1() && line.getCote2() < line.getCoteN())
			return BetType.TWO;
		
		if(line.getCote1() == line.getCoteN())
			return BetType.ONE;
		
		if(line.getCote2() == line.getCoteN())
			return BetType.TWO;
		
		return BetType.NULL;
	}
	
	public static Double calculer(Double mise, Double cote) {
		return mise * cote;
	}

}
