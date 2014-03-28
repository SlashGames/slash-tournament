import java.util.List;
import java.util.Map;

import org.slashgames.tournament.core.models.Game;

import play.Application;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class InitialData {
	@SuppressWarnings("unchecked")
	public static void insert(Application app) {
		if (Ebean.find(Game.class).findRowCount() == 0) {
			// Save games.
			Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml
					.load("data/games.yml");

			Ebean.save(data.get("games"));
		}
	}
}
