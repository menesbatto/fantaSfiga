package fantapianto._0_realChampionshipVotesDownloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fantapianto._0_realChampionshipVotesDownloader.model.PlayerVoteComplete;
import fantapianto._0_realChampionshipVotesDownloader.model.VotesSourceEnum;
import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

public class MainSeasonVotesDowloader {

	public static void main(String args[]) {
		execute();
	}
	
	public static Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> execute(){
		
		Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> seasonVotesMap = null;
			
			System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
			System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
			populateTeamIds();

			seasonVotesMap = retrieveAllVotesFromFile();
			
			String finalSeasonDayVotesUrl;
			Integer lastSeasonDayCalculated = seasonVotesMap.get(VotesSourceEnum.MILANO).keySet().size();
			
//			napoli	1 	ata		sportiello, caldara, toloi
//						bol		antonioli, ...
//						cro		ferrari, rosi, ....
//					2
//					
//			milano 	1 	ata
//			
//			italia 	1 	ata
			
			
			String tvStamp = getTVUrlParameter();
			Integer serieAActualSeasonDay = MainSeasonAnalyzerFINAL.getSerieAActualSeasonDay();
			for (int i = lastSeasonDayCalculated + 1; i <= serieAActualSeasonDay; i++) {
				System.out.print(i + "\t");
				finalSeasonDayVotesUrl = AppConstants.SEASON_DAY_VOTES_URL_TEMPLATE.replace("[SEASON_DAY]", i+"").replace("[DATE_TIME_MILLIS]", tvStamp+"");
				Map<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>> trisVote = DaySeasonVotesDowloader.execute(finalSeasonDayVotesUrl);
				
				Map<String, List<PlayerVoteComplete>> singleVotes = trisVote.get(VotesSourceEnum.NAPOLI);
				seasonVotesMap.get(VotesSourceEnum.NAPOLI).put(i+"", singleVotes);
				
				Map<String, List<PlayerVoteComplete>> singleVotes1 = trisVote.get(VotesSourceEnum.MILANO);
				seasonVotesMap.get(VotesSourceEnum.MILANO).put(i+"", singleVotes1);

				Map<String, List<PlayerVoteComplete>> singleVotes2 = trisVote.get(VotesSourceEnum.ITALIA);
				seasonVotesMap.get(VotesSourceEnum.ITALIA).put(i+"", singleVotes2);
				
			}
			
			String realChampionshipVotes = AppConstants.REAL_CHAMPIONSHIP_VOTES_DIR + AppConstants.REAL_CHAMPIONSHIP_VOTES_FILE_NAME;
			IOUtils.write(realChampionshipVotes, seasonVotesMap);

		
		return seasonVotesMap;
	}
	
	

	
	private static String getTVUrlParameter() {
		String tvStamp = null;
		Connection connect =  Jsoup.connect(AppConstants.SEASON_DAY_ALL_VOTES_URL + "/1");
		try {
			Document doc = connect.ignoreContentType(true).get();
			tvStamp = doc.select("#tvstamp").val();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tvStamp;
		
	}


	private static Map<VotesSourceEnum,Map<String, Map<String, List<PlayerVoteComplete>>>> retrieveAllVotesFromFile() {
		Map<VotesSourceEnum,Map<String, Map<String, List<PlayerVoteComplete>>>> map = IOUtils.read(AppConstants.REAL_CHAMPIONSHIP_VOTES_DIR	+ AppConstants.REAL_CHAMPIONSHIP_VOTES_FILE_NAME, HashMap.class);
		if (map== null){
			map = new HashMap<VotesSourceEnum, Map<String,Map<String,List<PlayerVoteComplete>>>>();
			map.put(VotesSourceEnum.NAPOLI, new HashMap<String, Map<String,List<PlayerVoteComplete>>>());
			map.put(VotesSourceEnum.MILANO, new HashMap<String, Map<String,List<PlayerVoteComplete>>>());
			map.put(VotesSourceEnum.ITALIA, new HashMap<String, Map<String,List<PlayerVoteComplete>>>());
		}
		return map;
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
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		String realChampionshipTeamsIds = AppConstants.REAL_CHAMPIONSHIP_TEAMS_IDS_DIR + AppConstants.REAL_CHAMPIONSHIP_TEAMS_IDS_FILE_NAME;
		IOUtils.write(realChampionshipTeamsIds, map);
		System.out.println(map);

		
		return map;
	}
	
	
	public static Map<VotesSourceEnum,Map<String, Map<String, List<PlayerVoteComplete>>>> getAllVotes() {
		return retrieveAllVotesFromFile();
	}	
	
	
	public static Map<String, String> getTeamsIds() {
		Map<String, String> map = IOUtils.read(AppConstants.REAL_CHAMPIONSHIP_TEAMS_IDS_DIR
				+ AppConstants.REAL_CHAMPIONSHIP_TEAMS_IDS_FILE_NAME, HashMap.class);

		return map;
	}
	
}