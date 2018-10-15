package fr.srivollet.bean;

public class Line {

	private String equipeDomicile;
	private String equipeExterieure;
	private String resultat;
	private String datetime;
	private double cote1;
	private double cote2;
	private double coteN;
	
	
	
	public Line(String equipeDomicile, String equipeExterieure, String resultat, String datetime, double cote1,
			 double coteN, double cote2) {
		super();
		this.equipeDomicile = equipeDomicile;
		this.equipeExterieure = equipeExterieure;
		this.resultat = resultat;
		this.datetime = datetime;
		this.cote1 = cote1;
		this.coteN = coteN;
		this.cote2 = cote2;
	}
	
	public String getEquipeDomicile() {
		return equipeDomicile;
	}
	public void setEquipeDomicile(String equipeDomicile) {
		this.equipeDomicile = equipeDomicile;
	}
	public String getEquipeExterieure() {
		return equipeExterieure;
	}
	public void setEquipeExterieure(String equipeExterieure) {
		this.equipeExterieure = equipeExterieure;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public double getCote1() {
		return cote1;
	}
	public void setCote1(double cote1) {
		this.cote1 = cote1;
	}
	public double getCote2() {
		return cote2;
	}
	public void setCote2(double cote2) {
		this.cote2 = cote2;
	}
	public double getCoteN() {
		return coteN;
	}
	public void setCoteN(double coteN) {
		this.coteN = coteN;
	}

	@Override
	public String toString() {
		return "Line [equipeDomicile=" + equipeDomicile + ", equipeExterieure=" + equipeExterieure + ", resultat="
				+ resultat + ", datetime=" + datetime + ", cote1=" + cote1 + ", coteN=" + coteN + ", cote2=" + cote2
				+ "]";
	}
}
