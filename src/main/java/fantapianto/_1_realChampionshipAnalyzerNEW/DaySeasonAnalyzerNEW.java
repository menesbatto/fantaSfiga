package fantapianto._1_realChampionshipAnalyzerNEW;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fantapianto._1_realChampionshipAnalyzer.model.LineUp;
import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;
import fantapianto._1_realChampionshipAnalyzer.model.PlayerVote;
import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;
import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto.utils.AppConstants;

public class DaySeasonAnalyzerNEW {

	public static SeasonDayResult execute(String seasonDayURL) {
		
		try {
			
			Document doc = Jsoup.connect(seasonDayURL).get();
		
//			System.out.println(doc);
			String daySeasonName = seasonDayURL.substring(seasonDayURL.indexOf("=")+1);
			
			Elements lineUpElements = doc.select("div.itemBox");
			LineUp createdLineUp1, createdLineUp2;
			List<LineUp> linesUp = new ArrayList<LineUp>();
			System.out.println(doc);
			// Crea oggetti formazioni e modificatori dall'excel
			for (Element lineUpElem : lineUpElements) {
				createdLineUp1 = createLineUp(lineUpElem.child(1), lineUpElem.child(0).getElementsByTag("h3").get(0).text() );
				linesUp.add(createdLineUp1);
				createdLineUp2 = createLineUp(lineUpElem.child(2), lineUpElem.child(0).getElementsByTag("h3").get(2).text() );
				linesUp.add(createdLineUp2);
			}
			
			// Calcola la formazione scesa in campo
			calculareRealLineUps(linesUp);

			calculateMiddlefieldVariation(linesUp);

			// System.out.println(linesUp); 
			List<LineUpLight> linesUpLight = new ArrayList<LineUpLight>();
			LineUpLight lineUpLight;
			for (LineUp lineUp : linesUp) {

				lineUpLight = createLineUpLight(lineUp);
				linesUpLight.add(lineUpLight);
			}

//			System.out.println(linesUpLight);
			
			SeasonDayResult result = new SeasonDayResult(daySeasonName, linesUpLight);
			
			return result;
			
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		return null;

	}

	private static Double calculateMiddlefieldVariation(List<LineUp> linesUp) {
		int middlefieldersNumber = 0;
		Double middlefieldersComulativeVote = 0.0;
		Double middlefieldersVariation = 0.0;
		Double malus = 0.0;
		Double bonus = 0.0;
		for (LineUp lineUp : linesUp) {
			middlefieldersComulativeVote = 0.0;
			middlefieldersVariation = 0.0;
			malus = 0.0;
			bonus = 0.0;
			middlefieldersNumber = 0;
			for (PlayerVote player : lineUp.getFinalLineUp()) {
				if (player.getRole().equals(RoleEnum.C)) {
					if (player.getTeam().equals("OFFICE"))
						middlefieldersComulativeVote += 5;
					else	
						middlefieldersComulativeVote += player.getVote();
					middlefieldersNumber++;
				}
			}

			middlefieldersVariation = middlefieldersComulativeVote - (middlefieldersNumber * 6);
			if (middlefieldersNumber < 4) {
				malus = (double) ((4 - middlefieldersNumber) * -1);
			}
			if (middlefieldersNumber > 4) {
				bonus = (double) ((middlefieldersNumber - 4));
			}
			middlefieldersVariation += malus;
			middlefieldersVariation += bonus;
			lineUp.setMiddlefieldersVariation(middlefieldersVariation);
		}
		
		return middlefieldersVariation;

	}

	private static LineUpLight createLineUpLight(LineUp lineUp) {
		LineUpLight lul = new LineUpLight();
		lul.setTeamName(lineUp.getTeamName());
		Double totalWithoutMiddlefielderModifier = 0.0;
		for (PlayerVote pv : lineUp.getFinalLineUp()) {
			totalWithoutMiddlefielderModifier += pv.getFantaVote();
		}
		totalWithoutMiddlefielderModifier += lineUp.getDefenderModifier();
		totalWithoutMiddlefielderModifier += lineUp.getStrickerModifier();

		lul.setTotalWithoutGoalkeeperAndMiddlefielderModifiers(totalWithoutMiddlefielderModifier);
		lul.setMiddlefieldersVariation(lineUp.getMiddlefieldersVariation());
		return lul;

	}

	private static void calculareRealLineUps(List<LineUp> linesUp) {

		Integer substitutions = 0;
		for (LineUp lineUp : linesUp) {
			substitutions = 0;
			List<PlayerVote> finalLineUp = new ArrayList<PlayerVote>();

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getGoalKeeper(), lineUp.getReserves(),
					RoleEnum.P, substitutions, AppConstants.goalkeeperPlayerOfficeVote);

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getDefenders(), lineUp.getReserves(),
					RoleEnum.D, substitutions, AppConstants.movementsPlayerOfficeVote);

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getMidfielders(), lineUp.getReserves(),
					RoleEnum.C, substitutions, AppConstants.movementsPlayerOfficeVote);

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getStrikers(), lineUp.getReserves(),
					RoleEnum.A, substitutions, AppConstants.movementsPlayerOfficeVote);

			lineUp.setFinalLineUp(finalLineUp);
		}

	}

	private static Integer addPlayerVoteForRole(List<PlayerVote> finalLineUp, List<PlayerVote> roleSet,
			List<PlayerVote> reserves, RoleEnum role, Integer substitutions, Double officePlayerVote) {
		PlayerVote playerVoteToAdd;
		for (PlayerVote pv : roleSet) {
			playerVoteToAdd = pv;
			if (playerVoteToAdd.getFantaVote() == null) {
				//Ricerca in panchina 
				playerVoteToAdd = getReserveForPlayer(playerVoteToAdd.getRole(), reserves, substitutions);
				if (playerVoteToAdd != null){
					substitutions++;
				}
				//Inserimento di ufficio o niente
				else {//if (playerVoteToAdd == null) {
					if (substitutions < 3) {
						playerVoteToAdd = new PlayerVote(role, "OFFICE", "OFFICE", officePlayerVote, officePlayerVote);
						substitutions++;
					} else {
						playerVoteToAdd = new PlayerVote(role, "ZERO", "ZERO", 0.0, 0.0);
					}
				}
			}
			finalLineUp.add(playerVoteToAdd);
		}
		return substitutions;
	}

	private static PlayerVote getReserveForPlayer(RoleEnum role, List<PlayerVote> reserves, Integer substitutions) {
		PlayerVote playerVoteToAdd = null;
		if (substitutions < 3) {
			for (PlayerVote reserve : reserves){
				if (reserve.getRole().equals( role )){
					if (!reserve.isAlreadyUsed()){
						playerVoteToAdd = reserve;
						if (playerVoteToAdd.getFantaVote() != null) {
							reserve.setAlreadyUsed(true);
							break;
						}
						else {
							reserve.setAlreadyUsed(true);
						}
					}
				}
			}
		}
		if (playerVoteToAdd != null && playerVoteToAdd.getFantaVote() == null)
			playerVoteToAdd = null;
		return playerVoteToAdd;
	}

	private static LineUp createLineUp(Element lineUpDomElement, String teamName) {
		LineUp lineUp = new LineUp();
		
//		String teamName = lineUpDomElement.getElementsByTag("h3").get(0).ownText();
		lineUp.setTeamName(teamName);
		
		Elements playersElem = lineUpDomElement.getElementsByClass("playerRow");
		PlayerVote playerVote;
		
		boolean reserveSection = false;

		for (int i = 0; i < playersElem.size(); i++) {
			Element playerElem = playersElem.get(i);
			playerVote = getPlayer(playerElem);
			if ( i > 10 )
				reserveSection = true;
		
			switch (playerVote.getRole()) {
			case P:
				if (!reserveSection)
					lineUp.getGoalKeeper().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
//						lineUp.getGoalKeeperReserve().add(playerVote);
				break;
			case D:
				if (!reserveSection)
					lineUp.getDefenders().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
//						lineUp.getDefendersReserves().add(playerVote);
				break;
			case C:
				if (!reserveSection)
					lineUp.getMidfielders().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
//						lineUp.getMidfieldersReserves().add(playerVote);
				break;
			case A:
				if (!reserveSection)
					lineUp.getStrikers().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
//						lineUp.getStrikersReserves().add(playerVote);
				break;

			default:
				break;
			}
		} 
		

		Elements strickerModifierDomElement = lineUpDomElement.getElementsMatchingOwnText("Modificatore attacco:");
		Double strikerModifier = strickerModifierDomElement.isEmpty() ? 0 : getVote(strickerModifierDomElement.get(0).siblingNodes().get(0).childNode(0).toString());
		lineUp.setStrickerModifier(strikerModifier);
		Elements defenderModifiersDomElement = lineUpDomElement.getElementsMatchingOwnText("Modificatore difesa:");
		Double defenderModifier = defenderModifiersDomElement.isEmpty() ? 0 : getVote(defenderModifiersDomElement.get(0).siblingNodes().get(0).childNode(0).toString());
		lineUp.setDefenderModifier(defenderModifier);
		
		System.out.println(lineUp);
		return lineUp;
	}

	private static PlayerVote getPlayer(Element playerElem) {
		RoleEnum role = RoleEnum.valueOf(playerElem.getElementsByClass("myhidden-xs").get(0).text());
		String name = playerElem.getElementsByClass("sh").get(0).text();
		String team = playerElem.getElementsByClass("pt").get(0).text();
		Double vote = getVote(playerElem.getElementsByClass("pt").get(1).text());
		Double fantaVote = getVote(playerElem.getElementsByClass("pt").get(2).text());
		PlayerVote p = new PlayerVote(role, name, team, vote, fantaVote);

		return p;
	}
	
	private static Double getVote(String voteString) {
		Double vote = null;
		if (!voteString.equals("-"))
			vote = Double.valueOf(voteString.replace(",", "."));
		return vote;
	}
	
	
	
}