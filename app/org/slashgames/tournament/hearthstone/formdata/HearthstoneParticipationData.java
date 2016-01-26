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
		if (StringHelper.isNullOrEmpty(battleTag)) {
			return "Bitte BattleTag angeben!";
		}

		if (StringHelper.isNullOrEmpty(deck1Link)
				|| StringHelper.isNullOrEmpty(deck2Link)
				|| StringHelper.isNullOrEmpty(deck3Link)) {
			return "Bitte alle Decklisten angeben!";
		}

		if (StringHelper.isNullOrEmpty(seedRank)) {
			return "Bitte aktuellen Battle.Net-Rang angeben!";
		}

		try {
			int rank = Integer.parseInt(seedRank);

			if (rank < 1) {
				return "Bitte Gesamtheldenlevel angeben!";
			}
		} catch (NumberFormatException e) {
			return "Bitte Gesamtheldenlevel angeben!";
		}

		// Classes must not be picked more than one.
		if (deck1Class.equals(deck2Class) || deck1Class.equals(deck3Class) || deck2Class.equals(deck3Class)) {
			return "Keine Klasse darf doppelt gewählt werden.";
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
