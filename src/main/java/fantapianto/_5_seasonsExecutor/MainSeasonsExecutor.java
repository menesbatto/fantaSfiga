package fantapianto._5_seasonsExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fantapianto._2_seasonPatternExtractor.model.Season;
import fantapianto._4_seasonsGenerator.MainSeasonsGenerator;
import fantapianto._5_seasonsExecutor.model.Ranking;
import fantapianto._5_seasonsExecutor.model.RankingRow;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

public class MainSeasonsExecutor {
	
	private static ArrayList<Season> allSeasons;
	private static int i = 0;
	
	
	public static void main(String[] args) {
		execute(null);
	}
	
	public static ArrayList<Ranking> execute(ArrayList<Season> allSeasonsInput){
		allSeasons = allSeasonsInput;
		initStaticFields();
		
		long startTime = System.nanoTime();
		System.out.println("Inizio esecuzione di tutti i calendari");
		
		ArrayList<Ranking> rankings = new ArrayList<Ranking>();
		
		for ( Season season : allSeasons){
			i++;
			Ranking ranking = SeasonExecutor.execute(season);
			Collections.sort(ranking.getRows(), new Comparator<RankingRow>() {

				public int compare(RankingRow o1, RankingRow o2) {
					return o2.getPoints() - o1.getPoints();
				}
			});
			List<RankingRow> rows = ranking.getRows();
			
			rows.get(0).setRankingPosition(1); 
			for (int j = 1; j < rows.size(); j++) {
				RankingRow row = rows.get(j);
				RankingRow prevRow = rows.get(j-1);
				if (row.getPoints()== prevRow.getPoints())
					row.setRankingPosition(prevRow.getRankingPosition());
				else 
					row.setRankingPosition(j+1);
				
			}
			System.out.println();
			rankings.add(ranking);
//			if (ranking.getRows().get(1).getName().equals("Hawkins"))
//				System.out.println(season);
		} 
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		System.out.println("Fine esecuzione di tutti i calendari " + duration/1000000);
		
		IOUtils.write(AppConstants.REAL_RANKING_DIR + AppConstants.REAL_RANKING_FILE_NAME , rankings.get(0));
		
		if (!AppConstants.FAST_MODE_ACTIVE)
			IOUtils.write(AppConstants.RANKING_DIR + AppConstants.RANKING_FILE_NAME , rankings);
		
		return rankings;
	}
	
	

	private static void initStaticFields() {
		if (allSeasons == null)
			allSeasons =  MainSeasonsGenerator.getAllGeneratedSeasonStructures();
	}

	public static ArrayList<Ranking> getAllGeneratedRankings() {
		long startTime = System.nanoTime();
		System.out.println("Inizio caricamento di tutte le classifiche");
		ArrayList<Ranking> allRankings = IOUtils.read(AppConstants.RANKING_DIR + AppConstants.RANKING_FILE_NAME, ArrayList.class);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime); // divide by 1000000 to get
												// milliseconds.
		System.out.println("Fine caricamento " + duration / 1000000);
		return allRankings;
	}
	
	public static Ranking getRealRanking() {
		Ranking ranking = IOUtils.read(AppConstants.REAL_RANKING_DIR + AppConstants.REAL_RANKING_FILE_NAME, Ranking.class);
		return ranking;
	}
}
