package org.slashgames.tournament.hearthstone.formdata;

import org.slashgames.tournament.util.StringHelper;

public class HearthstoneParticipationData {
	public String battleTag;

	public String deck1Link;
	public String deck1Class;

	public String deck2Link;
	public String deck2Class;

	public String deck3Link;
	public String deck3Class;

	public String seedRank;

	public String validate() {
		if (StringHelper.IsNullOrEmpty(battleTag)) {
			return "Bitte BattleTag angeben!";
		}

		if (StringHelper.IsNullOrEmpty(deck1Link)
				|| StringHelper.IsNullOrEmpty(deck2Link)
				|| StringHelper.IsNullOrEmpty(deck3Link)) {
			return "Bitte alle Decklisten angeben!";
		}

		if (StringHelper.IsNullOrEmpty(seedRank)) {
			return "Bitte aktuellen Battle.Net-Rang angeben!";
		}

		try {
			int rank = Integer.parseInt(seedRank);

			if (rank < 0 || rank > 25) {
				return "Bitte aktuellen Battle.Net-Rang (1-25) angeben!";
			}
		} catch (NumberFormatException e) {
			return "Bitte aktuellen Battle.Net-Rang (1-25) angeben!";
		}

		return null;
	}
}
