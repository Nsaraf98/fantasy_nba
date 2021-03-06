package stats.api.team;

import java.util.Map;

import stats.api.connection.Connection;
import stats.api.util.Constants;
import stats.api.util.FieldType;
import stats.api.util.Statistic;

public class TeamSeasons extends Statistic {

	private static FieldType[] required_fields = { FieldType.TEAM_ID, FieldType.LEAGUE, FieldType.SEASON_TYPE,
			FieldType.PER_MODE };

	/**
	 * @param endpoint
	 * @param fields
	 * @param required_fields
	 */
	public TeamSeasons(Map<FieldType, Object> fields) {
		super(Constants.Endpoints.TEAM_SEASONS.toString(), fields, required_fields);
	}

	public TeamSeasons(Map<FieldType, Object> fields, Connection c) {
		super(Constants.Endpoints.TEAM_SEASONS.toString(), fields, required_fields, c);
	}

	public enum ItemType implements Statistic.ItemType {
		INFO(0);

		private int index;

		private ItemType(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

}
