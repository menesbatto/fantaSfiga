package fantapianto._1_realChampionshipAnalyzerNEW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

public class MainSeasonAnalyzerNEW {


	
	public static void main(String args[]) {
		readAllSheet();
	}

	private static void readAllSheet() {

		try {
			if (AppConstants.PROXY_ACTIVE){
				System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
				System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
			}
			
			String competitionId = getCompetitionId();
			Integer fantacalcioSeasonDaysNumber = AppConstants.SERIE_A_ACTUAL_SEASON_DAY - AppConstants.FANTACALCIO_STARTING_SERIE_A_SEASON_DAY + 1;
			System.out.println(fantacalcioSeasonDaysNumber);
			String finalSeasonDayUrl;
			List<SeasonDayResult> seasonDayResults = new ArrayList<SeasonDayResult>();
			SeasonDayResult seasonDayResult;
			for (int i = 1; i <= fantacalcioSeasonDaysNumber; i++) {
//			for (int i = 1; i <= 1; i++) {
				finalSeasonDayUrl = AppConstants.SEASON_DAY_LINES_UP_URL_TEMPLATE.replace("[COMPETITION_ID]", competitionId) + i;
				if (AppConstants.SERIE_A_SEASON_DAYS_TO_WAIT.contains(i - 1 + AppConstants.FANTACALCIO_STARTING_SERIE_A_SEASON_DAY)){
//				if (AppConstants.SEASON_DAYS_TO_WAIT.contains(i)){
					seasonDayResults.add(null);
					continue;
				}
				seasonDayResult = DaySeasonAnalyzerNEW.execute(finalSeasonDayUrl);
				seasonDayResults.add(seasonDayResult);
				System.out.println(seasonDayResults);

			}
			String finalResultSeasonPath= AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME;
			IOUtils.write(finalResultSeasonPath, seasonDayResults);
			System.out.println(seasonDayResults);

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
	
	private static String getCompetitionId() {
		// Vado usl calendario e prendo i due indirizzi
		// Provo il primo 
		//		Se mi torna che ha una scritta del tipo
		// 		"questo tipo di competizione non prevede un calendario."
		//		Allora è di tipo racing e non ci posso fare niente quindi pwe le formazioni devo scegliere l'altro
		//
		//		Se mi torna che NON ha una scritta del tipo
		// 		"questo tipo di competizione non prevede un calendario."
		//		Allora per le formazioni devo scegliere questo
				
		
		//http://leghe.fantagazzetta.com/accaniti-division/formazioni/143826?g=1
		
		String calendarUrlDefault = AppConstants.CALENDAR_URL_TEMPLATE.replace("[COMPETITION_ID]", "");
		String competitionId = null;
		String calendarCompetitionUrl;
		try {
			Document doc = Jsoup.connect(calendarUrlDefault).get();
			Elements select = doc.select("option");
			for (Element s :select){
				if (s.text().equalsIgnoreCase(AppConstants.COMPETITION_NAME)){
					calendarCompetitionUrl= s.val();
					competitionId = calendarCompetitionUrl.substring(calendarCompetitionUrl.lastIndexOf("/")+1);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return competitionId;

	}
	
}