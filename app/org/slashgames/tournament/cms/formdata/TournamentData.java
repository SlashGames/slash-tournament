package org.slashgames.tournament.cms.formdata;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slashgames.tournament.core.util.StringHelper;
import org.slashgames.tournament.tournaments.models.Tournament;

public class TournamentData {
	public static DateTimeFormatter DATE_TIME_FORM_FORMATTER = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm");
	
	public String name;

	public String date;

	public String location;

	public String googleMapsUrl;
	
	public String judge;

	public Long gameRules;
	
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

		if (date == null) {
			return "Bitte Datum angeben!";
		}

		if (StringHelper.isNullOrEmpty(location)) {
			return "Bitte Ort angeben!";
		}

		if (StringHelper.isNullOrEmpty(googleMapsUrl)) {
			return "Bitte Google Maps-URL angeben!";
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
		this.date = DATE_TIME_FORM_FORMATTER.print(tournament.date);
		this.game = tournament.game.name;
		this.gameRules = tournament.gameRules.id;
		this.judge = tournament.judge;
		this.location = tournament.location;
		this.googleMapsUrl = tournament.googleMapsUrl;
		this.name = tournament.name;
		this.bestOf = String.valueOf(tournament.bestOf);
		this.format = tournament.format.name;
		this.mode = tournament.mode.name;
		this.rules = tournament.rules;
		this.status = tournament.status.name();
	}
}
