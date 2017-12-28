package fantapianto._00_fantaChampionshipRulesExtractor.model;

import java.io.Serializable;
import java.util.List;

public class Points  implements Serializable{

	private static final long serialVersionUID = -6475495633212412217L;

	private List<Double> goalPoints;
	private List<Double> formulaUnoPoints;
	
	private Boolean fasciaConIntornoActive;
	private Double fasciaConIntorno;
	
	private Boolean intornoActive;
	private Double intorno;
	
	
	private Boolean controllaPareggioActive;
	private Double controllaPareggio;
	
	private Boolean differenzaPuntiActive;
	private Double differenzaPunti;
	
	private Boolean portiereImbattutoActive;
	private Double portiereImbattuto;
	
	
	public List<Double> getGoalPoints() {
		return goalPoints;
	}
	public void setGoalPoints(List<Double> goalPoints) {
		this.goalPoints = goalPoints;
	}
	public List<Double> getFormulaUnoPoints() {
		return formulaUnoPoints;
	}
	public void setFormulaUnoPoints(List<Double> formulaUnoPoints) {
		this.formulaUnoPoints = formulaUnoPoints;
	}
	public Boolean isFasciaConIntornoActive() {
		return fasciaConIntornoActive;
	}
	public void setFasciaConIntornoActive(Boolean fasciaConIntornoActive) {
		this.fasciaConIntornoActive = fasciaConIntornoActive;
	}
	public Double getFasciaConIntorno() {
		return fasciaConIntorno;
	}
	public void setFasciaConIntorno(Double fasciaConIntorno) {
		this.fasciaConIntorno = fasciaConIntorno;
	}
	public Boolean isIntornoActive() {
		return intornoActive;
	}
	public void setIntornoActive(Boolean intornoActive) {
		this.intornoActive = intornoActive;
	}
	public Double getIntorno() {
		return intorno;
	}
	public void setIntorno(Double intorno) {
		this.intorno = intorno;
	}
	public Boolean isControllaPareggioActive() {
		return controllaPareggioActive;
	}
	public void setControllaPareggioActive(Boolean controllaPareggioActive) {
		this.controllaPareggioActive = controllaPareggioActive;
	}
	public Double getControllaPareggio() {
		return controllaPareggio;
	}
	public void setControllaPareggio(Double controllaPareggio) {
		this.controllaPareggio = controllaPareggio;
	}
	public Boolean isDifferenzaPuntiActive() {
		return differenzaPuntiActive;
	}
	public void setDifferenzaPuntiActive(Boolean differenzaPuntiActive) {
		this.differenzaPuntiActive = differenzaPuntiActive;
	}
	public Double getDifferenzaPunti() {
		return differenzaPunti;
	}
	public void setDifferenzaPunti(Double differenzaPunti) {
		this.differenzaPunti = differenzaPunti;
	}
	public Boolean isPortiereImbattutoActive() {
		return portiereImbattutoActive;
	}
	public void setPortiereImbattutoActive(Boolean portiereImbattutoActive) {
		this.portiereImbattutoActive = portiereImbattutoActive;
	}
	public Double getPortiereImbattuto() {
		return portiereImbattuto;
	}
	public void setPortiereImbattuto(Double portiereImbattuto) {
		this.portiereImbattuto = portiereImbattuto;
	}
	@Override
	public String toString() {
		return "Points [goalPoints=" + goalPoints + "\n formulaUnoPoints=" + formulaUnoPoints
				+ "\n isFasciaConIntornoActive=" + fasciaConIntornoActive + "\n fasciaConIntorno=" + fasciaConIntorno
				+ "\n isIntornoActive=" + intornoActive + "\n intorno=" + intorno + "\n isControllaPareggioActive="
				+ controllaPareggioActive + "\n controllaPareggio=" + controllaPareggio + "\n isDifferenzaPuntiActive="
				+ differenzaPuntiActive + "\n differenzaPunti=" + differenzaPunti + "\n isPortiereImbattutoActive="
				+ portiereImbattutoActive + "\n portiereImbattuto=" + portiereImbattuto + "]";
	}
	
	
	

}
