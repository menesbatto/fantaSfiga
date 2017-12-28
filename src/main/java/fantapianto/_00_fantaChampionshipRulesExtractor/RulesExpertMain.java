package fantapianto._00_fantaChampionshipRulesExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.NewCookie;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fantapianto._00_fantaChampionshipRulesExtractor.model.BonusMalus;
import fantapianto._00_fantaChampionshipRulesExtractor.model.DataSources;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Modifiers;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Points;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Rules;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Substitutions;
import fantapianto._00_fantaChampionshipRulesExtractor.model.SubstitutionsModeEnum;
import fantapianto._0_realChampionshipVotesDownloader.model.VotesSourceEnum;
import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;
import fantapianto.utils.AppConstants;
import fantapianto.utils.HttpUtils;
import fantapianto.utils.IOUtils;

public class RulesExpertMain {
	public static void main(String args[]) {

		try {
			if (AppConstants.PROXY_ACTIVE){
				System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
				System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
			}

//			ClientResponse cuResponse = loginInFantagazzetta();

			
			BonusMalus bonusMalus = analyzeRulesPageBonusMalus();
			
			DataSources dataSources = analyzeRulesPageDataSources();
			
			Substitutions substitutions = analyzeRulesPageSubstitutions();

			Points points = analyzeRulesPagePoints();

			Modifiers modifiers = analyzeRulesPageModifiers();
			
			Rules rules = new Rules();
			rules.setBonusMalus(bonusMalus);
			rules.setDataSource(dataSources);
			rules.setSubstitutions(substitutions);
			rules.setPoints(points);
			rules.setModifiers(modifiers);
				
				
				
				String rulesPath= AppConstants.RULES_DIR + AppConstants.RULES_FILE_NAME;
				IOUtils.write(rulesPath, rules);
				

			

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static Modifiers analyzeRulesPageModifiers( ) throws IOException {
		Document doc = HttpUtils.getHtmlPage(AppConstants.RULES_5_MODIFIERS_URL);
		
		Modifiers m = new Modifiers();
		
		
		m = getGoalKeeperModifiers(m, doc);
		
		m = getDefenderModifiers(m, doc);
		
		m = getMiddlefielderModifiers(m, doc);
		
		m = getStrickerModifiers(m, doc);
		
		m = getPerformanceModifiers(m, doc);
		
		m = getFairPlayMoodifiers(m, doc);
		
		
		
		
		System.out.println(m);
		
		return m;
	}

	private static Modifiers getPerformanceModifiers(Modifiers m, Document doc) {
		String isPerformanceActiveString = doc.getElementsMatchingOwnText("Modificatore di rendimento:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isPerformancerActive = isPerformanceActiveString.equals("1");
		
		if (!isPerformancerActive)
			return m;
		
		m.setPerformanceModifierActive(true);
		
		m.setPerformance0(-5.0);
		m.setPerformance1(-3.0);
		m.setPerformance2(-2.0);
		m.setPerformance3(-1.0);
		m.setPerformance4(0.0);
		m.setPerformance5(0.0);
		m.setPerformance6(0.0);
		m.setPerformance7(0.0);
		m.setPerformance8(1.0);
		m.setPerformance9(2.0);
		m.setPerformance10(3.0);
		m.setPerformance11(5.0);
		
		return m;
		
	}
	
	
	private static Modifiers getFairPlayMoodifiers(Modifiers m, Document doc) {
		String isFairPlayString = doc.getElementsMatchingOwnText("Modificatore FairPlay:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isFairPlayActive = isFairPlayString.equals("1");
		
		if (!isFairPlayActive)
			return m;
		
		m.setFairPlayModifierActive(true);
		
		String fairPlayString = doc.getElementsByAttributeValue("id", "vdfairplay").val();
		Double fairPlay = Double.valueOf(fairPlayString);
		m.setFairPlay(fairPlay);
		
		return m;
	}
	
	private static Modifiers getStrickerModifiers(Modifiers m, Document doc) {
		String isStrikerActiveString = doc.getElementsMatchingOwnText("Modificatore attacco:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isStrikerActive = isStrikerActiveString.equals("1");
		
		if (!isStrikerActive)
			return m;
		
		m.setStrikerModifierActive(true);
		
		String vote6String = doc.getElementsByAttributeValue("id", "moda1").val();
		Double vote6 = Double.valueOf(vote6String);
		m.setStrikerVote6(vote6);
	
		String vote6halfString = doc.getElementsByAttributeValue("id", "moda2").val();
		Double vote6half = Double.valueOf(vote6halfString);
		m.setStrikerVote6half(vote6half);
		
		String vote7String = doc.getElementsByAttributeValue("id", "moda3").val();
		Double vote7 = Double.valueOf(vote7String);
		m.setStrikerVote7(vote7);
		
		String vote7halfString = doc.getElementsByAttributeValue("id", "moda4").val();
		Double vote7half = Double.valueOf(vote7halfString);
		m.setStrikerVote7half(vote7half);
		
		String vote8String = doc.getElementsByAttributeValue("id", "moda5").val();
		Double vote8 = Double.valueOf(vote8String);
		m.setStrikerVote8(vote8);
		
		return m;
		
	}

	private static Modifiers getMiddlefielderModifiers(Modifiers m, Document doc) {
		String isMidActiveString = doc.getElementsMatchingOwnText("Modificatore centrocampo:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isMidActive = isMidActiveString.equals("1");
		
		if (!isMidActive)
			return m;
		
		m.setMiddlefielderModifierActive(true);

		
		String near0String = doc.getElementsByAttributeValue("id", "modc1").val();
		Double near0 = Double.valueOf(near0String);
		m.setMiddlefielderNear0(near0);
		
		String over2String = doc.getElementsByAttributeValue("id", "modc2").val();
		Double over2 = Double.valueOf(over2String);
		m.setMiddlefielderOver2(over2);
		
		String underMinus2String = doc.getElementsByAttributeValue("id", "modc3").val();
		Double underMinus2 = Double.valueOf(underMinus2String);
		m.setMiddlefielderUnderMinus2(underMinus2);
		
		
		String over4String = doc.getElementsByAttributeValue("id", "modc4").val();
		Double over4 = Double.valueOf(over4String);
		m.setMiddlefielderOver4(over4);
		
		String underMinus4String = doc.getElementsByAttributeValue("id", "modc5").val();
		Double underMinus4 = Double.valueOf(underMinus4String);
		m.setMiddlefielderUnderMinus4(underMinus4);
		
		
		String over6String = doc.getElementsByAttributeValue("id", "modc6").val();
		Double over6 = Double.valueOf(over6String);
		m.setMiddlefielderOver6(over6);
		
		String underMinus6String = doc.getElementsByAttributeValue("id", "modc7").val();
		Double underMinus6 = Double.valueOf(underMinus6String);
		m.setMiddlefielderUnderMinus6(underMinus6);
		
		
		String over8String = doc.getElementsByAttributeValue("id", "modc8").val();
		Double over8 = Double.valueOf(over8String);
		m.setMiddlefielderOver8(over8);
		
		String underMinus8String = doc.getElementsByAttributeValue("id", "modc9").val();
		Double underMinus8 = Double.valueOf(underMinus8String);
		m.setMiddlefielderUnderMinus8(underMinus8);
		
		
		return m;
		
		
		
	}

	private static Modifiers getDefenderModifiers(Modifiers m, Document doc) {
		String isDefenderActiveString = doc.getElementsMatchingOwnText("Modificatore difesa:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isDefenderActive = isDefenderActiveString.equals("1");
		
		if (!isDefenderActive)
			return m;
		
		m.setDefenderModifierActive(true);

		
		String pointTo7String = doc.getElementsByAttributeValue("id", "modd1").val();
		Double pointTo7 =  Double.valueOf(pointTo7String);
		m.setDefenderAvgVote7(pointTo7);
		
		
		String pointTo6halfString = doc.getElementsByAttributeValue("id", "modd2").val();
		Double pointTo6half =  Double.valueOf(pointTo6halfString);
		m.setDefenderAvgVote6half(pointTo6half);
		
		
		String pointTo6String = doc.getElementsByAttributeValue("id", "modd3").val();
		Double pointTo6 = Double.valueOf(pointTo6String);
		m.setDefenderAvgVote6(pointTo6);
		
		return m;
		
	}

	private static Modifiers getGoalKeeperModifiers(Modifiers m, Document doc) {
		String isGoalKeeperActiveString = doc.getElementsMatchingOwnText("Modificatore portiere:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isGoalKeeperActive = isGoalKeeperActiveString.equals("1");
		
		if (!isGoalKeeperActive)
			return m;
		
		m.setGoalkeeperModifierActive(true);
		
		String vote3String = doc.getElementsByAttributeValue("id", "modp1").val();
		Double vote3 = Double.valueOf(vote3String);
		m.setGoalkeeperVote3(vote3);
		
		String vote3halfString = doc.getElementsByAttributeValue("id", "modp2").val();
		Double vote3half = Double.valueOf(vote3halfString);
		m.setGoalkeeperVote3half(vote3half);
		
		String vote4String = doc.getElementsByAttributeValue("id", "modp3").val();
		Double vote4 = Double.valueOf(vote4String);
		m.setGoalkeeperVote4(vote4);
		
		String vote4halfString = doc.getElementsByAttributeValue("id", "modp4").val();
		Double vote4half = Double.valueOf(vote4halfString);
		m.setGoalkeeperVote4half(vote4half);
		
		String vote5String = doc.getElementsByAttributeValue("id", "modp5").val();
		Double vote5 = Double.valueOf(vote5String);
		m.setGoalkeeperVote5(vote5);
		
		String vote5halfString = doc.getElementsByAttributeValue("id", "modp6").val();
		Double vote5half = Double.valueOf(vote5halfString);
		m.setGoalkeeperVote5half(vote5half);
		
		String vote6String = doc.getElementsByAttributeValue("id", "modp7").val();
		Double vote6 = Double.valueOf(vote6String);
		m.setGoalkeeperVote6(vote6);
		
		String vote6halfString = doc.getElementsByAttributeValue("id", "modp8").val();
		Double vote6half = Double.valueOf(vote6halfString);
		m.setGoalkeeperVote6half(vote6half);
		
		String vote7String = doc.getElementsByAttributeValue("id", "modp9").val();
		Double vote7 = Double.valueOf(vote7String);
		m.setGoalkeeperVote7(vote7);
		
		String vote7halfString = doc.getElementsByAttributeValue("id", "modp10").val();
		Double vote7half = Double.valueOf(vote7halfString);
		m.setGoalkeeperVote7half(vote7half);
		
		String vote8String = doc.getElementsByAttributeValue("id", "modp11").val();
		Double vote8 = Double.valueOf(vote8String);
		m.setGoalkeeperVote8(vote8);
		
		String vote8halfString = doc.getElementsByAttributeValue("id", "modp12").val();
		Double vote8half = Double.valueOf(vote8halfString);
		m.setGoalkeeperVote8half(vote8half);
		
		String vote9String = doc.getElementsByAttributeValue("id", "modp13").val();
		Double vote9 = Double.valueOf(vote9String);
		m.setGoalkeeperVote9(vote9);
		
		
		
		return m;
		
	}

	private static Substitutions analyzeRulesPageSubstitutions()  throws IOException {
		Document doc = HttpUtils.getHtmlPage(AppConstants.RULES_3_SUBSTITUTIONS_URL);
//		
//		String isFasciaConIntornoActiveString = doc.getElementsMatchingOwnText("Fascia con intorno:").parents().get(0).getElementsByAttribute("checked").val();
//		Boolean isFasciaConIntornoActive = isFasciaConIntornoActiveString.equals("0");
//		p.setFasciaConIntornoActive(isFasciaConIntornoActive);
//		String fasciaConIntornoString = doc.getElementsByAttributeValue("id", "valintorno").val();
//		Double fasciaConIntorno = Double.valueOf(fasciaConIntornoString);
//		p.setFasciaConIntorno(fasciaConIntorno);
		Substitutions s = new Substitutions();
		
		String numeroSostituzioniString = doc.getElementsByAttributeValue("id", "nsostituzioni").val();
		Integer numeroSostituzioni = Integer.valueOf(numeroSostituzioniString);
		s.setSubstitutionNumber(numeroSostituzioni);
		
		String effettuaSostituzioni = doc.getElementsMatchingOwnText("Effettua sostituzioni:").parents().get(0).getElementsByAttribute("selected").text();
		if (effettuaSostituzioni.equals("Con applicazione immediata del cambio modulo"))
			s.setSubstitutionMode(SubstitutionsModeEnum.CHANGE_MODULE);
		else if (effettuaSostituzioni.equals("Con cambio ruolo prioritario sul cambio modulo"))
			s.setSubstitutionMode(SubstitutionsModeEnum.CHANGE_ROLE_THEN_CHANGE_MODULE);
		else// if (effettuaSostituzioni.equals("Solo con cambio ruolo (senza cambio modulo)"))
			s.setSubstitutionMode(SubstitutionsModeEnum.CHANGE_ROLE);
			
		String isAssegnaVotoAdAmmonitoSvActiveString = doc.getElementsByAttributeValue("id", "ammonitosv1").attr("checked");
		Boolean isAssegnaVotoAdAmmonitoSvActive = isAssegnaVotoAdAmmonitoSvActiveString.equals("checked");
		s.setYellowCardSvOfficeVoteActive(isAssegnaVotoAdAmmonitoSvActive);
		String votoAdAmmonitoSvString = doc.getElementsByAttributeValue("id", "vammonitosv").val();
		Double votoAdAmmonitoSv = Double.valueOf(votoAdAmmonitoSvString.replace(",", "."));
		s.setYellowCardSvOfficeVote(votoAdAmmonitoSv);
		
		String goalkeeperPlayerOfficeVoteActiveString = doc.getElementsByAttributeValue("id", "riservaufficiop1").attr("checked");
		Boolean goalkeeperPlayerOfficeVoteActive = goalkeeperPlayerOfficeVoteActiveString.equals("checked");
		s.setGoalkeeperPlayerOfficeVoteActive(goalkeeperPlayerOfficeVoteActive);
		String goalkeeperPlayerOfficeVoteString = doc.getElementsByAttributeValue("id", "vriservap").val();
		Double goalkeeperPlayerOfficeVote = Double.valueOf(goalkeeperPlayerOfficeVoteString.replace(",", "."));
		s.setGoalkeeperPlayerOfficeVote(goalkeeperPlayerOfficeVote);
		
		String movementsPlayerOfficeVoteActiveString =  doc.getElementsByAttributeValue("id", "riservaufficiogm1").attr("checked");
		Boolean movementsPlayerOfficeVoteActive = movementsPlayerOfficeVoteActiveString.equals("checked");
		s.setMovementsPlayerOfficeVoteActive(movementsPlayerOfficeVoteActive);
		String movementsPlayerOfficeVoteString = doc.getElementsByAttributeValue("id", "vriservagm").val();
		Double movementsPlayerOfficeVote = Double.valueOf(movementsPlayerOfficeVoteString.replace(",", "."));
		s.setMovementsPlayerOfficeVote(movementsPlayerOfficeVote);;
		
		return s;
	}

	private static Points analyzeRulesPagePoints( ) throws IOException {
		Document doc = HttpUtils.getHtmlPage(AppConstants.RULES_4_POINTS_URL);
				
		Points p = new Points();
		
		String firstGoalPointsString = doc.getElementsMatchingOwnText("Soglia gol:").parents().get(0).select("input").val();
		Double firstGoalPoints = Double.valueOf(firstGoalPointsString);
		
		String goalsRangesString = doc.getElementsMatchingOwnText("Numero delle fasce gol:").parents().get(0).select("input").val();
		String[] goalsRangesSplit = goalsRangesString.split(";");
		
		
		List<Double> goalPoints = new ArrayList<Double>();
		goalPoints.add(firstGoalPoints);
		int i = 0;
		for (i = 0; i < goalsRangesSplit.length; i++)
			goalPoints.add(goalPoints.get(i) + new Double(goalsRangesSplit[i]));
		Double lastRange = goalPoints.get(i) - goalPoints.get(i-1);
		for (; i < 15; i++){
			goalPoints.add(goalPoints.get(i) + lastRange);
		}
		p.setGoalPoints(goalPoints);
		
		String formulaUnoPointsString = doc.getElementsMatchingOwnText("Numero posizioni Formula 1:").parents().get(0).select("input").val();
		String[] formulaUnoSplit = formulaUnoPointsString.split(";");

		List<Double> formulaUnoPoints = new ArrayList<Double>();
		for (i = 0; i < formulaUnoSplit.length; i++)
			formulaUnoPoints.add(new Double(formulaUnoSplit[i]));
		p.setFormulaUnoPoints(formulaUnoPoints);
		//p.setFormulaUnoPoints(Arrays.asList(8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0));
		
		String isFasciaConIntornoActiveString = doc.getElementsMatchingOwnText("Fascia con intorno:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isFasciaConIntornoActive = isFasciaConIntornoActiveString.equals("0");
		p.setFasciaConIntornoActive(isFasciaConIntornoActive);
		String fasciaConIntornoString = doc.getElementsByAttributeValue("id", "valintorno").val();
		Double fasciaConIntorno = Double.valueOf(fasciaConIntornoString);
		p.setFasciaConIntorno(fasciaConIntorno);
		
		String isIntornoActiveString = doc.getElementsMatchingOwnText("Intorno:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isIntornoActive = isIntornoActiveString.equals("0");
		p.setIntornoActive(isIntornoActive);
		String intornoString = doc.getElementsByAttributeValue("id", "vintorno").val();
		Double intorno = Double.valueOf(intornoString);
		p.setIntorno(intorno);
		
		String isControllaPareggioActiveString = doc.getElementsMatchingOwnText("Controlla pareggio:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isControllaPareggioActive = isControllaPareggioActiveString.equals("0");
		p.setControllaPareggioActive(isControllaPareggioActive);
		String controllaPareggioString = doc.getElementsByAttributeValue("id", "vcpareggio").val();
		Double controllaPareggio = Double.valueOf(controllaPareggioString);
		p.setControllaPareggio(controllaPareggio);
		
		String isDifferenzaPuntiActiveString = doc.getElementsMatchingOwnText("Differenza punti:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isDifferenzaPuntiActive = isDifferenzaPuntiActiveString.equals("0");
		p.setDifferenzaPuntiActive(isDifferenzaPuntiActive);
		String differenzaPuntiString = doc.getElementsByAttributeValue("id", "vdpunti").val();
		Double differenzaPunti = Double.valueOf(differenzaPuntiString);
		p.setDifferenzaPunti(differenzaPunti);
		
		String isPortiereImbattutoActiveString = doc.getElementsMatchingOwnText("Bonus portiere imbattuto:").parents().get(0).getElementsByAttribute("checked").val();
		Boolean isPortiereImbattutoActive = isPortiereImbattutoActiveString.equals("1");
		p.setPortiereImbattutoActive(isPortiereImbattutoActive);
		String portiereImbattutoString = doc.getElementsByAttributeValue("id", "vpimbattuto").val();
		Double portiereImbattuto = Double.valueOf(portiereImbattutoString);
		p.setPortiereImbattuto(portiereImbattuto);
		
		System.out.println(p);
		
		
		
		
		return p;
	}


	private static DataSources analyzeRulesPageDataSources() throws IOException {
		
		Document doc = HttpUtils.getHtmlPage(AppConstants.RULES_2_SOURCE_URL);
		
		DataSources ds = new DataSources();
		
		String votesSourceString = doc.getElementsMatchingOwnText("Fonte voti:").parents().get(0).getElementsByAttribute("selected").text();
		VotesSourceEnum voteSource = VotesSourceEnum.valueOf(votesSourceString.toUpperCase());
		ds.setVotesSource(voteSource);
		
		String bonusMalusSourceString = doc.getElementsMatchingOwnText("Fonte bonus/malus:").parents().get(0).getElementsByAttribute("selected").text();
		if (bonusMalusSourceString.equals("Fantagazzetta (Ex Napoli)"))
			bonusMalusSourceString = "Napoli";
		VotesSourceEnum bonusMalusSource = VotesSourceEnum.valueOf(bonusMalusSourceString.toUpperCase());
		ds.setBonusMalusSource(bonusMalusSource);
		
//		String cardsSourceString = doc.getElementsMatchingOwnText("Fonte ammonizioni/esplusioni:").parents().get(0).getElementsByAttribute("selected").text();
//		String precisiontring = doc.getElementsMatchingOwnText("Precisione Voti:").parents().get(0).getElementsByAttribute("selected").text();
		
		
		
		System.out.println(ds);
		
		
		return ds;
	}

	
	private static BonusMalus analyzeRulesPageBonusMalus() throws IOException {

		Document doc = HttpUtils.getHtmlPage(AppConstants.RULES_1_BONUS_MALUS_URL);	
		
		System.out.println(doc);
		BonusMalus bm = new BonusMalus();
		
		Elements scoredGoalElements = doc.getElementsMatchingOwnText("Gol segnato:").parents().get(0).select("input");
		bm.setScoredGoal(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Gol subito:").parents().get(0).select("input");
		bm.setTakenGoal(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Rigore segnato:").parents().get(0).select("input");
		bm.setScoredPenalty(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Rigore sbagliato:").parents().get(0).select("input");
		bm.setMissedPenalty(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Rigore parato:").parents().get(0).select("input");
		bm.setSavedPenalty(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Ammonizione:").parents().get(0).select("input");
		bm.setYellowCard(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Espulsione:").parents().get(0).select("input");
		bm.setRedCard(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Assist:").parents().get(0).select("input");
		bm.setMovementAssist(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Assist da fermo:").parents().get(0).select("input");
		bm.setStationaryAssist(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Autogol:").parents().get(0).select("input");
		bm.setAutogoal(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Gol decisivo pareggio:").parents().get(0).select("input");
		bm.setEvenGoal(getRolesMap(scoredGoalElements));
		
		scoredGoalElements = doc.getElementsMatchingOwnText("Gol decisivo vittoria:").parents().get(0).select("input");
		bm.setWinGoal(getRolesMap(scoredGoalElements));
		
		
		System.out.println(bm);
		return bm;
	}

	private static Map<RoleEnum, Double> getRolesMap(Elements elements) {
		Map<RoleEnum, Double> map = new HashMap<RoleEnum, Double>();
		String valString = elements.get(0).val();
		Double val = Double.valueOf(valString); 
		map.put(RoleEnum.P, val);
		
		valString = elements.get(1).val();
		val = Double.valueOf(valString); 
		map.put(RoleEnum.D, val);
		
		valString = elements.get(2).val();
		val = Double.valueOf(valString); 
		map.put(RoleEnum.C, val);
		
		valString = elements.get(3).val();
		val = Double.valueOf(valString); 
		map.put(RoleEnum.A, val);
		
		return map;
	}

//	private static ClientResponse loginInFantagazzetta() {
//		Client client = Client.create();
//		WebResource webResource = null;
//		String urlToCall = "http://leghe.fantagazzetta.com/servizi/LG.asmx/lg";
//		webResource = client.resource(urlToCall);
//		WebResource.Builder builder = webResource.getRequestBuilder();
//
//		ClientResponse cuResponse = null;
//		builder.header("Content-Type", "application/json");
//		String requestPayload = "{" + "\"u\": \"" + AppConstants.user + "\", "
//									+ "\"p\": \"" + AppConstants.password + "\", "
//									+ "\"aliaslega\": \"" + AppConstants.LEAGUE_NAME + "\", "
//									+ "\"check\": \"u1-L12\"}";
//		cuResponse = builder.post(ClientResponse.class, requestPayload);
//		return cuResponse;
//	}
//	//{"u":"menesbatto","p":"suppaman","aliaslega":"accaniti-division","check":"u1-L12"}
//	
//	private static Document getHtmlPage(ClientResponse cuResponse, String url) throws IOException {
//		
//		Document htmlPage = HttpUtils.getHtmlPage(url);
//
//		
//		return htmlPage;
//	}
	
	

	public static Rules getRules() {
		Rules rules = IOUtils.read(AppConstants.RULES_DIR + AppConstants.RULES_FILE_NAME, Rules.class);
		return rules;
	}
	
	
}
