package fantapianto._1_realChampionshipAnalyzerFINAL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fantapianto._00_fantaChampionshipRulesExtractor.RulesExpertMain;
import fantapianto._00_fantaChampionshipRulesExtractor.model.BonusMalus;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Modifiers;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Rules;
import fantapianto._0_realChampionshipVotesDownloader.model.PlayerVoteComplete;
import fantapianto._1_realChampionshipAnalyzer.model.LineUp;
import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;
import fantapianto._1_realChampionshipAnalyzer.model.PlayerVote;
import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;
import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto.utils.AppConstants;

public class DaySeasonAnalyzerFINAL {
	
	static Map<String, List<PlayerVoteComplete>> seasonDayVotes;
	static Rules rules = RulesExpertMain.getRules();
	
	static Map<String, ArrayList<String>> force6map = initTo6MatchesMap();
	
	
	public static SeasonDayResult execute(String seasonDayLinesUpURL, Map<String, List<PlayerVoteComplete>> seasonDayVotesFromMain, String serieASeasonDay) {
		
		try {
 			seasonDayVotes = seasonDayVotesFromMain;
//			AppConstants.LEAGUE_NAME = JOptionPane.showInputDialog("Inserisci il nome della lega"); 
			
			//Recupero le formazioni di giornata
			Document doc = Jsoup.connect(seasonDayLinesUpURL).get();
		
//			System.out.println(doc);
			String daySeasonName = seasonDayLinesUpURL.substring(seasonDayLinesUpURL.indexOf("=")+1);
			
			Elements lineUpElements = doc.select("div.itemBox");
			LineUp createdLineUp1, createdLineUp2;
			List<LineUp> linesUp = new ArrayList<LineUp>();
//			System.out.println(doc);
			// Crea oggetti formazioni e modificatori dall'excel
			for (Element lineUpElem : lineUpElements) {
				createdLineUp1 = createLineUp(lineUpElem.child(1), lineUpElem.child(0).getElementsByTag("h3").get(0).text(), serieASeasonDay );
				linesUp.add(createdLineUp1);
				createdLineUp2 = createLineUp(lineUpElem.child(2), lineUpElem.child(0).getElementsByTag("h3").last().text(), serieASeasonDay );
				linesUp.add(createdLineUp2);
			}

			// Calcola la formazione scesa in campo
			calculareRealLineUps(linesUp);
			
			// Calcola i modificatori
			if ( rules.getModifiers().isGoalkeeperModifierActive() )
				if (!AppConstants.FORCE_GOALKEEPER_MODIFIER_DISABLED)
					calculateGoalkeeperTeamsModifier(linesUp);
			if ( rules.getModifiers().isDefenderModifierActive() )
				if (!AppConstants.FORCE_DEFENDER_MODIFIER_DISABLED) 
					calculateDefenderTeamsModifier(linesUp);
			if ( rules.getModifiers().isMiddlefielderModifierActive() )
				if (!AppConstants.FORCE_MIDDLEFIELD_MODIFIER_DISABLED)
					calculateMiddlefieldTeamsVariation(linesUp);
			if ( rules.getModifiers().isStrikerModifierActive() )
				if (!AppConstants.FORCE_STRIKER_MODIFIER_DISABLED)
					calculateStrikerTeamsModifier(linesUp);
			if ( rules.getModifiers().isPerformanceModifierActive())
				if (!AppConstants.FORCE_PERFORMANCE_MODIFIER_DISABLED)
					calculatePerformanceTeamsModifier(linesUp);
			if ( rules.getModifiers().isFairPlayModifierActive())
				if (!AppConstants.FORCE_FAIR_PLAY_MODIFIER_DISABLED)
					calculateFairPlayTeamsModifier(linesUp);
			
			// System.out.println(linesUp);
			List<LineUpLight> linesUpLight = new ArrayList<LineUpLight>();
			LineUpLight lineUpLight;
			for (LineUp lineUp : linesUp) {

				lineUpLight = createLineUpLight(lineUp);
				linesUpLight.add(lineUpLight);
			}

			
			SeasonDayResult result = new SeasonDayResult(daySeasonName, linesUpLight);
			
			return result;
			
		}
		catch (Exception e){
			System.out.println(e);
		}
		 
		return null;

	}


	


