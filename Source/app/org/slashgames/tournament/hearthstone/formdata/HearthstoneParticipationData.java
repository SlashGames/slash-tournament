package org.slashgames.tournament.hearthstone.formdata;

import org.slashgames.tournament.core.util.StringHelper;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;

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

	public void fill(HearthstoneParticipation data) {
		this.battleTag = data.battleTag;
		this.deck1Class = data.deck1.heroClass.name;
		this.deck1Link = data.deck1.deckLink;
		this.deck2Class = data.deck2.heroClass.name;
		this.deck2Link = data.deck2.deckLink;
		this.deck3Class = data.deck3.heroClass.name;
		this.deck3Link = data.deck3.deckLink;
		this.seedRank = Integer.toString(data.seedRank);
	}
}
