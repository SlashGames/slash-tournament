package org.slashgames.tournament.tournaments.modelcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.slashgames.tournament.cms.formdata.TournamentData;
import org.slashgames.tournament.tournaments.models.Tournament;
import org.slashgames.tournament.tournaments.models.TournamentStatus;

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
		updateTournament(tournament, data);
	}

	public static List<String> getTournamentStatusStrings() {
		List<String> states = new ArrayList<String>();
		for (TournamentStatus status : TournamentStatus.values()) {
			states.add(status.name());
		}
		return states;
	}
	
	public static void updateTournament(Tournament tournament,
			TournamentData data) {
		tournament.name = data.name;
		tournament.date = data.date;
		tournament.location = data.location;
		tournament.judge = data.judge;
		tournament.game = GameModelController.findByName(data.game);
		tournament.bestOf = Integer.parseInt(data.bestOf);
		tournament.format = TournamentFormatModelController
				.findByName(data.format);
		tournament.mode = TournamentModeModelController.findByName(data.mode);
		tournament.rules = data.rules;
		tournament.status = TournamentStatus.valueOf(data.status);
		tournament.save();
	}
}
