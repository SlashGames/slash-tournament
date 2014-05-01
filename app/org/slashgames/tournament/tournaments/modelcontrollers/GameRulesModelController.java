package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.List;

import org.slashgames.tournament.tournaments.models.GameRules;

import play.db.ebean.Model;

public class GameRulesModelController {
	private static Model.Finder<Long, GameRules> find = new Model.Finder<Long, GameRules>(
			Long.class, GameRules.class);
	
	public static List<GameRules> getGameRules() {
		return find.all();
	}
	
	public static GameRules findById(Long id) {
		return find.byId(id);
	}
}
