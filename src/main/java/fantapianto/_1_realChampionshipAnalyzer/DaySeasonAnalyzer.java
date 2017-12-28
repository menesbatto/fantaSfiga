package fantapianto._1_realChampionshipAnalyzer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import fantapianto._00_fantaChampionshipRulesExtractor.RulesExpertMain;
import fantapianto._00_fantaChampionshipRulesExtractor.model.Rules;
import fantapianto._1_realChampionshipAnalyzer.model.Constants;
import fantapianto._1_realChampionshipAnalyzer.model.LineUp;
import fantapianto._1_realChampionshipAnalyzer.model.LineUpLight;
import fantapianto._1_realChampionshipAnalyzer.model.Pair;
import fantapianto._1_realChampionshipAnalyzer.model.PlayerVote;
import fantapianto._1_realChampionshipAnalyzer.model.RoleEnum;
import fantapianto._1_realChampionshipAnalyzer.model.SeasonDayResult;
import fantapianto.utils.AppConstants;

public class DaySeasonAnalyzer {

	static Rules rules = RulesExpertMain.getRules();

	
	public static SeasonDayResult execute(String seasonDayPath) {
		InputStream inp = null;
		Workbook wb = null;
		try {
			inp = new FileInputStream(seasonDayPath);
			wb = WorkbookFactory.create(inp);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		Sheet sheet = wb.getSheetAt(0);
		int rowsCount = sheet.getLastRowNum();

		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		String firstRowText = cell.getStringCellValue();
		String daySeasonName = firstRowText.substring(firstRowText.indexOf("Giornata"));
		
		
		
		
		System.out.println(seasonDayPath + "-  Total Number of Rows: " + (rowsCount + 1));

		// Crea limiti per recuperare dati
		ArrayList<Pair> startRows = new ArrayList<Pair>();
		ArrayList<Pair> endingRows = new ArrayList<Pair>();

		calculateLinesUpEdgeRows(sheet, rowsCount, Constants.firstMatchColumnStartingColumnIndex, startRows, endingRows);
		calculateLinesUpEdgeRows(sheet, rowsCount, Constants.secondMatchColumnStartingColumnIndex, startRows,
				endingRows);

		Integer teamFirstRow = startRows.size();

		// for (int i = 0; i < teamFirstRow; i++){
		// // System.out.println(startRows.get(i).toString() + " - " +
		// endingRows.get(i).toString());
		// }

		List<LineUp> linesUp = new ArrayList<LineUp>();

		// Crea oggetti formazioni e modificatori dall'excel
		for (int i = 0; i < teamFirstRow; i++) {
			Integer startRow = startRows.get(i).getRowIndex();
			Integer endRow = endingRows.get(i).getRowIndex();
			Integer startColumn = endingRows.get(i).getColumnIndex();
			LineUp lineUp = getLineUp(startRow, endRow, startColumn, sheet);
			linesUp.add(lineUp);
		}

		// Calcola la formazione scesa in campo
		calculareRealLineUps(linesUp);

		calculateMiddlefieldVariation(linesUp);

		// System.out.println(linesUp);
		List<LineUpLight> linesUpLight = new ArrayList<LineUpLight>();
		LineUpLight lineUpLight;
		for (LineUp lineUp : linesUp) {

			lineUpLight = createLineUpLight(lineUp);
			linesUpLight.add(lineUpLight);
		}

//		System.out.println(linesUpLight);
		
		SeasonDayResult result = new SeasonDayResult(daySeasonName, linesUpLight);
		
		return result;
	}

	private static Double calculateMiddlefieldVariation(List<LineUp> linesUp) {
		int middlefieldersNumber = 0;
		Double middlefieldersComulativeVote = 0.0;
		Double middlefieldersVariation = 0.0;
		Double malus = 0.0;
		Double bonus = 0.0;
		for (LineUp lineUp : linesUp) {
			middlefieldersComulativeVote = 0.0;
			middlefieldersVariation = 0.0;
			malus = 0.0;
			bonus = 0.0;
			middlefieldersNumber = 0;
			for (PlayerVote player : lineUp.getFinalLineUp()) {
				if (player.getRole().equals(RoleEnum.C)) {
					if (player.getTeam().equals("OFFICE"))
						middlefieldersComulativeVote += 5;
					else	
						middlefieldersComulativeVote += player.getVote();
					middlefieldersNumber++;
				}
			}

			middlefieldersVariation = middlefieldersComulativeVote - (middlefieldersNumber * 6);
			if (middlefieldersNumber < 4) {
				malus = (double) ((4 - middlefieldersNumber) * -1);
			}
			if (middlefieldersNumber > 4) {
				bonus = (double) ((middlefieldersNumber - 4));
			}
			middlefieldersVariation += malus;
			middlefieldersVariation += bonus;
			lineUp.setMiddlefieldersVariation(middlefieldersVariation);
		}

		return middlefieldersVariation;

	}

	private static LineUpLight createLineUpLight(LineUp lineUp) {
		LineUpLight lul = new LineUpLight();
		lul.setTeamName(lineUp.getTeamName());
		Double totalWithoutMiddlefielderModifier = 0.0;
		for (PlayerVote pv : lineUp.getFinalLineUp()) {
			totalWithoutMiddlefielderModifier += pv.getFantaVote();
		}
		totalWithoutMiddlefielderModifier += lineUp.getDefenderModifier();
		totalWithoutMiddlefielderModifier += lineUp.getStrickerModifier();

		lul.setTotalWithoutGoalkeeperAndMiddlefielderModifiers(totalWithoutMiddlefielderModifier);
		lul.setMiddlefieldersVariation(lineUp.getMiddlefieldersVariation());
		return lul;

	}

	private static void calculareRealLineUps(List<LineUp> linesUp) {

		Integer substitutions = 0;
		for (LineUp lineUp : linesUp) {
			substitutions = 0;
			List<PlayerVote> finalLineUp = new ArrayList<PlayerVote>();

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getGoalKeeper(), lineUp.getReserves(),
					RoleEnum.P, substitutions, rules.getSubstitutions().getGoalkeeperPlayerOfficeVote());

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getDefenders(), lineUp.getReserves(),
					RoleEnum.D, substitutions, rules.getSubstitutions().getMovementsPlayerOfficeVote());

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getMidfielders(), lineUp.getReserves(),
					RoleEnum.C, substitutions, rules.getSubstitutions().getMovementsPlayerOfficeVote());