	private static Map<String, ArrayList<String>> initTo6MatchesMap() {
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		list.add("LAZ");
		list.add("UDI");
		map.put("12", list);
		return map;
	}





	private static void calculateGoalkeeperTeamsModifier(List<LineUp> linesUp) {
		Double goalkeeperrModifier = 0.0;
		for (LineUp lineUp : linesUp){
			for (PlayerVote player : lineUp.getFinalLineUp()){
				if (player.getRole().equals(RoleEnum.P)){
					goalkeeperrModifier = player.getGoalkeerModifier();
					break;
				}
			}
//			if (lineUp.getGoalkeeperModifier() != goalkeeperrModifier)
//				System.out.println("C'è una disprepanza tra il modificatore dei portieri calcolato dal web e quello calcolato dalla app " + lineUp);
			lineUp.setGoalkeeperModifier(goalkeeperrModifier);
		}
		
	}

	private static void calculateDefenderTeamsModifier(List<LineUp> linesUp) {
		Double defenderSum, defenderNum, defendereVoteAvg;
		List<Double> maxFour;
		for (LineUp lineUp : linesUp){
			maxFour = new ArrayList<Double>();
			defenderNum = 0.0;
			for (PlayerVote newVote : lineUp.getFinalLineUp()){
				if (newVote.getRole().equals(RoleEnum.D)){
					if (   !newVote.getName().equals("OFFICE") && !newVote.getName().equals("ZERO") ){
						defenderNum ++;
						if (maxFour.size() < 3){
							maxFour.add(newVote.getVote());
						}
						else {
							int minIndex = getMinIndex(maxFour);
							if (newVote.getVote() > maxFour.get(minIndex)  ){
								maxFour.remove(minIndex);
								maxFour.add(minIndex, newVote.getVote());
							}
						}
					}
				}
			}
			boolean goalkeeperHasPlayed = false;
			PlayerVote goalkeeperVote = lineUp.getFinalLineUp().get(0);
			if (   !goalkeeperVote.getName().equals("OFFICE") && !goalkeeperVote.getName().equals("ZERO") ){
				maxFour.add(goalkeeperVote.getVote());
				goalkeeperHasPlayed = true;
			}
			
			defenderSum = 0.0;
			if (defenderNum >= 4 && goalkeeperHasPlayed){
				for (Double vote : maxFour)
					defenderSum += vote;
				
				defendereVoteAvg = defenderSum / 4;
				
				
				Double defendereModifier = 0.0;
				if (defendereVoteAvg >= 6 && defendereVoteAvg < 6.5)			defendereModifier = rules.getModifiers().getDefenderAvgVote6();
				else if (defendereVoteAvg >= 6.5 && defendereVoteAvg < 7)		defendereModifier = rules.getModifiers().getDefenderAvgVote6half();
				else if (defendereVoteAvg >= 7 )								defendereModifier = rules.getModifiers().getDefenderAvgVote7();
				
//				if (!lineUp.getDefenderModifier().equals(defendereModifier))
//					System.out.println("C'è una discrepanza tra il modificatore dei difesori calcolato dal web e quello calcolato dalla app " + lineUp);
		
				lineUp.setDefenderModifier(defendereModifier);
			}
		}
	}

	private static Integer getMinIndex(List<Double> maxFour) {
		Double min = 10.0;
		Integer index = 0;
		for (int i = 0; i < maxFour.size(); i++) {
			Double num = maxFour.get(i);
			if (num < min){
				min = num;
				index = i;
			}
		}
			
		return index;
		
	}





	private static void calculateFairPlayTeamsModifier(List<LineUp> linesUp) {
//		for (LineUp lineUp : linesUp){
//			lineUp.setFairPlayModifier(0.0);
//		}
	}
		
