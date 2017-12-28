package fantapianto._1_realChampionshipAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

public class MainSeasonAnalyzer {


	
	public static void main(String args[]) {
		readAllSheet();
	}

	private static void readAllSheet() {

		try {
			int seasonDaysToWait = AppConstants.SEASON_DAYS_TO_WAIT != null ?  AppConstants.SEASON_DAYS_TO_WAIT .size() : 0;
			Integer seasonDaysNumber = new File(AppConstants.SEASON_DAY_DIR).list().length + seasonDaysToWait;
			System.out.println(seasonDaysNumber);
			String finalSeasonDayPatternName;
			List<SeasonDayResult> seasonDayResults = new ArrayList<SeasonDayResult>();
			SeasonDayResult seasonDayResult;
			for (int i = 1; i < seasonDaysNumber; i++) {
//			for (int i = 1; i < 2; i++) {
				finalSeasonDayPatternName = AppConstants.SEASON_DAY_DIR + AppConstants.SEASON_DAY_PATTERN_FILE_NAME.replace("XXX", i+"");
				if (AppConstants.SEASON_DAYS_TO_WAIT.contains(i)){
					seasonDayResults.add(null);
					continue;
				}
				seasonDayResult = DaySeasonAnalyzer.execute(finalSeasonDayPatternName);
				seasonDayResults.add(seasonDayResult);
				

//				Per salvare su file separati
//				String finalResultSeasonDayPatternName = FileSystemPath.seasonsDayResultsDirectory + FileSystemPath.seasonsDayResultsPatternFileName.replace("XXX", i+"");
//				IOUtils.write(finalResultSeasonDayPatternName, seasonDayResults);

//				Per leggere
//				SeasonDayResult read = IOUtils.read(directoryResults + resultSeasonDayPatternName.replace("XXX", i+""), SeasonDayResult.class);
				
			}
			System.out.println(seasonDayResults);
			String finalResultSeasonPath= AppConstants.SEASON_DAYS_RESULTS_DIR + AppConstants.SEASON_DAYS_RESULTS_FILE_NAME;
			IOUtils.write(finalResultSeasonPath, seasonDayResults);

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}	
}