			substitutions = addPlayerVoteForRole(finalLineUp, lineUp.getStrikers(), lineUp.getReserves(),
					RoleEnum.A, substitutions, rules.getSubstitutions().getMovementsPlayerOfficeVote());

			lineUp.setFinalLineUp(finalLineUp);
		}

	}

	private static Integer addPlayerVoteForRole(List<PlayerVote> finalLineUp, List<PlayerVote> roleSet,
			List<PlayerVote> reserves, RoleEnum role, Integer substitutions, Double officePlayerVote) {
		PlayerVote playerVoteToAdd;
		for (PlayerVote pv : roleSet) {
			playerVoteToAdd = pv;
			if (playerVoteToAdd.getFantaVote() == null) {
				//Ricerca in panchina
				playerVoteToAdd = getReserveForPlayer(playerVoteToAdd.getRole(), reserves, substitutions);
				if (playerVoteToAdd != null){
					substitutions++;
				}
				//Inserimento di ufficio o niente
				else {//if (playerVoteToAdd == null) {
					if (substitutions < 3) {
						playerVoteToAdd = new PlayerVote(role, "OFFICE", "OFFICE", officePlayerVote, officePlayerVote);
						substitutions++;
					} else {
						playerVoteToAdd = new PlayerVote(role, "ZERO", "ZERO", 0.0, 0.0);
						playerVoteToAdd.setStrikerModifier(0.0);
						playerVoteToAdd.setGoalkeerModifier(0.0);
					}
				}
			}
			finalLineUp.add(playerVoteToAdd);
		}
		return substitutions;
	}

	private static PlayerVote getReserveForPlayer(RoleEnum role, List<PlayerVote> reserves, Integer substitutions) {
		PlayerVote playerVoteToAdd = null;
		if (substitutions < 3) {
			for (PlayerVote reserve : reserves){
				if (reserve.getRole().equals( role )){
					if (!reserve.isAlreadyUsed()){
						playerVoteToAdd = reserve;
						if (playerVoteToAdd.getFantaVote() != null) {
							reserve.setAlreadyUsed(true);
							break;
						}
						else {
							reserve.setAlreadyUsed(true);
						}
					}
				}
			}
		}
		if (playerVoteToAdd != null && playerVoteToAdd.getFantaVote() == null)
			playerVoteToAdd = null;
		return playerVoteToAdd;
	}

	private static LineUp getLineUp(Integer startRow, Integer endRow, Integer startColumn, Sheet sheet) {
		LineUp lineUp = new LineUp();

		PlayerVote playerVote;

		String teamName = sheet.getRow(startRow).getCell(startColumn).getStringCellValue();
		lineUp.setTeamName(teamName);

		RoleEnum role;
		String name;
		String team;
		Double vote = 1.0;
		Double fantaVote;
		boolean reserveSection = false;

		int startPlayerRow = startRow + 1;

		for (int i = startPlayerRow; i <= endRow; i++) {
			Row row = sheet.getRow(i);

			if (isPlayerVote(row, startColumn)) {

				role = RoleEnum.valueOf(row.getCell(startColumn).getStringCellValue());
				name = row.getCell(startColumn + 1).getStringCellValue();
				team = row.getCell(startColumn + 2).getStringCellValue();

				vote = getVote(row.getCell(startColumn + 3), row);
				fantaVote = getVote(row.getCell(startColumn + 4), row);

				playerVote = new PlayerVote(role, name, team, vote, fantaVote);
				switch (role) {
				case P:
					if (!reserveSection)
						lineUp.getGoalKeeper().add(playerVote);
					else
						lineUp.getReserves().add(playerVote);
//						lineUp.getGoalKeeperReserve().add(playerVote);
					break;
				case D:
					if (!reserveSection)
						lineUp.getDefenders().add(playerVote);
					else
						lineUp.getReserves().add(playerVote);
//						lineUp.getDefendersReserves().add(playerVote);
					break;
				case C:
					if (!reserveSection)
						lineUp.getMidfielders().add(playerVote);
					else
						lineUp.getReserves().add(playerVote);
//						lineUp.getMidfieldersReserves().add(playerVote);
					break;
				case A:
					if (!reserveSection)
						lineUp.getStrikers().add(playerVote);
					else
						lineUp.getReserves().add(playerVote);
//						lineUp.getStrikersReserves().add(playerVote);
					break;

				default:
					break;
				}
			} else if (isReserveStartRow(row, startColumn)) {
				reserveSection = true;
			} else if (isDefenderModifierRow(row, startColumn)) {
				if (rules.getModifiers().isDefenderModifierActive()){
					Cell cell = row.getCell(startColumn + 4);
					String stringCellValue = cell.getStringCellValue();
					Double modifier = Double.valueOf(stringCellValue.replace(",", "."));
					lineUp.setDefenderModifier(modifier);
				}
			} else if (isStrikerModifierRow(row, startColumn)) {
				if (rules.getModifiers().isStrikerModifierActive()){
					Cell cell = row.getCell(startColumn + 4);
					String stringCellValue = cell.getStringCellValue();
					Double modifier = Double.valueOf(stringCellValue.replace(",", "."));
					lineUp.setStrickerModifier(modifier);
				}
			}

		}
//		System.out.println(lineUp);
		return lineUp;
	}

	private static Double getVote(Cell cell, Row row) {
		String stringCellValue = cell.getStringCellValue();
		Double vote = null;
		if (!stringCellValue.equals("-"))
			vote = Double.valueOf(stringCellValue.replace(",", "."));
		return vote;
	}

	private static boolean isReserveStartRow(Row row, Integer startColumn) {
		Cell cell = row.getCell(startColumn);
		String stringCellValue = cell.getStringCellValue();
		if (stringCellValue.equals("Panchina")) {
			return true;
		}
		return false;
	}

	private static boolean isStrikerModifierRow(Row row, Integer startColumn) {
		Cell cell = row.getCell(startColumn);
		String stringCellValue = cell.getStringCellValue();
		if (stringCellValue.equals("Modificatore attacco")) {
			return true;
		}
		return false;
	}

	private static boolean isDefenderModifierRow(Row row, Integer startColumn) {
		Cell cell = row.getCell(startColumn);
		String stringCellValue = cell.getStringCellValue();
		if (stringCellValue.equals("Modificatore difesa")) {
			return true;
		}
		return false;
	}

	private static boolean isPlayerVote(Row row, Integer startColumn) {
		Cell cell = row.getCell(startColumn);
		String stringCellValue = cell.getStringCellValue();
		if (stringCellValue.length() == 1) {
			return true;
		}

		return false;
	}

	private static void calculateLinesUpEdgeRows(Sheet sheet, int rowsCount, int columnIndex,
			ArrayList<Pair> startingEdges, ArrayList<Pair> endingEdges) {

		for (int i = 4; i <= rowsCount; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				if (isInitial(row, Constants.resultColumnIndex)) {
					startingEdges.add(new Pair(i, columnIndex));
					continue;
				}
				if (isFinal(row, columnIndex)) {
					endingEdges.add(new Pair(i, columnIndex));
					continue;
				}
			}
		}

	}

	private static boolean isInitial(Row row, Integer column) {
		Cell cell = row.getCell(column);
		if (cell != null) {
			String stringCellValue = cell.getStringCellValue();
			if (stringCellValue.contains("-")) {
				return true;
			}
		}
		return false;

	}

	private static boolean isFinal(Row row, Integer column) {
		Cell cell = row.getCell(column);
		if (cell != null) {
			String stringCellValue = cell.getStringCellValue();
			if (stringCellValue.contains("TOTALE")) {
				return true;
			}
		}
		return false;
	}

}