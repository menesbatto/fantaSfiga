package fantapianto.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fantapianto._0_realChampionshipVotesDownloader.model.VotesSourceEnum;

public class AppConstants {
	
	
	
	public static boolean OFFICE_RESERVERS_TILL_11 = true;
	public static boolean PROXY_ACTIVE = false;
	public static String PROXY_HOST = "10.0.1.251";
	public static String PROXY_PORT = "3128";

	public static boolean DEBUG_MODE =false;
	public static boolean FAST_MODE_ACTIVE = false;
	public static Boolean FORCE_INVERT_HOME_AWAY = false;
	public static Boolean FORCE_WINNING_FOR_DISTANCE = false;
	public static Double FORCE_WINNING_FOR_DISTANCE_POINTS = 1.0;
	
	public static VotesSourceEnum FORCE_VOTE_SOURCE = null;//VotesSourceEnum.NAPOLI;
	public static Boolean FORCE_GOALKEEPER_MODIFIER_DISABLED = false;
	public static Boolean FORCE_MIDDLEFIELD_MODIFIER_DISABLED = false;
	public static Boolean FORCE_DEFENDER_MODIFIER_DISABLED = false;
	public static Boolean FORCE_STRIKER_MODIFIER_DISABLED = false;
	public static Boolean FORCE_PERFORMANCE_MODIFIER_DISABLED = false;
	public static Boolean FORCE_FAIR_PLAY_MODIFIER_DISABLED = false;
	
	
//	
//	public static List<Double> GOAL_POINTS = Arrays.asList(66.0, 72.0, 77.0, 81.0, 85.0, 89.0, 93.0, 97.0, 101.0, 105.0, 109.0, 113.0, 117.0);
//	public static List<Double> FORMULA_UNO_POINTS = Arrays.asList(10.0, 8.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0);
	
	@Deprecated
	public static Set<Integer> SERIE_A_SEASON_DAYS_TO_WAIT =  new HashSet();
	@Deprecated
	public static List<Integer> SEASON_DAYS_TO_WAIT =  Arrays.asList(16);
	@Deprecated
	public static Double movementsPlayerOfficeVote = 4.0;
	@Deprecated
	public static Double goalkeeperPlayerOfficeVote = 3.0;
	@Deprecated
	public static Integer SERIE_A_ACTUAL_SEASON_DAY =  22;
	@Deprecated
	public static Integer FANTACALCIO_STARTING_SERIE_A_SEASON_DAY = 3;
//	public static Integer FANTACALCIO_STARTING_SERIE_A_SEASON_DAY = 5;
	
//	public static String LEAGUE_NAME = "mefaipvopviopenaleague";
//	public static String COMPETITION_NAME = "ME FAI PVOPVIO PENA LEAGUE";
//	public static String LEAGUE_DIR = "ale\\";
//	public static String user = "Ale.Dima.9";
//	public static String password = "9Vucinic";
	
	
//	public static String LEAGUE_NAME = "hppomezialeague";
//	public static String COMPETITION_NAME = "CAMPIONATOSEI";
//	public static String LEAGUE_DIR = "hppomezialeague\\";
//	public static String user = "nick23asr";
//	public static String password = "asroma23";
//	
	
	
	
	
	public static String LEAGUE_NAME = "accaniti-division";
	public static String COMPETITION_NAME = "CAMPIONATO SERIE A-CCANITA";
	public static String LEAGUE_DIR = "accanitidivision\\";
	public static String user = "menesbatto";
	public static String password = "suppaman";

	
	
	public static String LOGIN_PAGE = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME;
	public static String RULES_1_BONUS_MALUS_URL = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/visualizza-opzioni-calcolo1";
	public static String RULES_2_SOURCE_URL = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/visualizza-opzioni-calcolo2";
	public static String RULES_3_SUBSTITUTIONS_URL = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/visualizza-opzioni-calcolo3";
	public static String RULES_4_POINTS_URL = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/visualizza-opzioni-calcolo4";
	public static String RULES_5_MODIFIERS_URL = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/visualizza-opzioni-calcolo5";
	
	public static String SEASON_DAY_LINES_UP_URL_TEMPLATE = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/formazioni/[COMPETITION_ID]?g=";

