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

	public String validate() {
		if (StringHelper.IsNullOrEmpty(name)) {
			return "Bitte Turniernamen angeben!";
		}

		if (StringHelper.IsNullOrEmpty(date)) {
			return "Bitte Datum angeben!";
		}

		if (StringHelper.IsNullOrEmpty(location)) {
			return "Bitte Ort angeben!";
		}

		if (StringHelper.IsNullOrEmpty(judge)) {
			return "Bitte Judge angeben!";
		}

		if (StringHelper.IsNullOrEmpty(game)) {
			return "Bitte Spiel angeben!";
		}

		if (StringHelper.IsNullOrEmpty(format)) {
			return "Bitte Turnierformat angeben!";
		}

		if (StringHelper.IsNullOrEmpty(mode)) {
			return "Bitte Turniermodus angeben!";
		}

		if (StringHelper.IsNullOrEmpty(bestOf)) {
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
	}
}
