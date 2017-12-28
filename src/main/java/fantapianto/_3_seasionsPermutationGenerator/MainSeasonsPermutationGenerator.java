package fantapianto._3_seasionsPermutationGenerator;

import java.util.ArrayList;
import java.util.List;

import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

public class MainSeasonsPermutationGenerator {

	private static Integer i = 0;
	
	public static void main(String[] args) {
		List<String> calendarPermutations = new ArrayList<String>();
		permutation("ABCDEFGH", calendarPermutations);
		IOUtils.write(AppConstants.ALL_INPUT_PERMUTATIONS_DIR + AppConstants.ALL_INPUT_PERMUTATIONS_FILE_NAME , calendarPermutations);
	}
	
	public static void permutation(String str, List<String> list) { 
	    permutation("", str, list); 
	}

	private static void permutation(String prefix, String str, List<String> list) {
	    int n = str.length();
	    if (n == 0) {
	    	System.out.println(prefix);
	    	list.add(prefix);
	    	System.out.println(i++);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), list);
	    }
	}
	
	public static ArrayList<String> getAllPermutations() {
		ArrayList<String> players = IOUtils.read(AppConstants.ALL_INPUT_PERMUTATIONS_DIR + AppConstants.ALL_INPUT_PERMUTATIONS_FILE_NAME, ArrayList.class);
		return players;
	}
}