	private static void calculatePerformanceTeamsModifier(List<LineUp> linesUp) {
		Double performanceModifier;
		Integer  accceptableVotesNumber = 0; 
		for (LineUp lineUp : linesUp){
			
			accceptableVotesNumber = 0;
			performanceModifier = 0.0;
			for (PlayerVote pv : lineUp.getFinalLineUp()){
				if (pv.getVote() >= 6){
					accceptableVotesNumber ++;
				}
			}
//			if (!lineUp.getStrickerModifier().equals(stikerModifier))
//				System.out.println("C'è una disprepanza tra il modificatore degli attaccanti calcolato dal web e quello calcolato dalla app " + lineUp);
			Modifiers mod = rules.getModifiers();
			if (accceptableVotesNumber == 0)	performanceModifier = mod.getPerformance0();  
			if (accceptableVotesNumber == 1)	performanceModifier = mod.getPerformance1();  
			if (accceptableVotesNumber == 2)	performanceModifier = mod.getPerformance2();  
			if (accceptableVotesNumber == 3)	performanceModifier = mod.getPerformance3();  
			if (accceptableVotesNumber == 4)	performanceModifier = mod.getPerformance4();  
			if (accceptableVotesNumber == 5)	performanceModifier = mod.getPerformance5();  
			if (accceptableVotesNumber == 6)	performanceModifier = mod.getPerformance6();  
			if (accceptableVotesNumber == 7)	performanceModifier = mod.getPerformance7();  
			if (accceptableVotesNumber == 8)	performanceModifier = mod.getPerformance8();  
			if (accceptableVotesNumber == 9)	performanceModifier = mod.getPerformance9();  
			if (accceptableVotesNumber == 10)	performanceModifier = mod.getPerformance10();  
			if (accceptableVotesNumber == 11)	performanceModifier = mod.getPerformance11();  
			
			lineUp.setPerformanceModifier(performanceModifier);
		}
		
	}
	
	private static void calculateStrikerTeamsModifier(List<LineUp> linesUp) {
		Double stikerModifier;
		for (LineUp lineUp : linesUp){
			stikerModifier = 0.0;
			for (PlayerVote pv : lineUp.getFinalLineUp()){
				if (pv.getRole().equals(RoleEnum.A)){
					stikerModifier += pv.getStrikerModifier();
				}
			}
//			if (!lineUp.getStrickerModifier().equals(stikerModifier))
//				System.out.println("C'è una disprepanza tra il modificatore degli attaccanti calcolato dal web e quello calcolato dalla app " + lineUp);
			
			lineUp.setStrickerModifier(stikerModifier);
		}
		
	}

	private static Double calculateGoalkeeperModifier(PlayerVoteComplete pvcVote) {
		Double vote = pvcVote.getVote();
//		if (pvcVote.getTakenGoals() == 0){
			if (vote < 3.0)			return rules.getModifiers().getGoalkeeperVote3();
			if (vote.equals( 3.0 )) 	return rules.getModifiers().getGoalkeeperVote3();
			if (vote.equals( 3.5 )) return rules.getModifiers().getGoalkeeperVote3half();
			if (vote.equals( 4.0 )) 	return rules.getModifiers().getGoalkeeperVote4();
			if (vote.equals( 4.5 )) return rules.getModifiers().getGoalkeeperVote4half();
			if (vote.equals( 5.0 )) 	return rules.getModifiers().getGoalkeeperVote5();
			if (vote.equals( 5.5 )) 	return rules.getModifiers().getGoalkeeperVote5half();
			if (vote.equals( 6.0 )) 	return rules.getModifiers().getGoalkeeperVote6();
			if (vote.equals( 6.5 )) return rules.getModifiers().getGoalkeeperVote6half();
			if (vote.equals( 7.0 )) 	return rules.getModifiers().getGoalkeeperVote7();
			if (vote.equals( 7.5 )) return rules.getModifiers().getGoalkeeperVote7half();
			if (vote.equals( 8.0 )) 	return rules.getModifiers().getGoalkeeperVote8();
			if (vote.equals( 8.5 )) return rules.getModifiers().getGoalkeeperVote8half();
			if (vote.equals( 9.0 )) 	return rules.getModifiers().getGoalkeeperVote9();
			if (vote > 9.0) 			return rules.getModifiers().getGoalkeeperVote9();
			
//		}
		return 0.0;	 
				 
		
	}

