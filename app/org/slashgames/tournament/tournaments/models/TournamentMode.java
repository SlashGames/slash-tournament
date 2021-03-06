package org.slashgames.tournament.tournaments.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class TournamentMode extends Model {
	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String name;
}
