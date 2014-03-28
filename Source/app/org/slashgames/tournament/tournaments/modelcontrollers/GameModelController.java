package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.slashgames.tournament.tournaments.models.Game;

import play.db.ebean.Model;

public class GameModelController {
	private static Model.Finder<String, Game> find = new Model.Finder<String, Game>(
			String.class, Game.class);

	public static List<Game> getGames() {
		return find.all();
	}

	public static List<String> getGameNames() {
		List<Game> games = getGames();
		List<String> gameNames = new ArrayList<String>();

		for (Game game : games) {
			gameNames.add(game.name);
		}

		return gameNames;
	}

	public static Game findByName(String name) {
		return find.byId(name);
	}
}
