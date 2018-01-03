package fantapianto._5_seasonsExecutor;

import java.util.ArrayList;
import java.util.List;

import fantapianto._00_fantaChampionshipRulesExtractor.RulesExpertMain;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Rules;
import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;
import fantapianto._2_seasonPatternExtractor.model.Match;
import fantapianto._2_seasonPatternExtractorNEW.MainSeasonPatternExtractorNEW;
import fantapianto._6_rankingAnalizer.model.Pair;
import fantapianto.utils.AppConstants;

public class MatchExecutor {

	public static List<Pair> winOrDrewForHalfPointsList = createPairList();
	public static List<Pair> loseOrDrewForHalfPointList = createPairList();

	public static List<Pair> rankingPointsWinOrDrewForHalfPointsList = createPairList();
	public static List<Pair> rankingPointsLoseOrDrewForHalfPointList = createPairList();
	
	static Rules rules = RulesExpertMain.getRules();

	
	public static void execute(Match m) {

		
		LineUpLight homeTeamResult = m.getHomeTeamResult();
		LineUpLight awayTeamResult = m.getAwayTeamResult();

		// 1 - Calcola i punti dei voti + modificatori e casa
		Double homeSumTotalPoints = homeTeamResult.getTotalWithoutGoalkeeperAndMiddlefielderModifiers();
		Double awaySumTotalPoints = awayTeamResult.getTotalWithoutGoalkeeperAndMiddlefielderModifiers();
		
		
		// Modificatore difesa
		if (rules.getModifiers().isGoalkeeperModifierActive()){
			if (!AppConstants.FORCE_GOALKEEPER_MODIFIER_DISABLED){
				Double homeVarGoalkeeper = homeTeamResult.getGoalkeeperModifier();
				Double awayVarGoalkeeper = awayTeamResult.getGoalkeeperModifier();
				homeSumTotalPoints += awayVarGoalkeeper;
				awaySumTotalPoints += homeVarGoalkeeper;
			}
		}
		
		// Modificatore centrocampo
		if (rules.getModifiers().isMiddlefielderModifierActive()){
			if (!AppConstants.FORCE_GOALKEEPER_MODIFIER_DISABLED){
				Double homeVarMid = homeTeamResult.getMiddlefieldersVariation();
				Double awayVarMid = awayTeamResult.getMiddlefieldersVariation();
				Double varMid = homeVarMid - awayVarMid;
				
				if (varMid <= -2 || varMid >= 2){
					if (varMid >= 8){
						homeSumTotalPoints += 4;
						awaySumTotalPoints -= 4;
					} else if (varMid >= 6) {
						homeSumTotalPoints += 3;
						awaySumTotalPoints -= 3;
					} else if (varMid >= 4) {
						homeSumTotalPoints += 2;
						awaySumTotalPoints -= 2;
					} else if (varMid >= 2) {
						homeSumTotalPoints += 1;
						awaySumTotalPoints -= 1;
					} else if (varMid <= -8) {
						homeSumTotalPoints -= 4;
						awaySumTotalPoints += 4;
					} else if (varMid <= -6) {
						homeSumTotalPoints -= 3;
						awaySumTotalPoints += 3;
					} else if (varMid <= -4) {
						homeSumTotalPoints -= 2;
						awaySumTotalPoints += 2;
					} else if (varMid <= -2) {
						homeSumTotalPoints -= 1;
						awaySumTotalPoints += 1;
					} else {
						System.out.println("C'e' un errore");
					}
				}
			}
		}
		if (AppConstants.FORCE_INVERT_HOME_AWAY != null){
			if (!AppConstants.FORCE_INVERT_HOME_AWAY){
				homeSumTotalPoints = homeSumTotalPoints + 2; // Chi gioca in casa +2
			} else if (AppConstants.FORCE_INVERT_HOME_AWAY){
				awaySumTotalPoints = awaySumTotalPoints + 2; // Chi gioca fuori casa +2
			}
		}
		
		
		homeTeamResult.setSumTotalPoints(homeSumTotalPoints);
		awayTeamResult.setSumTotalPoints(awaySumTotalPoints);
		
		//2 - Calcola gol fatti 			
		Integer homeTeamGoals = calculateGoal(homeSumTotalPoints);
		Integer awayTeamGoals = calculateGoal(awaySumTotalPoints);

		if (AppConstants.FORCE_WINNING_FOR_DISTANCE){
			Double difference = homeSumTotalPoints - awaySumTotalPoints;
			if (difference < AppConstants.FORCE_WINNING_FOR_DISTANCE_POINTS &&  difference > -AppConstants.FORCE_WINNING_FOR_DISTANCE_POINTS){
				if (homeTeamGoals>awayTeamGoals)
					awayTeamGoals= homeTeamGoals;
				else 
					homeTeamGoals = awayTeamGoals;
			} 
		}
		
		homeTeamResult.setGoals(homeTeamGoals);
		awayTeamResult.setGoals(awayTeamGoals);
		
		//3 - Calcola punti in classifica guadagnati
		Integer homeRankingPoints;
		Integer awayRankingPoints;
		
		if (homeTeamGoals > awayTeamGoals){
			homeRankingPoints = 3;
			awayRankingPoints = 0;
		} else if (homeTeamGoals < awayTeamGoals){
			homeRankingPoints = 0;
			awayRankingPoints = 3;
		} else {
			homeRankingPoints = 1;
			awayRankingPoints = 1;
		}
		
		homeTeamResult.setRankingPoints(homeRankingPoints);
		awayTeamResult.setRankingPoints(awayRankingPoints);
		
		homeTeamResult.setTakenGoals(awayTeamGoals);
		awayTeamResult.setTakenGoals(homeTeamGoals);
		
		analyzeLuckyEdges(homeTeamResult, awayTeamResult, 0.5);
		analyzeLuckyEdges(homeTeamResult, awayTeamResult, 0.1);
		
		
	}

