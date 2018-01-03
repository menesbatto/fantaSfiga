package fantapianto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fantapianto._0_realChampionshipVotesDownloader.MainSeasonVotesDowloader;
import fantapianto._0_realChampionshipVotesDownloader.model.PlayerVoteComplete;
import fantapianto._0_realChampionshipVotesDownloader.model.VotesSourceEnum;
import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import fantapianto._2_seasonPatternExtractor.model.Season;
import fantapianto._4_seasonsGenerator.MainSeasonsGenerator;
import fantapianto._5_seasonsExecutor.MainSeasonsExecutor;
import fantapianto._5_seasonsExecutor.model.Ranking;
import fantapianto._6_rankingAnalizer.MainRankingAnalyzer;
import fantapianto.utils.AppConstants;

public class Main {
	public static void main(String[] args) {
		
		AppConstants.FAST_MODE_ACTIVE = true;
		
		// Scarica voti
//		Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> votes = MainSeasonVotesDowloader.execute();
		Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> votes = null;
		
		// Scarica le formazioni schierate, associa i voti scaricati precedentemente,
		// controlla differenze tra i voti delle formazioni schierate e i voti scaricati
		MainSeasonAnalyzerFINAL.execute(votes);
		
		// Genera tutti i possibili calendari (sarebbe inutile farlo sempre ma ci si mette meno ad eseguirlo che a deserializzarli da disco)
		ArrayList<Season> allSeasons = MainSeasonsGenerator.execute();
		
		// Esegue tutti i possibili calendari e ne salva i risutati
		ArrayList<Ranking> allRankings = MainSeasonsExecutor.execute(allSeasons);
		
		// Analizza i risultati raccolti e mostra statistiche
		MainRankingAnalyzer.execute(allRankings);
	}
}
