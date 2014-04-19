package org.slashgames.tournament.hearthstone.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class HearthstoneClass extends Model {
	@Id
	public String name;
}
