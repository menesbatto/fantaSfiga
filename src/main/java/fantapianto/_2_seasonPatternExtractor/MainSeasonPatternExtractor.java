package fantapianto._2_seasonPatternExtractor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import fantapianto._1_realChampionshipAnalyzer.model.Constants;
import fantapianto._2_seasonPatternExtractor.model.Match;
import fantapianto._2_seasonPatternExtractor.model.PlayerEnum;
import fantapianto._2_seasonPatternExtractor.model.Season;
import fantapianto._2_seasonPatternExtractor.model.SeasonDay;
import fantapianto.utils.AppConstants;
import fantapianto.utils.IOUtils;

@Deprecated
public class MainSeasonPatternExtractor {

	@Deprecated
	public static void main(String args[]) {
		readAllSheet();
	}
	@Deprecated
	private static void readAllSheet() {

		InputStream inp = null;
		Workbook wb = null;
		try {
			inp = new FileInputStream(AppConstants.CALENDAR_DIR + AppConstants.CALENDAR_FILE_NAME);
			wb = WorkbookFactory.create(inp);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		Sheet sheet = wb.getSheetAt(0);
		int rowsCount = sheet.getLastRowNum();

		System.out.println("Total Number of Rows: " + (rowsCount + 1));

		Season season = new Season();

		calculateSeasonColumn(sheet, rowsCount, Constants.firstMatchColumnStartingColumnIndex, season);
		calculateSeasonColumn(sheet, rowsCount, Constants.secondMatchColumnStartingColumnIndex, season);

		Collections.sort(season.getSeasonDays(), new Comparator<SeasonDay>() {
			public int compare(SeasonDay o1, SeasonDay o2) {
				return o1.getNameNumber().compareTo(o2.getNameNumber());
			}
		});

		Season lightSeason = createLightSeason(season);

		System.out.println(lightSeason);
		
		IOUtils.write(AppConstants.SEASON_REASULTS_DIR + AppConstants.SEASON_REASULTS_FILE_NAME , lightSeason);
//		Season read = IOUtils.read(FileSystemPath.seasonResultsDirectory + FileSystemPath.seasonPatternFileName, Season.class);
//		System.out.println(read);

	}
	@Deprecated
	private static Season createLightSeason(Season season) {
		Season ls = new Season();
		SeasonDay fsd = season.getSeasonDays().get(1);
		List<String> players = new ArrayList<String>();
		for (Match match : fsd.getMatches()) {
			players.add(match.getHomeTeam());
			players.add(match.getAwayTeam());
		}
		Collections.sort(players);
		IOUtils.write(AppConstants.PLAYERS_DIR + AppConstants.PLAYERS_FILE_NAME , players);
		
		
		for (SeasonDay sd : season.getSeasonDays()) {
			for (Match m : sd.getMatches()) {
				PlayerEnum homeTeamEnum = PlayerEnum.values()[players.indexOf(m.getHomeTeam())];
				PlayerEnum awayTeamEnum = PlayerEnum.values()[players.indexOf(m.getAwayTeam())];
				m.setHomeTeamEnum(homeTeamEnum);
				m.setAwayTeamEnum(awayTeamEnum);
				m.setHomeTeam(null);
				m.setAwayTeam(null);
			}
		}

		return season;

	}
	@Deprecated
	private static void calculateSeasonColumn(Sheet sheet, int rowsCount, int columnIndex, Season season) {
		SeasonDay seasonDay = new SeasonDay();
		for (int i = 3; i <= rowsCount; i++) {
			Row row = sheet.getRow(i);

			if (isSeasonDayName(row, columnIndex)) {
				if (seasonDay != null) {
					if (seasonDay.getMatches() != null)
						season.getSeasonDays().add(seasonDay);

					Cell cell = row.getCell(columnIndex);
					String seasonDayName = cell.getStringCellValue();
					seasonDay = new SeasonDay(seasonDayName);

				}
			} else {
				Match match = createMatch(row, columnIndex);
				seasonDay.getMatches().add(match);
			}

		}
	}
	@Deprecated
	private static Match createMatch(Row row, int columnIndex) {
		Cell cellHome = row.getCell(columnIndex);
		String homeTeamName = cellHome.getStringCellValue().trim();

		Cell cellAway = row.getCell(columnIndex + 3);
		String awayTeamName = cellAway.getStringCellValue().trim();

		Match m = new Match(homeTeamName, awayTeamName);

		return m;
	}
	@Deprecated
	private static boolean isSeasonDayName(Row row, Integer column) {
		Cell cell = row.getCell(column);
		if (cell != null) {
			String stringCellValue = cell.getStringCellValue();
			if (stringCellValue.contains("Giornata")) {
				return true;
			}
		}
		return false;

	}

}