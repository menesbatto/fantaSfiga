package fantapianto._6_rankingAnalizer.model;

import java.io.Serializable;
import java.util.List;

public class Pair implements Serializable{

	private static final long serialVersionUID = -1183201598168181426L;
	

	private String name;
	private Double value;
	private List<Double> valueList;
	
	
	public Pair(String name, Double value) {
		this.name = name;
		this.value = value;
	}
	public Pair(String name, List<Double> valueList) {
		this.name = name;
		this.setValueList(valueList);
		this.value=0.0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public List<Double> getValueList() {
		return valueList;
	}
	public void setValueList(List<Double> valueList) {
		this.valueList = valueList;
	}
	@Override
	public String toString() {
		return "\n" + name + "\t" + getFormattedValue(value) + "\t " + getFormattedValueList(valueList);
	}
	
	private String getFormattedValueList(List<Double> valueList) {
		String s= "";
		if (valueList!= null)
			for(Double value : valueList){
				s += getFormattedValue(value) + "\t";
			} 	
		return s;
	}
	private String getFormattedValue(Double value2) {
		return (value2 + "").replace('.', ',');
	}
	private String getNameToString() {
		return this.name.length() > 11 ? this.name.substring(0,10) : this.name;
	}

	 

	
	
}
