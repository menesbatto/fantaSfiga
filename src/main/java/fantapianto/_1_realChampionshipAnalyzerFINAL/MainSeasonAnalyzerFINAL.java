package fantapianto._1_realChampionshipAnalyzerFINAL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fantapianto._00_fantaChampionshipRulesExtractor.RulesExpertMain;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Rules;
import fantapianto._0_realChampionshipVotesDownloader.MainSeasonVotesDowloader;
import fantapianto._0_realChampionshipVotesDownloader.model.PlayerVoteComplete;
import fantapianto._0_realChampionshipVotesDownloader.model.VotesSourceEnum;
import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

public class MainSeasonAnalyzerFINAL {

	static Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> seasonVotesMap;
	static Rules rules;
	static String competitionId = getCompetitionId();
	static Integer fantacalcioStartingSerieASeasonDay;
	static Integer seriaAActualSeasonDay;
	static ArrayList<Integer> serieAseasonToWait;
	private static Map<Integer, Integer> seasonDayBind;

	
	public static ArrayList<SeasonDayResult> execute(Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> seasonVotesMapInput){
		seasonVotesMap = seasonVotesMapInput;
		initStaticFields();
		
		ArrayList<SeasonDayResult> seasonDayResults = null;
		try {
			if (AppConstants.PROXY_ACTIVE){
				System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
				System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
			}
			String finalSeasonDayUrl = AppConstants.SEASON_DAY_LINES_UP_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionId);
			
			Integer fantacalcioSeasonDaysNumber = calculateFantaSeasonDayForSerieASSeasonDay(seriaAActualSeasonDay);//seriaAActualSeasonDay - fantacalcioStartingSerieASeasonDay + 1;
			System.out.println(fantacalcioSeasonDaysNumber);
			
			seasonDayResults = new ArrayList<SeasonDayResult>();
			SeasonDayResult seasonDayResult;
			for (int i = 1; i <= fantacalcioSeasonDaysNumber; i++) {
				if (serieAseasonToWait.contains(i - 1 + fantacalcioStartingSerieASeasonDay)){
					seasonDayResults.add(null);
					continue;
				}
				VotesSourceEnum voteSource = rules.getDataSource().getVotesSource();
				if  (AppConstants.FORCE_VOTE_SOURCE != null){
					voteSource = AppConstants.FORCE_VOTE_SOURCE;
				}
				
				Map<String, Map<String, List<PlayerVoteComplete>>> map = null;
				map = seasonVotesMap.get(voteSource);
				String key = calculateSerieASeasonDayForFantaSeasonDay(i);
//				String key = i + fantacalcioStartingSerieASeasonDay - 1 + "";
				
				
				seasonDayResult = DaySeasonAnalyzerFINAL.execute(finalSeasonDayUrl + i, map.get(key), key ) ;
				
				seasonDayResults.add(seasonDayResult);

			}
			//System.out.println(seasonDayResults);
			String finalResultSeasonPath= AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME;
			IOUtils.write(finalResultSeasonPath, seasonDayResults);

		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return seasonDayResults;
	}
	
	private static Integer calculateFantaSeasonDayForSerieASSeasonDay( Integer serieATot) {
		//	1 - 4		//	5 - 9 		//	10 - 15		//	15 - 21		//	20 - 27
		//	2 - 5		//	6 - 10		//	11 - 16 	//	16 - 22		//	21 - 28
		//	3 - 6		//	7 - 11		//	12 - 17 	//	17 - 23		//	22 - 29
		//	4 - 7		//	8 - 12		//	13 - 18 	//	18 - 24		//	23 - 30
						//	9 - 13 		//	14 - 19		//	19 - 25		//	24 - 31
		
		for (Entry<Integer, Integer> entry : seasonDayBind.entrySet()){
			if (entry.getValue().equals(serieATot))
				return entry.getKey();
		}
		
		
		return null;
	}

	private static String calculateSerieASeasonDayForFantaSeasonDay(int i) {
		
		Integer fantaSeasonDay = seasonDayBind.get(i);
		
		return fantaSeasonDay+"";
//		String key = i + fantacalcioStartingSerieASeasonDay - 1 + "";
//		
//		
//		Integer keyNumber = (fantacalcioStartingSerieASeasonDay + i - 1) + ((i - 1) / 5);
//		// -1 perche si parla di indici
//		return keyNumber+"";
	}

	private static void createSeasonDayBinging() {
		seasonDayBind = new HashMap<Integer, Integer>();
		seasonDayBind.put(1, 4);
		seasonDayBind.put(2, 5);
		seasonDayBind.put(3, 6);
		seasonDayBind.put(4, 7);
		
		seasonDayBind.put(5, 9);
		seasonDayBind.put(6, 10);
		seasonDayBind.put(7, 11);
		seasonDayBind.put(8, 12);
		seasonDayBind.put(9, 13);
		
		seasonDayBind.put(10, 15);
		seasonDayBind.put(11, 16);
		seasonDayBind.put(12, 17);
		seasonDayBind.put(13, 18);
		seasonDayBind.put(14, 19);
		
		seasonDayBind.put(15, 21);
		seasonDayBind.put(16, 22);
		seasonDayBind.put(17, 23);
		seasonDayBind.put(18, 24);
		seasonDayBind.put(19, 25);
		
		seasonDayBind.put(20, 27);
		seasonDayBind.put(21, 28);
		seasonDayBind.put(22, 29);
		seasonDayBind.put(23, 30);
		seasonDayBind.put(24, 31);
		
		seasonDayBind.put(25, 33);
		seasonDayBind.put(26, 34);
		seasonDayBind.put(27, 35);
		seasonDayBind.put(28, 36);
	}