	private static Double calculateMiddlefieldTeamsVariation(List<LineUp> linesUp) {
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

	

	private static Double calculateStrikerModifier(PlayerVoteComplete pvcVote) {
		Double vote = pvcVote.getVote();
		if (vote != null)
			if ( pvcVote.getScoredGoals() == 0 && pvcVote.getScoredPenalties() == 0){
				if (vote.equals( 6.0 )) 		return rules.getModifiers().getStrikerVote6();
				if (vote.equals( 6.5 )) 		return rules.getModifiers().getStrikerVote6half();
				if (vote.equals( 7.0 )) 		return rules.getModifiers().getStrikerVote7();
				if (vote.equals( 7.5 )) 		return rules.getModifiers().getStrikerVote7half();
				if (vote.equals( 8.0 )) 		return rules.getModifiers().getStrikerVote8();
				if (vote > 8.0) 				return rules.getModifiers().getStrikerVote8();
			}
		return 0.0;	 
	}





	private static Map<String, String> populateTeamIds() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = Jsoup.connect(AppConstants.TEAMS_IDS_URL).get();
			Elements teamsIds = doc.select("div.row.no-gutter.tbvoti");
			for(Element team : teamsIds){
				String gazzettaTeamId = team.attr("data-team");
				String name = team.attr("id").toUpperCase();
				map.put(name, gazzettaTeamId);
			}
//			System.out.println(teamsIds);
		}
		catch (Exception e){
			
		}
		return map;
	}



	
	private static LineUpLight createLineUpLight(LineUp lineUp) {
		LineUpLight lul = new LineUpLight();
		lul.setTeamName(lineUp.getTeamName());
		Double totalWithoutGoalkeeperAndMiddlefielderModifier = 0.0;
		for (PlayerVote pv : lineUp.getFinalLineUp()) {
			totalWithoutGoalkeeperAndMiddlefielderModifier += pv.getFantaVote();
		}
		totalWithoutGoalkeeperAndMiddlefielderModifier += lineUp.getDefenderModifier();
		totalWithoutGoalkeeperAndMiddlefielderModifier += lineUp.getStrickerModifier();
		totalWithoutGoalkeeperAndMiddlefielderModifier += lineUp.getPerformanceModifier();
		totalWithoutGoalkeeperAndMiddlefielderModifier += lineUp.getFairPlayModifier();

		
		lul.setTotalWithoutGoalkeeperAndMiddlefielderModifiers(totalWithoutGoalkeeperAndMiddlefielderModifier);
		lul.setMiddlefieldersVariation(lineUp.getMiddlefieldersVariation());
		
		lul.setGoalkeeperModifier(lineUp.getGoalkeeperModifier());

		
		
		return lul;

	}

	private static void calculareRealLineUps(List<LineUp> linesUp) {

		for (LineUp lineUp : linesUp) {
			List<PlayerVote> finalLineUp = new ArrayList<PlayerVote>();
			List<PlayerVote> substitutionCandidates = new ArrayList<PlayerVote>();

			addPlayerVoteForRole(finalLineUp, lineUp.getGoalKeeper(), lineUp.getReserves(),
					RoleEnum.P,  substitutionCandidates);

			addPlayerVoteForRole(finalLineUp, lineUp.getDefenders(), lineUp.getReserves(),
					RoleEnum.D,  substitutionCandidates);

			addPlayerVoteForRole(finalLineUp, lineUp.getMidfielders(), lineUp.getReserves(),
					RoleEnum.C, substitutionCandidates);

			addPlayerVoteForRole(finalLineUp, lineUp.getStrikers(), lineUp.getReserves(),
					RoleEnum.A, substitutionCandidates);

			List<PlayerVote> orderedSubstiturionCandidate = new ArrayList<PlayerVote>();
			for (PlayerVote reserve : lineUp.getReserves()){
				for (PlayerVote candidates : substitutionCandidates){
					if (reserve.getName().equals(candidates.getName())){
						orderedSubstiturionCandidate.add(candidates);
						break;
					}
				}
			}
			
			for (PlayerVote candidates : substitutionCandidates){
				if (candidates.getName() == null){
					orderedSubstiturionCandidate.add(candidates);
				}
			}
			
			
			for (int i = 0; i < orderedSubstiturionCandidate.size(); i++) {
				PlayerVote subCandidate = orderedSubstiturionCandidate.get(i);
				if (subCandidate.getName() == null || i > rules.getSubstitutions().getSubstitutionNumber()-1 ){
					createOfficePlayer(i, subCandidate);
				} 
				finalLineUp.add(subCandidate);
			}
			
			lineUp.setFinalLineUp(finalLineUp);
		}

	}





	private static void createOfficePlayer(int i, PlayerVote subCandidate) {
		if (i <=  rules.getSubstitutions().getSubstitutionNumber() - 1 || AppConstants.OFFICE_RESERVERS_TILL_11) {
			subCandidate.setName("OFFICE");
			subCandidate.setTeam("OFFICE");
			if (subCandidate.getRole().equals(RoleEnum.P)){
				subCandidate.setVote(rules.getSubstitutions().getGoalkeeperPlayerOfficeVote());
				subCandidate.setFantaVote(rules.getSubstitutions().getGoalkeeperPlayerOfficeVote());
			}
			else {
				subCandidate.setVote(rules.getSubstitutions().getMovementsPlayerOfficeVote());
				subCandidate.setFantaVote(rules.getSubstitutions().getMovementsPlayerOfficeVote());
			}
		}
		else {
			subCandidate.setName("ZERO");
			subCandidate.setTeam("ZERO");
			subCandidate.setVote(0.0);
			subCandidate.setFantaVote(0.0);
		}
	}

	private static void addPlayerVoteForRole(List<PlayerVote> finalLineUp, List<PlayerVote> roleSet,
			List<PlayerVote> reserves, RoleEnum role, List<PlayerVote> substitutionCandidates) {
		PlayerVote playerVoteToAdd;
		for (PlayerVote pv : roleSet) {
			playerVoteToAdd = pv;
			if (playerVoteToAdd.getVote() != null){
				finalLineUp.add(playerVoteToAdd);
			}
			else {
				//Ricerca in panchina 
				playerVoteToAdd = getReserveForPlayer(playerVoteToAdd.getRole(), reserves);
				//Inserimento di ufficio o niente
				if (playerVoteToAdd == null) {
					playerVoteToAdd = new PlayerVote(role, null, null, null, null);
					playerVoteToAdd.setStrikerModifier(0.0);
					playerVoteToAdd.setGoalkeerModifier(0.0);
				}
				substitutionCandidates.add(playerVoteToAdd);
			}
		}
	}

	private static PlayerVote getReserveForPlayer(RoleEnum role, List<PlayerVote> reserves) {
		PlayerVote playerVoteToAdd = null;
		for (PlayerVote reserve : reserves){
			if (reserve.getRole().equals( role )){
				if (!reserve.isAlreadyUsed()){
					playerVoteToAdd = reserve;
					if (playerVoteToAdd.getVote() != null) {
						reserve.setAlreadyUsed(true);
						break;
					}
					else {
						reserve.setAlreadyUsed(true);
					}
				}
			}
		}
		if (playerVoteToAdd != null && playerVoteToAdd.getVote() == null)
			playerVoteToAdd = null;
		return playerVoteToAdd;
	}

	private static LineUp createLineUp(Element lineUpDomElement, String teamName, String serieASeasonDay) {
		LineUp lineUp = new LineUp();
		
		lineUp.setTeamName(teamName);
		
		Elements playersElem = lineUpDomElement.getElementsByClass("playerRow");
		PlayerVote playerVote;
		
		boolean reserveSection = false;

		for (int i = 0; i < playersElem.size(); i++) {
			Element playerElem = playersElem.get(i);
			playerVote = getPlayer(playerElem, serieASeasonDay);
			if ( i > 10 )
				reserveSection = true;
		
			switch (playerVote.getRole()) {
			case P:
				if (!reserveSection)
					lineUp.getGoalKeeper().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
				break;
			case D:
				if (!reserveSection)
					lineUp.getDefenders().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
				break;
			case C:
				if (!reserveSection)
					lineUp.getMidfielders().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
				break;
			case A:
				if (!reserveSection)
					lineUp.getStrikers().add(playerVote);
				else
					lineUp.getReserves().add(playerVote);
				break;

			default:
				break;
			}
		} 
		// Middlefield Modifier non è calcolabile qui' ma solo a match in corso
		if (rules.getModifiers().isGoalkeeperModifierActive()){
			if (!AppConstants.FORCE_GOALKEEPER_MODIFIER_DISABLED){
				Elements goalkeeperModifierDomElement = lineUpDomElement.getElementsMatchingOwnText("Modificatore portiere:");
				Double goalkeeperModifierFromWeb = goalkeeperModifierDomElement.isEmpty() ? 0 : getVote(goalkeeperModifierDomElement.get(0).siblingNodes().get(0).childNode(0).toString());
				lineUp.setGoalkeeperModifier(goalkeeperModifierFromWeb);
			}
		}
		if (rules.getModifiers().isStrikerModifierActive()){
			if (!AppConstants.FORCE_STRIKER_MODIFIER_DISABLED){
				Elements strickerModifierDomElement = lineUpDomElement.getElementsMatchingOwnText("Modificatore attacco:");
				Double strikerModifierFromWeb = strickerModifierDomElement.isEmpty() ? 0 : getVote(strickerModifierDomElement.get(0).siblingNodes().get(0).childNode(0).toString());
				lineUp.setStrickerModifier(strikerModifierFromWeb);
			}
		}
		if (rules.getModifiers().isDefenderModifierActive()){
			if (!AppConstants.FORCE_DEFENDER_MODIFIER_DISABLED){
				Elements defenderModifiersDomElement = lineUpDomElement.getElementsMatchingOwnText("Modificatore difesa:");
				Double defenderModifierFromWeb = defenderModifiersDomElement.isEmpty() ? 0 : getVote(defenderModifiersDomElement.get(0).siblingNodes().get(0).childNode(0).toString());
				lineUp.setDefenderModifier(defenderModifierFromWeb);
			}
		}
		//System.out.println(lineUp);
		return lineUp;
	}

	private static PlayerVote getPlayer(Element playerElem, String serieASeasonDay) {
		
		RoleEnum role = RoleEnum.valueOf(playerElem.getElementsByClass("myhidden-xs").get(0).text());
		String name = playerElem.getElementsByClass("sh").get(0).text().toUpperCase();
		name = name.replace(" *", "");
		String team = playerElem.getElementsByClass("pt").get(0).text().toUpperCase();
		PlayerVoteComplete pvcVote = null;
		Double vote = null;
		Double fantaVote = null;
		
		pvcVote = getVoteFromMap(team, name, seasonDayVotes.get(team));

		 
		// Controllo se lo devo forzare a 6
		if (pvcVote == null){
			ArrayList<String> forcedTeams = force6map.get(serieASeasonDay);
			if (forcedTeams != null)
				 if (forcedTeams.contains(team))
					 pvcVote = new PlayerVoteComplete(name, team, role, 6.0, false, false, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, false, false, false, false);
			
		}
	
		
		//Se non ha giocato non proseguo con i calcoli successivi
		if (pvcVote == null){		
			return new PlayerVote(role, name, team, null, null);
		}	
		
		vote = pvcVote.getVote();
		fantaVote = getFantaVote(pvcVote);
		
		Double voteFromWeb = getVote(playerElem.getElementsByClass("pt").get(1).text());
		if (vote != null && !vote.equals(voteFromWeb)){
			System.out.println("VOTO DIVERSO: " + team + " " + name + " " + "voti ufficiali: " + vote + " - Voti da nostra lega: " + voteFromWeb);
		}
		
		
		Double fantaVoteFromWeb = getVote(playerElem.getElementsByClass("pt").get(2).text());
		if (fantaVote != null && !fantaVote.equals(fantaVoteFromWeb)){
			System.out.println("FANTAVOTO DIVERSO: " + team + " " + name + " " + "voti ufficiali: " + fantaVote + " - Voti da nostra lega: " + fantaVoteFromWeb);
		}
		
		PlayerVote p = new PlayerVote(role, name, team, vote, fantaVote);
		
		
		if (role.equals(RoleEnum.P)){
			if (rules.getModifiers().isGoalkeeperModifierActive()){
				Double goalkeeperModifier = calculateGoalkeeperModifier(pvcVote); 
				p.setGoalkeerModifier(goalkeeperModifier);
			}
		} 
		else if (role.equals(RoleEnum.A)){
			if (rules.getModifiers().isStrikerModifierActive()){ 
				Double strikerModifier = calculateStrikerModifier(pvcVote);
				p.setStrikerModifier(strikerModifier);
			}
		}
		
		return p;
	}
	
	private static PlayerVoteComplete getVoteFromMap (String team, String name, List<PlayerVoteComplete> playerVoteCompleteList) {
		for (PlayerVoteComplete playerVoteComplete : playerVoteCompleteList){
			if (playerVoteComplete.getName().equals(name)){
				return playerVoteComplete;
			}
		}
		return null;
	}

