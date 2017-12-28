package fantapianto._4_seasonsGenerator.model;

public class PlayerOrder {
	
	private String name;
	private String letter;
	
	
	
	
	public PlayerOrder(String name, String letter) {
		this.name = name;
		this.letter = letter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	@Override
	public String toString() {
		return "PlayerOrder [name=" + name + ", letter=" + letter + "]";
	}
	
	

}