	private static void analyzeLuckyEdges(LineUpLight homeTeamResult, LineUpLight awayTeamResult, Double differencePoints) {
		Integer homeRankingPoints = homeTeamResult.getRankingPoints();
		Integer awayRankingPoints = awayTeamResult.getRankingPoints();
		Integer homeTeamGoals = homeTeamResult.getGoals();
		Integer awayTeamGoals = awayTeamResult.getGoals();
		Double awaySumTotalPoints = awayTeamResult.getSumTotalPoints();
		Double homeSumTotalPoints = homeTeamResult.getSumTotalPoints();
		if (homeTeamGoals - awayTeamGoals <= 1 && homeTeamGoals - awayTeamGoals >= -1){
			//SCULO PER HOME
			if ( rules.getPoints().getGoalPoints().contains(homeSumTotalPoints) ||rules.getPoints().getGoalPoints().contains(awaySumTotalPoints + differencePoints)){
				Double rankingPointsGainedFromHomeTeam = 0.0;
				Double rankingPointsLostFromAwayTeam = 0.0;
				if (homeRankingPoints == 3.0){
					rankingPointsGainedFromHomeTeam = 2.0;
					rankingPointsLostFromAwayTeam = 1.0;
				}
				else if (homeRankingPoints == 1.0){
					rankingPointsGainedFromHomeTeam = 1.0;
					rankingPointsLostFromAwayTeam = 2.0;
				}
				if (homeRankingPoints > 0.0){
					updateWinForHalfPointList(homeTeamResult.getTeamName(), rankingPointsGainedFromHomeTeam);
					updateLoseForHalfPointList(awayTeamResult.getTeamName(), -rankingPointsLostFromAwayTeam);
				}
			} 
			//SCULO PER AWAY
			if ( rules.getPoints().getGoalPoints().contains(awaySumTotalPoints) || rules.getPoints().getGoalPoints().contains(homeSumTotalPoints + differencePoints)){
				Double rankingPointsGainedFromAwayTeam = 0.0;
				Double rankingPointsLostFromHomeTeam = 0.0;
				if (awayRankingPoints == 3.0){
					rankingPointsGainedFromAwayTeam = 1.0;
					rankingPointsLostFromHomeTeam = 2.0;
				}
				else if (awayRankingPoints == 1.0){
					rankingPointsGainedFromAwayTeam = 1.0;
					rankingPointsLostFromHomeTeam = 2.0;
				}
				if (awayRankingPoints > 0.0){
					updateWinForHalfPointList(awayTeamResult.getTeamName(), rankingPointsGainedFromAwayTeam);
					updateLoseForHalfPointList(homeTeamResult.getTeamName(), -rankingPointsLostFromHomeTeam);
				}
			}
		}
		
	}

	private static void updateLoseForHalfPointList(String teamName, Double rankingPointsGainedFromHomeTeam) {
		for ( Pair p : loseOrDrewForHalfPointList){
			if (p.getName().equalsIgnoreCase(teamName)){
				p.setValue(p.getValue() + 1);
			}
		}
		for ( Pair p : rankingPointsLoseOrDrewForHalfPointList){
			if (p.getName().equalsIgnoreCase(teamName)){
				p.setValue(p.getValue() + rankingPointsGainedFromHomeTeam);
			}
		}
		
		
	}

	private static void updateWinForHalfPointList(String teamName, Double rankingPointsGainedFromAwayTeam) {
		for ( Pair p : winOrDrewForHalfPointsList){
			if (p.getName().equalsIgnoreCase(teamName)){
				p.setValue(p.getValue() + 1);
			}
		}
		for ( Pair p : rankingPointsWinOrDrewForHalfPointsList){
			if (p.getName().equalsIgnoreCase(teamName)){
				p.setValue(p.getValue() + rankingPointsGainedFromAwayTeam);
			}
		}
		
		
		
	}

	private static List<Pair> createPairList() {
		ArrayList<String> players = MainSeasonPatternExtractorNEW.getPlayers();		
		Pair p;
		List<Pair> list = new ArrayList<Pair>();
		for(String s: players){
			p = new Pair(s, 0.0);
			list.add(p);
		}
		return list;
	}

	private static Integer calculateGoal(Double homeTeamPoints) {
		for (int i = 0; i< rules.getPoints().getGoalPoints().size(); i++){
			if (homeTeamPoints < rules.getPoints().getGoalPoints().get(i))
				return i;
		}
//		if (homeTeamPoints<66){
//			return 0;
//		} else if (homeTeamPoints >= 66 && homeTeamPoints < 72 ){
//			return 1;
//		} else if (homeTeamPoints >= 72 && homeTeamPoints < 77 ){
//			return 2;
//		} else if (homeTeamPoints >= 77 && homeTeamPoints < 81 ){
//			return 3;
//		} else if (homeTeamPoints >= 81 && homeTeamPoints < 85 ){
//			return 4;
//		} else if (homeTeamPoints >= 85 && homeTeamPoints < 89 ){
//			return 5;
//		} else if (homeTeamPoints >= 89 && homeTeamPoints < 93 ){
//			return 6;
//		} else if (homeTeamPoints >= 93 && homeTeamPoints < 97 ){
//			return 7;
//		} else if (homeTeamPoints >= 97 && homeTeamPoints < 101 ){
//			return 8;
//		} else if (homeTeamPoints >= 101 && homeTeamPoints < 105 ){
//			return 9;
//		}
		
		return 14;
	}
	

}