//	private static PlayerVoteComplete getFantaVoteFromMap(String team, String name, List<PlayerVoteComplete> list) {
//		for (PlayerVoteComplete playerVoteComplete : list){
//			if (playerVoteComplete.getName().equals(name)){
//				if (playerVoteComplete.getVote() != null){
//					return getFantaVote(playerVoteComplete);
//				}
//			}
//		}
//		return null;
//	}

	private static Double getFantaVote(PlayerVoteComplete p) {
		BonusMalus bonusMalus = rules.getBonusMalus();
		if (p.getVote() == null)
			return null;
		Double fantaVote = p.getVote() + 
							(p.getRedCard() ? 			bonusMalus.getRedCard().get(p.getRole()) 			: 0) +
							(p.getYellowCard() ? 		bonusMalus.getYellowCard().get(p.getRole()) 		: 0) + 
							(p.getScoredGoals() * 		bonusMalus.getScoredGoal().get(p.getRole())) + 
							(p.getScoredPenalties() * 	bonusMalus.getScoredPenalty().get(p.getRole())) + 
							(p.getMovementAssists() * 	bonusMalus.getMovementAssist().get(p.getRole())) + 
							(p.getStationaryAssists() * bonusMalus.getStationaryAssist().get(p.getRole())) +
							(p.getAutogoals() * 		bonusMalus.getAutogoal().get(p.getRole())) +
							(p.getMissedPenalties() * 	bonusMalus.getMissedPenalty().get(p.getRole())) + 
							(p.getSavedPenalties() * 	bonusMalus.getSavedPenalty().get(p.getRole())) +
							(p.getTakenGoals() * 		bonusMalus.getTakenGoal().get(p.getRole())) + 
							(p.getEvenGoal() ?  		bonusMalus.getEvenGoal().get(p.getRole()) 			: 0) + 
							(p.getWinGoal() ? 	 		bonusMalus.getWinGoal().get(p.getRole()) 			: 0);
		
		if (rules.getPoints().isPortiereImbattutoActive()){
			if (p.getRole().equals(RoleEnum.P)){
				if (p.getTakenGoals() == 0.0){
					fantaVote += rules.getPoints().getPortiereImbattuto();
				}
			}
		}
		
		return fantaVote;
	}

	private static Double getVote(String voteString) {
		Double vote = null;
		if (!voteString.equals("-"))
			vote = Double.valueOf(voteString.replace(",", "."));
		return vote;
	}
	

	
}