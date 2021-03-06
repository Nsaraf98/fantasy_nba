package stats.api.team;

import java.util.HashMap;

import stats.api.connection.Connection;
import stats.api.util.Constants;
import stats.api.util.FieldType;
import stats.api.util.StatItem;
import stats.api.util.Statistic;

public class TeamList extends Statistic {

	private static FieldType[] required_fields = { FieldType.LEAGUE };

	/**
	 * @param fields
	 * @param required_fields
	 */
	public TeamList() {
		super(Constants.Endpoints.TEAM_LIST.toString(), new HashMap<FieldType, Object>(), required_fields);
	}

	public TeamList(Connection c) {
		super(Constants.Endpoints.TEAM_LIST.toString(), new HashMap<FieldType, Object>(), required_fields, c);
	}

	public String getIDFromCode(String team_code) {
		StatItem item = getItem(0);
		return item.getDataPoint("TEAM_ID", item.getRowContaining("ABBREVIATION", team_code)).toString();
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
