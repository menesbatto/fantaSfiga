package fantapianto._4_seasonsGenerator;

import java.util.ArrayList;
import java.util.List;

import fantapianto._2_seasonPatternExtractor.model.Season;
import fantapianto._3_seasionsPermutationGenerator.MainSeasonsPermutationGenerator;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

//Crea tutti i possibili calendari (40320) e li scrive su file
public class MainSeasonsGenerator {
	
	private static Integer seasonNumber = 0;
	private static List<String> allInputPermutations;
	
	public static void main(String[] args) {
		execute();
	}
	
	public static ArrayList<Season> execute(){
		initStaticFields();
	
		long startTime = System.nanoTime();
		System.out.println("Inizio generazione di tutti i calendari");
		Season s;
		ArrayList<Season> allSeasons = new ArrayList<Season>();

		if (AppConstants.DEBUG_MODE) 
			allInputPermutations = allInputPermutations.subList(0, 1);
		
		for (String permutation : allInputPermutations){
			s = SeasonGenerator.execute(permutation, seasonNumber++);
//		for (String permutation : allInputPermutations){
//			s = SeasonGenerator.execute(permutation , seasonNumber++);
			allSeasons.add(s);
		}
		
		if (!AppConstants.FAST_MODE_ACTIVE)
			IOUtils.write(AppConstants.GENERATED_SEASONS_DIR + AppConstants.GENERATED_SEASONS_FILE_NAME , allSeasons);
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		System.out.println("Fine generazione di tutti i calendari " + duration/1000000);

		
		return allSeasons;
		
	}
	
	
	
	
	private static void initStaticFields() {
		allInputPermutations = MainSeasonsPermutationGenerator.getAllPermutations();
	}

	public static ArrayList<Season> getAllGeneratedSeasonStructures() {
		long startTime = System.nanoTime();
		System.out.println("Inizio caricamento di tutti i calendari");
		ArrayList<Season> allSeasons = IOUtils.read(AppConstants.GENERATED_SEASONS_DIR + AppConstants.GENERATED_SEASONS_FILE_NAME, ArrayList.class);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		System.out.println("Fine caricamento " + duration/1000000);
		return allSeasons;
	}
	
}
