package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.slashgames.tournament.tournaments.models.TournamentFormat;

import play.db.ebean.Model;

public class TournamentFormatModelController {
	private static Model.Finder<String, TournamentFormat> find = new Model.Finder<String, TournamentFormat>(
			String.class, TournamentFormat.class);

	public static List<TournamentFormat> getFormats() {
		return find.all();
	}

	public static List<String> getFormatNames() {
		List<TournamentFormat> formats = getFormats();
		List<String> formatNames = new ArrayList<String>();

		for (TournamentFormat format : formats) {
			formatNames.add(format.name);
		}

		return formatNames;
	}

	public static TournamentFormat findByName(String name) {
		return find.byId(name);
	}
}
