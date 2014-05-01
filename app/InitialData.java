import java.util.List;
import java.util.Map;

import org.slashgames.tournament.hearthstone.models.HearthstoneClass;
import org.slashgames.tournament.tournaments.models.Game;
import org.slashgames.tournament.tournaments.models.GameRules;
import org.slashgames.tournament.tournaments.models.TournamentFormat;
import org.slashgames.tournament.tournaments.models.TournamentMode;

import play.Application;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class InitialData {
	@SuppressWarnings("unchecked")
	public static void insert(Application app) {
		if (Ebean.find(Game.class).findRowCount() == 0) {
			// Save games.
			Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml
					.load("data/games.yml");

			Logger.info("Saving games to database.");
			
			Ebean.save(data.get("games"));
		}
		
		if (Ebean.find(GameRules.class).findRowCount() == 0) {
			// Save games.
			Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml
					.load("data/games.yml");

			Logger.info("Saving game rules to database.");
			
			Ebean.save(data.get("rules"));
		}
		
		if (Ebean.find(HearthstoneClass.class).findRowCount() == 0) {
			// Save Hearthstone classes.
			Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml
					.load("data/hearthstone-classes.yml");

			Logger.info("Saving Hearthstone classes to database.");
			
			Ebean.save(data.get("classes"));
		}
		
		
		if (Ebean.find(TournamentFormat.class).findRowCount() == 0) {
			// Save Tournament formats.
			Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml
					.load("data/tournament-formats.yml");

			Logger.info("Saving Tournament formats to database.");
			
			Ebean.save(data.get("formats"));
		}
		
		if (Ebean.find(TournamentMode.class).findRowCount() == 0) {
			// Save Tournament modes.
			Map<String, List<Object>> data = (Map<String, List<Object>>) Yaml
					.load("data/tournament-modes.yml");

			Logger.info("Saving Tournament modes to database.");
			
			Ebean.save(data.get("modes"));
		}
	}
}
