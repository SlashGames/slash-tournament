package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.slashgames.tournament.tournaments.models.TournamentMode;

import play.db.ebean.Model;

public class TournamentModeModelController {
	private static Model.Finder<String, TournamentMode> find = new Model.Finder<String, TournamentMode>(
			String.class, TournamentMode.class);

	public static List<TournamentMode> getModes() {
		return find.all();
	}

	public static List<String> getModeNames() {
		List<TournamentMode> modes = getModes();
		List<String> modeNames = new ArrayList<String>();

		for (TournamentMode mode : modes) {
			modeNames.add(mode.name);
		}

		return modeNames;
	}

	public static TournamentMode findByName(String name) {
		return find.byId(name);
	}
}
