package org.slashgames.tournament.cms.formdata;

import org.slashgames.tournament.core.util.StringHelper;
import org.slashgames.tournament.tournaments.models.Tournament;

public class TournamentData {
	public String name;

	public String date;

	public String location;

	public String judge;

	public String game;

	public String format;

	public String mode;

	public String bestOf;

	public String rules;

	public String status;
	
	public String validate() {
		if (StringHelper.isNullOrEmpty(name)) {
			return "Bitte Turniernamen angeben!";
		}

		if (StringHelper.isNullOrEmpty(date)) {
			return "Bitte Datum angeben!";
		}

		if (StringHelper.isNullOrEmpty(location)) {
			return "Bitte Ort angeben!";
		}

		if (StringHelper.isNullOrEmpty(judge)) {
			return "Bitte Judge angeben!";
		}

		if (StringHelper.isNullOrEmpty(game)) {
			return "Bitte Spiel angeben!";
		}

		if (StringHelper.isNullOrEmpty(format)) {
			return "Bitte Turnierformat angeben!";
		}

		if (StringHelper.isNullOrEmpty(mode)) {
			return "Bitte Turniermodus angeben!";
		}

		if (StringHelper.isNullOrEmpty(bestOf)) {
			return "Bitte Best-Of angeben!";
		}

		try {
			int bo = Integer.parseInt(bestOf);

			if (bo < 0 || bo % 2 == 0) {
				return "Bitte gültiges Best-Of (1,3,5,...) angeben!";
			}
		} catch (NumberFormatException e) {
			return "Bitte gültiges Best-Of (1,3,5,...) angeben!";
		}

		return null;
	}

	public void fill(Tournament tournament) {
		this.date = tournament.date;
		this.game = tournament.game.name;
		this.judge = tournament.judge;
		this.location = tournament.location;
		this.name = tournament.name;
		this.bestOf = String.valueOf(tournament.bestOf);
		this.format = tournament.format.name;
		this.mode = tournament.mode.name;
		this.rules = tournament.rules;
		this.status = tournament.status.name();
	}
}
