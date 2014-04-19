import java.util.List;
import java.util.Map;

import org.slashgames.tournament.tournaments.models.Game;

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

			// Save Hearthstone classes.
			data = (Map<String, List<Object>>) Yaml
					.load("data/hearthstone-classes.yml");

			Ebean.save(data.get("classes"));

			// Save Tournament data.
			data = (Map<String, List<Object>>) Yaml
					.load("data/tournament-formats.yml");

			Ebean.save(data.get("formats"));

			data = (Map<String, List<Object>>) Yaml
					.load("data/tournament-modes.yml");

			Ebean.save(data.get("modes"));
		}
	}
}
