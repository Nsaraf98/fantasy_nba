package stats.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import stats.api.connection.Connection;
import stats.api.game.Game;
import stats.api.league.League;
import stats.api.player.Player;
import stats.api.player.PlayerList;
import stats.api.player.PlayerVsPlayer;
import stats.api.team.Team;
import stats.api.team.TeamList;
import stats.api.team.TeamVsPlayer;
import stats.api.util.Constants;
import stats.api.util.FieldType;
import stats.api.combine.DraftCombine;

public class StatsFactory {

	private static Connection connection;
	private static PlayerList player_list;

	static {
		establishConnection();
	}

	public static void establishConnection() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("referer", Constants.SCORES_REFERER.toString());
		connection = new Connection(Constants.API_URL.toString(), headers);
		setUpdatedDefaults();
	}

	public static Connection getConnection() {
		if (connection == null) {
			establishConnection();
		}
		return connection;
	}

	public static void setUpdatedDefaults() {
		LocalDate date = LocalDate.now();
		int year = date.getYear();
		int month_val = date.getMonthValue();
		if (month_val <= 8) {
			year = year - 1;
		}
		String seasonYear = year + "-" + (year % 100 + 1);
		String month = "" + date.getMonthValue();
		String day = "" + date.getDayOfMonth();
		if (day.length() == 1) {
			day = "0" + day;
		}
		if (month.length() == 1) {
			month = "0" + month;
		}
		String dateToday = month + "/" + day + "/" + date.getYear();
		FieldType.GAME_DATE.setDefault(dateToday);
		FieldType.SEASON.setDefault(seasonYear);
		FieldType.SEASON_YEAR.setDefault(seasonYear);
	}

	public static Scoreboard getTodayScoreboard() {
		Map<FieldType, Object> fields = new HashMap<FieldType, Object>();
		Scoreboard score = new Scoreboard(fields, connection);
		return score;
	}

	public static Scoreboard getScoreboard(String game_date) {
		Map<FieldType, Object> fields = new HashMap<FieldType, Object>();
		fields.put(FieldType.GAME_DATE, game_date);
		Scoreboard score = new Scoreboard(fields, connection);
		return score;
	}

	public static Game getGame(String game_id) {
		return new Game(game_id);
	}

	public static PlayerList getPlayerList() {
		if (player_list == null) {
			Map<FieldType, Object> fields = new HashMap<FieldType, Object>();
			player_list = new PlayerList(fields, connection);
		}
		return player_list;
	}

	public static Player getPlayer(String category, String value) {
		return getPlayerList().getPlayerBy(category, value);

	}

	public static List<Player> getPlayers(String category, String value) {
		return getPlayerList().getPlayersBy(category, value);

	}

	public static Team getTeam(String team_id) {
		return new Team(team_id);
	}

	public static TeamList getTeamList() {
		return new TeamList(getConnection());
	}

	public static League getLeague() {
		return new League();

	}

	public static DraftCombine getCombine(String year) {
		return new DraftCombine(year);
	}

	public static PlayerVsPlayer getPlayerVsPlayer(String player1, String player2) {
		Map<FieldType, Object> fields = new HashMap<FieldType, Object>();
		fields.put(FieldType.PLAYER_ID, player1);
		fields.put(FieldType.VS_PLAYER_ID, player2);
		return new PlayerVsPlayer(fields, getConnection());
	}

	public static TeamVsPlayer getTeamVsPlayer(String team, String player) {
		Map<FieldType, Object> fields = new HashMap<FieldType, Object>();
		fields.put(FieldType.TEAM_ID, team);
		fields.put(FieldType.VS_PLAYER_ID, player);
		return new TeamVsPlayer(fields, getConnection());
	}
}