	public static void main(String args[]) {
		execute(null);
	}

	
	private static void initStaticFields() {
		if (seasonVotesMap == null)
			seasonVotesMap =  MainSeasonVotesDowloader.getAllVotes();
		
		rules = RulesExpertMain.getRules();
		seriaAActualSeasonDay = getSerieAActualSeasonDay();
		fantacalcioStartingSerieASeasonDay = getFantacalcioStartingSerieASeasonDay();
		serieAseasonToWait = getSeasonDaysToWait();
		createSeasonDayBinging();

	}


	public static Integer getSerieAActualSeasonDay() {
		if (seriaAActualSeasonDay == null) {
			String finalCalendarUrl = AppConstants.CALENDAR_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionId);
			Document doc;
			String result = "";
			try {
				doc = Jsoup.connect(finalCalendarUrl).get();
				String[] split = doc.select("tfoot a").last().parents().get(3).getElementsByTag("h4").toString().split("-");
				String temp = split[1].split(" ")[1];
				result = temp.substring(0, temp.length()-1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			seriaAActualSeasonDay = Integer.valueOf(result);
		}
		return seriaAActualSeasonDay;
	}
	
	public static Integer getFantacalcioStartingSerieASeasonDay() {
		if (fantacalcioStartingSerieASeasonDay == null){
			String finalCalendarUrl = AppConstants.CALENDAR_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionId);
			Document doc;
			String result = "";
			try {
				doc = Jsoup.connect(finalCalendarUrl).get();
				String[] split = doc.select("th h4").get(0).toString().split("-");
				String temp = split[1].split(" ")[1];
				result =  temp.substring(0, temp.length()-1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fantacalcioStartingSerieASeasonDay = Integer.valueOf(result);
		}
		return fantacalcioStartingSerieASeasonDay;
	}

	public static ArrayList<Integer> getSeasonDaysToWait() {
		if (serieAseasonToWait == null){
			serieAseasonToWait = new ArrayList<Integer>();
			String competitionId = getCompetitionId();
			String finalCalendarUrl = AppConstants.CALENDAR_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionId);
			Document doc;
			try {
				doc = Jsoup.connect(finalCalendarUrl).get();
				Elements seasonDayElements = doc.getElementsByTag("table");
				for (int i = 0; i < seasonDayElements.size(); i++) {
					Element lineUpElem = seasonDayElements.get(i);
					Elements matchesDomElems = lineUpElem.getElementsByClass("match"); 
					for (Element matchElem : matchesDomElems) {
						Elements resultPoints = matchElem.getElementsByClass("point");
						if (resultPoints.size() == 0){
							String serieAseasonDayString = lineUpElem.getElementsByClass("thtitle").text().split("-")[1].split(" ")[1];
							String serieAseasonDayString1 = serieAseasonDayString.substring(0, serieAseasonDayString.length()-1);
							Integer serieAseasonDay = Integer.valueOf(serieAseasonDayString1);
							serieAseasonToWait.add(serieAseasonDay);
							break;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return serieAseasonToWait;
	}


	private static String getCompetitionId() {
		// Vado sul calendario e prendo i due indirizzi
		// Provo il primo 
		//		Se mi torna che ha una scritta del tipo
		// 		"questo tipo di competizione non prevede un calendario."
		//		Allora è di tipo racing e non ci posso fare niente quindi pwe le formazioni devo scegliere l'altro
		//
		//		Se mi torna che NON ha una scritta del tipo
		// 		"questo tipo di competizione non prevede un calendario."
		//		Allora per le formazioni devo scegliere questo
				
		
		//http://leghe.fantagazzetta.com/accaniti-division/formazioni/143826?g=1
		if (competitionId == null){
			if (AppConstants.PROXY_ACTIVE){
				System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
				System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
			}
			
			String calendarUrlDefault = AppConstants.CALENDAR_URL_TEMPLATE.replace("[COMPETITION_ID]", "");
			String competitionIdCurrent = null;
			String calendarCompetitionUrl;
			try {
				Document doc = Jsoup.connect(calendarUrlDefault).get();
				Elements select = doc.select("option");
				for (Element s :select){
					if (s.text().equalsIgnoreCase(AppConstants.COMPETITION_NAME)){
						calendarCompetitionUrl= s.val();
						competitionIdCurrent = calendarCompetitionUrl.substring(calendarCompetitionUrl.lastIndexOf("/")+1);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			competitionId = competitionIdCurrent;
		}
		
		return competitionId;
	}

	public static ArrayList<SeasonDayResult> getRealChampionshipResults() {
		ArrayList<SeasonDayResult> realChampionshipResults = IOUtils.read(AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME, ArrayList.class);
		return realChampionshipResults;
	}
}