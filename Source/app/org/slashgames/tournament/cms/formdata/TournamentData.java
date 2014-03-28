package org.slashgames.tournament.cms.formdata;

import org.slashgames.tournament.util.StringHelper;

public class TournamentData {
	public String name;

	public String date;

	public String location;

	public String judge;

	public String game;

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

		return null;
	}
}
