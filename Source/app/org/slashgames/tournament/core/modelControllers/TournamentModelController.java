package org.slashgames.tournament.core.modelControllers;

import java.util.List;

import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.core.models.Tournament;

import play.db.ebean.Model;

public class TournamentModelController {
	private static Model.Finder<Long, Tournament> find = new Model.Finder<Long, Tournament>(
			Long.class, Tournament.class);

	public static List<Tournament> getTournaments() {
		return find.all();
	}

	public static Tournament findById(Long id) {
		return find.byId(id);
	}

	public static void addTournament(TournamentData data) {
		Tournament tournament = new Tournament();
		tournament.name = data.name;
		tournament.date = data.date;
		tournament.location = data.location;
		tournament.judge = data.judge;
		tournament.save();
	}
}
