package org.slashgames.tournament.tournaments.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Tournament extends Model {
	@Id
	public Long id;

	public String name;

	public String date;

	public String location;

	public String judge;

	@ManyToOne
	public Game game;

	@ManyToOne
	public TournamentFormat format;

	@ManyToOne
	public TournamentMode mode;

	public Integer bestOf;

	@Column(length = 32768)
	public String rules;
}
