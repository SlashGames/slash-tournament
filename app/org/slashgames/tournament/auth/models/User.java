package org.slashgames.tournament.auth.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name = "tournament_user")
public class User extends Model {
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -3046645594720182319L;

	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String email;

	@Constraints.Required
	@Formats.NonEmpty
	public String name;

	@Constraints.Required
	public String password;

	public boolean isAdmin;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return email;
	}
}
