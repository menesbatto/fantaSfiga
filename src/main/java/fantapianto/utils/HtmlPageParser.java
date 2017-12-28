package fantapianto.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fantapianto._1_realChampionshipAnalyzer.model.LineUp;
import fantapianto._1_realChampionshipAnalyzer.model.PlayerVote;
import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;
import fantapianto._2_seasonPatternExtractor.model.Match;
import fantapianto._2_seasonPatternExtractor.model.SeasonDay;

public class HtmlPageParser {

	public static void main(String[] args) throws Exception {

		if (AppConstants.PROXY_ACTIVE){
			System.setProperty("http.proxyHost", AppConstants.PROXY_HOST);
			System.setProperty("http.proxyPort", AppConstants.PROXY_PORT);
		}
		Document doc = Jsoup.connect(AppConstants.CALENDAR_URL_TEMPLATE).get();
		Elements seasonDayElements = doc.getElementsByTag("table");
		
		List<SeasonDay> seasonDays = new ArrayList<SeasonDay>();
		SeasonDay seasonDay;
		
		for (int i = 0; i < seasonDayElements.size(); i++) {
			Element lineUpElem = seasonDayElements.get(i);
			seasonDay = createSeasonDay(lineUpElem, "Giornata " + (i+1));
			seasonDays.add(seasonDay);
		}
		System.out.println(seasonDays);

	}

	private static SeasonDay createSeasonDay(Element seasonDayElements, String seasonDayName) {
		SeasonDay seasonDay = new SeasonDay(seasonDayName);
		Elements matchesDomElems = seasonDayElements.getElementsByClass("match");
		Match m;
		Elements teamsDomElems;
		String homeTeam;
		String awayTeam;
		for (Element matchElem : matchesDomElems){
			 teamsDomElems = matchElem.getElementsByTag("a");
			homeTeam = teamsDomElems.get(0).text();
			awayTeam = teamsDomElems.get(1).text();
			m = new Match(homeTeam, awayTeam);
			seasonDay.getMatches().add(m);
		}
		return seasonDay;
	}

	private static LineUp createLineUpLight(Element lue) {
		LineUp lu = new LineUp();
		
		String teamName = lue.getElementsByTag("h3").get(0).ownText();
		Elements playersElem = lue.getElementsByClass("playerRow");
		PlayerVote playerVote;
		for (Element playerElem : playersElem) {
			playerVote = getPlayer(playerElem);

		}
		
		
		Elements attackModifiers = lue.getElementsMatchingOwnText("Modificatore attacco:");
		Double attackModifier = attackModifiers.isEmpty() ? 0 : getVote(attackModifiers.get(0).siblingNodes().get(0).childNode(0).toString());
		Elements defenseModifiers = lue.getElementsMatchingOwnText("Modificatore difesa:");
		Double defenceModifier = defenseModifiers.isEmpty() ? 0 : getVote(defenseModifiers.get(0).siblingNodes().get(0).childNode(0).toString());
		
		return null;
	}

	private static PlayerVote getPlayer(Element playerElem) {
		String playerString = playerElem.text();

		RoleEnum role = RoleEnum.valueOf(playerElem.getElementsByClass("myhidden-xs").get(0).text());
		String name = playerElem.getElementsByClass("sh").get(0).text();
		String team = playerElem.getElementsByClass("pt").get(0).text();
		Double vote = getVote(playerElem.getElementsByClass("pt").get(1).text());
		Double fantaVote = getVote(playerElem.getElementsByClass("pt").get(2).text());
		PlayerVote p = new PlayerVote(role, name, team, vote, fantaVote);

		return null;
	}

	private static Double getVote(String voteString) {
		Double vote = null;
		if (!voteString.equals("-"))
			vote = Double.valueOf(voteString.replace(",", "."));
		return vote;
	}
}