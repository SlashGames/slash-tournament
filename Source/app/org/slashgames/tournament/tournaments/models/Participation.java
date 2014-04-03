package org.slashgames.tournament.tournaments.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.slashgames.tournament.auth.models.User;

import play.db.ebean.Model;

@Entity
public class Participation extends Model{
	@Id
	public Long id;

	@ManyToOne
	public Tournament tournament;

	@ManyToOne
	public User participant;

}
