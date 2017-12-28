package fantapianto._0_realChampionshipVotesDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import fantapianto._0_realChampionshipVotesDownloader.model.PlayerVoteComplete;
import fantapianto._0_realChampionshipVotesDownloader.model.VotesSourceEnum;
import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;
import fantapianto.utils.UsefulMethods;

public class DaySeasonVotesDowloader {

	private static Map<String, String> teamIds = MainSeasonVotesDowloader.getTeamsIds();

	
	
	
	public static Map<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>> execute(String seasonDayVotesUrl) {

		Map<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>> trisVotes = new HashMap<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>>();
		Map<String, List<PlayerVoteComplete>> votes1 = new HashMap<String, List<PlayerVoteComplete>>();
		Map<String, List<PlayerVoteComplete>> votes2 = new HashMap<String, List<PlayerVoteComplete>>();
		Map<String, List<PlayerVoteComplete>> votes3 = new HashMap<String, List<PlayerVoteComplete>>();
		try {

			// Recupero i voti di giornata
			String seasonDayVotesUrlFinal;
			Document doc;
			List<PlayerVoteComplete> teamPlayersVotes = null;
			Connection connect;
			String gazzettaTeamId;
			
//			int a = 0;
//			do {
//				try {
					for (String teamShortName : teamIds.keySet()) {
						//seasonDayVotesUrlFinalhttps =//www.fantagazzetta.com/Servizi/Voti.ashx?s=2016-17&g=1&tv=225959671391&t=8
						//https://www.fantagazzetta.com/Servizi/Voti.ashx?s=2016-17&g=1&tv=225959671391&t=22
						gazzettaTeamId = teamIds.get(teamShortName);
						seasonDayVotesUrlFinal = seasonDayVotesUrl.replace("[GAZZETTA_TEAM_ID]", gazzettaTeamId);
						connect = Jsoup.connect(seasonDayVotesUrlFinal);
						doc = connect.ignoreContentType(true).get();
						
						teamPlayersVotes = getTeamPlayersVotes(doc, teamShortName, VotesSourceEnum.NAPOLI);
						votes1.put(teamShortName, teamPlayersVotes);
						trisVotes.put(VotesSourceEnum.NAPOLI, votes1);
						
						teamPlayersVotes = getTeamPlayersVotes(doc, teamShortName, VotesSourceEnum.MILANO);
						votes2.put(teamShortName, teamPlayersVotes);
						trisVotes.put(VotesSourceEnum.MILANO, votes2);
						
						teamPlayersVotes = getTeamPlayersVotes(doc, teamShortName, VotesSourceEnum.ITALIA);
						votes3.put(teamShortName, teamPlayersVotes);
						trisVotes.put(VotesSourceEnum.ITALIA, votes3);
						
						
						
						
						System.out.print(".");
//						a = 0;
					
					}
//				}
//				catch (Exception e){
//					System.out.println(e);
//					a++;
//					System.out.println("tentativo " + a + " fallito");
//				}
//			}  
//			while ( a > 0 && a < 3);

			System.out.println(); 
		
		} catch (Exception e) {
			System.out.println(e);
		}

		return trisVotes;

	}

	

