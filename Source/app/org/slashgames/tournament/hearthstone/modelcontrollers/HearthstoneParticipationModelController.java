package org.slashgames.tournament.hearthstone.modelcontrollers;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.core.models.Tournament;
import org.slashgames.tournament.hearthstone.formdata.HearthstoneParticipationData;
import org.slashgames.tournament.hearthstone.models.HearthstoneDeck;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;

import play.db.ebean.Model;

public class HearthstoneParticipationModelController {
	private static Model.Finder<Long, HearthstoneParticipation> find = new Model.Finder<Long, HearthstoneParticipation>(
			Long.class, HearthstoneParticipation.class);

	public static HearthstoneParticipation getParticipation(User user,
			Tournament tournament) {
		return find.where().eq("participant", user)
				.eq("tournament", tournament).findUnique();
	}

	public static boolean isParticipating(User user, Tournament tournament) {
		return getParticipation(user, tournament) != null;
	}

	public static void addOrUpdateParticipation(User participant,
			Tournament tournament, HearthstoneParticipationData data) {

		HearthstoneParticipation participation = getParticipation(participant,
				tournament);

		if (participation == null) {
			participation = new HearthstoneParticipation();
		}

		participation.participant = participant;
		participation.tournament = tournament;

		participation.battleTag = data.battleTag;

		participation.deck1 = new HearthstoneDeck();
		participation.deck1.deckLink = data.deck1Link;
		participation.deck1.heroClass = HearthstoneClassModelController
				.findByName(data.deck1Class);
		participation.deck1.save();

		participation.deck2 = new HearthstoneDeck();
		participation.deck2.deckLink = data.deck2Link;
		participation.deck2.heroClass = HearthstoneClassModelController
				.findByName(data.deck2Class);
		participation.deck2.save();

		participation.deck3 = new HearthstoneDeck();
		participation.deck3.deckLink = data.deck3Link;
		participation.deck3.heroClass = HearthstoneClassModelController
				.findByName(data.deck3Class);
		participation.deck3.save();

		participation.seedRank = Integer.parseInt(data.seedRank);

		participation.save();
	}
}