	public static String SEASON_DAY_ALL_VOTES_URL = "http://www.fantagazzetta.com/voti-fantacalcio-serie-a/2017-18";
	public static String SEASON_DAY_VOTES_URL_TEMPLATE = "https://www.fantagazzetta.com/Servizi/Voti.ashx?s=2017-18&g=[SEASON_DAY]&tv=[DATE_TIME_MILLIS]&t=[GAZZETTA_TEAM_ID]";
//	public static String TEAMS_IDS_URL = "https://www.fantagazzetta.com/voti-serie-a/2017-18/1/";
	public static String TEAMS_IDS_URL = "https://www.fantagazzetta.com/voti-fantacalcio-serie-a/";
	
	public static String CALENDAR_URL_TEMPLATE = "http://leghe.fantagazzetta.com/" + LEAGUE_NAME + "/calendario/[COMPETITION_ID]";
	
	
	
//	"http://www.fantagazzetta.com/Servizi/Voti.ashx?s=2016-17&g=14&tv=222851700781&t=[GAZZETTA_TEAM_ID]";
//	"http://www.fantagazzetta.com/Servizi/Voti.ashx?s=2016-17&g=14&tv=224080328220&t=12"
	
	
	
	
	public static String ABSOLUT_PATH = "C:\\fantaSfiga\\";




	@Deprecated
	public static String CALENDAR_DIR = ABSOLUT_PATH + LEAGUE_DIR + "Calendar\\";
	@Deprecated
	public static String CALENDAR_FILE_NAME = "Calendario.xlsx";
	
	public static String SEASON_REASULTS_DIR = ABSOLUT_PATH + LEAGUE_DIR + "Calendar\\results\\";
	public static String SEASON_REASULTS_FILE_NAME = "Pattern";
	
	
	public static String PLAYERS_DIR = ABSOLUT_PATH + LEAGUE_DIR + "Players\\";
	public static String PLAYERS_FILE_NAME = "players";
	
	
	//Estrazione dei valori delle giornate del campionato reale
	@Deprecated
	public static String SEASON_DAY_DIR = ABSOLUT_PATH + LEAGUE_DIR + "RealChampionshipSeasonDays\\";
	@Deprecated
	public static String SEASON_DAY_PATTERN_FILE_NAME = "Formazioni-XXX.xlsx";
	
	public static String SEASON_DAYS_RESULTS_DIR = ABSOLUT_PATH + LEAGUE_DIR + "RealChampionshipSeasonDays\\results\\";
	public static String SEASON_DAYS_RESULTS_FILE_NAME = "SeasonDays";

	//MAPPA DI TUTTI I VOTI DI TUTTE LE GIORNATE DI TUTTE LE PARTITE DEL CAMPIONATO
	public static String REAL_CHAMPIONSHIP_VOTES_DIR = ABSOLUT_PATH + LEAGUE_DIR + "RealChampionshipVotes\\";
	public static String REAL_CHAMPIONSHIP_VOTES_FILE_NAME = "Votes";

	//MAPPA [NOME_SQUADRA] - [ID_SQUADRA] DI FANTAGAZZETTA
	public static String REAL_CHAMPIONSHIP_TEAMS_IDS_DIR = ABSOLUT_PATH + LEAGUE_DIR + "RealChampionshipTeamsIds\\";
	public static String REAL_CHAMPIONSHIP_TEAMS_IDS_FILE_NAME = "TeamsId";
	
	//TUTTE LE CLASSIFICHE DI TUTTI I POSSIBILI 40000 CAMPIONATI
	public static String RANKING_DIR = ABSOLUT_PATH + LEAGUE_DIR + "Rankings\\";
	public static String RANKING_FILE_NAME = "Rankings";

	//LA CLASSIFICA COMPLETA REALE
	public static String REAL_RANKING_DIR = ABSOLUT_PATH + LEAGUE_DIR + "Rankings\\";
	public static String REAL_RANKING_FILE_NAME = "RealChampionshipRanking";

	//LE REGOLE
	public static String RULES_DIR = ABSOLUT_PATH + LEAGUE_DIR + "Rules\\";
	public static String RULES_FILE_NAME = "Rules";
	
	
	public static String GENERATED_SEASONS_DIR = ABSOLUT_PATH + LEAGUE_DIR + "GeneratedSeasons\\"; 
	public static String GENERATED_SEASONS_FILE_NAME = "generatedSeasons"; 

	public static String ALL_INPUT_PERMUTATIONS_DIR = ABSOLUT_PATH + LEAGUE_DIR + "AllInputPermutations\\"; 
	public static String ALL_INPUT_PERMUTATIONS_FILE_NAME = "inputPermutation";
}