	private static List<PlayerVoteComplete> getTeamPlayersVotes(Document doc, String teamShortName, VotesSourceEnum source) {
		Elements elements = doc.select("td.pname");
		Node cardNode;
		String playerName, roleString, scoredGoalString, scoredPenaltiesString, takenGoalsString, savedPenaltiesString, missedPenaltiesString, autoGoalsString, assistTotalString, assistStationaryString;
		Double scoredGoal, scoredPenalties, takenGoals, savedPenalties, missedPenalties, autoGoals, assistTotal, assistStationary, assistMovement;
		Boolean winGoal = false, evenGoal = false, subIn = false, subOut = false;
		List<Node> siblingNodes;
		PlayerVoteComplete pv;
		List<PlayerVoteComplete> playerVoteCompleteList = new ArrayList<PlayerVoteComplete>();
		for (Element e : elements) {
			winGoal = false;
			evenGoal = false;
			subIn = false;
			subOut = false;
			Elements nameElement = e.getElementsByTag("a");
			playerName = nameElement.text();
			roleString = nameElement.parents().get(1).getElementsByClass("role").text();
			RoleEnum role = null;
			try {
				role = RoleEnum.valueOf(roleString);
			} catch (Exception ex) {
				continue;
			}

			Elements generalInfoElems = e.getElementsByClass("aleft");
			if (generalInfoElems.size() > 0) {
				Elements generalEvents = generalInfoElems.get(0).getElementsByTag("em");
				//e.getElementsByClass("pull-right").get(0);
				for (Element ne : generalEvents) {
					String text = ne.text();
					if (text.equals("V"))
						winGoal = true;
					else if (text.equals("P")) {
						evenGoal = true;
					}
					String className = ne.className();
					if (className.contains("fa-arrow-right"))
						subOut = true;
					if (className.contains("fa-arrow-left"))
						subIn = true;
				}
			}
			siblingNodes = e.siblingNodes();
			Integer sc = 0;
			
			switch (source) {
				case NAPOLI:	sc = 0*2;	break;
				case MILANO:	sc = 1*2;	break;
				case ITALIA:	sc = 2*2;	break;
				default:		sc = 0*2; 			}

			Double vote;
			
			Boolean isVoteSV = siblingNodes.get(1 + sc).childNode(0).attr("class").indexOf("label-lgrey") > 0;
			if (isVoteSV){
				vote = null;
			}
			else {
				String voteString = siblingNodes.get(1 + sc).childNode(0).childNode(0).toString();
				vote = UsefulMethods.getNumber(voteString);
			}
			Boolean yellow = false;
			Boolean red = false;

			if (siblingNodes.get(1 + sc).childNodes().size() > 1) {
				cardNode = siblingNodes.get(1 + sc).childNode(1);
				yellow = cardNode.attr("class").contains("trn-ry");
				red = cardNode.attr("class").contains("trn-rr");
			}

			Node scoredGoalsNode = siblingNodes.get(7).childNode(0);
			scoredGoalString = scoredGoalsNode.childNodeSize() > 0 ? scoredGoalsNode.childNode(0).toString() : scoredGoalsNode.toString();
			scoredGoal = UsefulMethods.getNumber(scoredGoalString);

			Node scoredPenaltiesNode = siblingNodes.get(8).childNode(0);
			scoredPenaltiesString = scoredPenaltiesNode.childNodeSize() > 0 ? scoredPenaltiesNode.childNode(0).toString() : scoredPenaltiesNode.toString();
			scoredPenalties = UsefulMethods.getNumber(scoredPenaltiesString);

			
			Node takenGoalsNode = siblingNodes.get(9).childNode(0);
			takenGoalsString = takenGoalsNode.childNodeSize() > 0 ? takenGoalsNode.childNode(0).toString() : takenGoalsNode.toString();
			takenGoals = UsefulMethods.getNumber(takenGoalsString);

			Node savedPenaltieslsNode = siblingNodes.get(10).childNode(0);
			savedPenaltiesString = savedPenaltieslsNode.childNodeSize() > 0 ? savedPenaltieslsNode.childNode(0).toString() : savedPenaltieslsNode.toString();
			savedPenalties = UsefulMethods.getNumber(savedPenaltiesString);

			Node missedPenaltiesNode = siblingNodes.get(11).childNode(0);
			missedPenaltiesString = missedPenaltiesNode.childNodeSize() > 0 ? missedPenaltiesNode.childNode(0).toString() : missedPenaltiesNode.toString();
			missedPenalties = UsefulMethods.getNumber(missedPenaltiesString);

			Node autoGoalsNode = siblingNodes.get(12).childNode(0);
			autoGoalsString = autoGoalsNode.childNodeSize() > 0 ? autoGoalsNode.childNode(0).toString() : autoGoalsNode.toString();
			autoGoals = UsefulMethods.getNumber(autoGoalsString);

			Node assistTotalNode = siblingNodes.get(13).childNode(0);
			assistTotalString = assistTotalNode.childNodeSize() > 0 ? assistTotalNode.childNode(0).toString() : assistTotalNode.toString();
			assistTotal = UsefulMethods.getNumber(assistTotalString);

			assistStationaryString = "0";
			assistStationary = 0.0;

			if (siblingNodes.get(13).childNode(0).childNodes().size() > 1) {
				assistStationaryString = siblingNodes.get(13).childNode(0).childNode(1).childNode(0).toString();
				assistStationary = UsefulMethods.getNumber(assistStationaryString);
			}

			assistMovement = assistTotal - assistStationary;

			pv = new PlayerVoteComplete(playerName, teamShortName, role, vote, yellow, red, scoredGoal,
					scoredPenalties, assistMovement, assistStationary, autoGoals, missedPenalties, savedPenalties,
					takenGoals, winGoal, evenGoal, subIn, subOut);
			playerVoteCompleteList.add(pv);

		}
		return playerVoteCompleteList;
	}

}