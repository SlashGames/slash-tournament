package org.slashgames.tournament.hearthstone.modelcontrollers;

import java.util.List;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.hearthstone.formdata.HearthstoneParticipationData;
import org.slashgames.tournament.hearthstone.models.HearthstoneDeck;
import org.slashgames.tournament.hearthstone.models.HearthstoneParticipation;
import org.slashgames.tournament.tournaments.modelcontrollers.ParticipationModelController;
import org.slashgames.tournament.tournaments.models.Participation;
import org.slashgames.tournament.tournaments.models.ParticipationStatus;
import org.slashgames.tournament.tournaments.models.Tournament;
import org.slashgames.tournament.tournaments.models.TournamentStatus;

import play.db.ebean.Model;

public class HearthstoneParticipationModelController {
	private static Model.Finder<Long, HearthstoneParticipation> find = new Model.Finder<Long, HearthstoneParticipation>(
			Long.class, HearthstoneParticipation.class);

	public static List<HearthstoneParticipation> getParticipations(
			Tournament tournament) {
		return find.fetch("participation").where().eq("participation.tournament", tournament).findList();
	}

	public static HearthstoneParticipation getParticipation(User user,
			Tournament tournament) {
		return find.fetch("participation").where().eq("participation.participant", user)
				.eq("participation.tournament", tournament).findUnique();
	}

	public static HearthstoneParticipation getParticipation(Long participationId) {
		return find.byId(participationId);
	}

	public static boolean isParticipating(User user, Tournament tournament) {
		return getParticipation(user, tournament) != null;
	}

	public static void addOrUpdateParticipation(User participant,
			Tournament tournament, HearthstoneParticipationData data) {

		Participation participation = ParticipationModelController.getParticipation(participant, tournament);
		HearthstoneParticipation hearthstoneParticipation = getParticipation(participant,
				tournament);

		if (hearthstoneParticipation == null) {
			// Don't allow to participate when signup is closed.
			if (tournament.status != TournamentStatus.SIGNUP && tournament.status != TournamentStatus.CHECKIN) {
				return;
			}
			
			hearthstoneParticipation = new HearthstoneParticipation();
			hearthstoneParticipation.participation = participation;
			
			// Marked as checked in.
			hearthstoneParticipation.participation.participationStatus = ParticipationStatus.CHECKED_IN;
			hearthstoneParticipation.participation.save();
		}

		hearthstoneParticipation.battleTag = data.battleTag;

		hearthstoneParticipation.deck1 = new HearthstoneDeck();
		hearthstoneParticipation.deck1.deckLink = data.deck1Link;
		hearthstoneParticipation.deck1.heroClass = HearthstoneClassModelController
				.findByName(data.deck1Class);
		hearthstoneParticipation.deck1.save();

		hearthstoneParticipation.deck2 = new HearthstoneDeck();
		hearthstoneParticipation.deck2.deckLink = data.deck2Link;
		hearthstoneParticipation.deck2.heroClass = HearthstoneClassModelController
				.findByName(data.deck2Class);
		hearthstoneParticipation.deck2.save();

		hearthstoneParticipation.deck3 = new HearthstoneDeck();
		hearthstoneParticipation.deck3.deckLink = data.deck3Link;
		hearthstoneParticipation.deck3.heroClass = HearthstoneClassModelController
				.findByName(data.deck3Class);
		hearthstoneParticipation.deck3.save();

		hearthstoneParticipation.seedRank = Integer.parseInt(data.seedRank);

		hearthstoneParticipation.save();
	}

	public static void removeParticipation(User participant,
			Tournament tournament) {
		HearthstoneParticipation hearthstoneParticipation = getParticipation(participant, tournament);
	
		if (hearthstoneParticipation != null) {
			// Reset participation status.
			hearthstoneParticipation.participation.participationStatus = ParticipationStatus.SIGNED_UP;
			hearthstoneParticipation.participation.save();
			
			// Remove hearthstone participation.
			hearthstoneParticipation.delete();
		}
	}
}
