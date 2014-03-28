package org.slashgames.tournament.cms.formdata;

import org.slashgames.tournament.core.models.Game;
import org.slashgames.tournament.util.StringHelper;

public class TournamentData {
	public String name;

	public String date;

	public String location;

	public String judge;

	public Game game;

	public String validate() {
		if (StringHelper.IsNullOrEmpty(name)) {
			return "Bitte Turniernamen angeben!";
		}

		return null;
	}
}
