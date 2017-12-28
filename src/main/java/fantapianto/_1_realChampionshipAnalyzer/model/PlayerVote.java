package fantapianto._1_realChampionshipAnalyzer.model;

public class PlayerVote {
	
	private RoleEnum role;
	private String name;
	private String team;
	private Double vote;
	private Double fantaVote;
	private boolean alreadyUsed;
	private Double goalkeerModifier;
	private Double strikerModifier;
	
	
	
	public PlayerVote(RoleEnum role, String name, String team, Double vote, Double fantaVote) {
		this.role = role;
		this.name = name;
		this.team = team;
		this.vote = vote;
		this.fantaVote = fantaVote;
	}
	
	public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Double getVote() {
		return vote;
	}
	public void setVote(Double vote) {
		this.vote = vote;
	}
	public Double getFantaVote() {
		return fantaVote;
	}
	public void setFantaVote(Double fantaVote) {
		this.fantaVote = fantaVote;
	}

	@Override
	public String toString() {
		return "PlayerVote [role=" + role + ", name=" + name + ", team=" + team + ", vote=" + vote + ", fantaVote="
				+ fantaVote + "] \n";
	}

	public boolean isAlreadyUsed() {
		return alreadyUsed;
	}

	public void setAlreadyUsed(boolean alreadyUsed) {
		this.alreadyUsed = alreadyUsed;
	}

	public Double getGoalkeerModifier() {
		return goalkeerModifier;
	}

	public void setGoalkeerModifier(Double goalkeerModifier) {
		this.goalkeerModifier = goalkeerModifier;
	}

	public Double getStrikerModifier() {
		return strikerModifier;
	}

	public void setStrikerModifier(Double strikerModifier) {
		this.strikerModifier = strikerModifier;
	}

	
	
	
	
}
