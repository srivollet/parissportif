package fr.srivollet.bean;

public class Bet {

	private BetType betType;
	private Double value;
	private Double ecart;
	private String equipe;
	
	public Bet(BetType betType, Double value, Double ecart, String equipe) {
		super();
		this.betType = betType;
		this.value = value;
		this.ecart = ecart;
		this.equipe = equipe;
	}
	public BetType getBetType() {
		return betType;
	}
	public void setBetType(BetType betType) {
		this.betType = betType;
	}
	public Double getEcart() {
		return ecart;
	}
	public void setEcart(Double ecart) {
		this.ecart = ecart;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getEquipe() {
		return equipe;
	}
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}
}

