package org.slashgames.tournament.auth.modelcontrollers;

import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.auth.util.PasswordEncryption;

import play.Logger;
import play.db.ebean.Model;

public class UserModelController {
	private static Model.Finder<String, User> find = new Model.Finder<String, User>(
			String.class, User.class);

	public static User addUser(String name, String email, String password) {
		User user = new User(email, password);
		user.name = name;
		user.save();

		return user;
	}

	public static User getUser(String email) {
		return find.where().eq("email", email).findUnique();
	}

	public static User getUser(String email, String password) {
		String encryptedPassword = PasswordEncryption.encryptPassword(password);
		Logger.info("Looking up user: " + email + " - " + encryptedPassword);
		return find.where().eq("email", email)
				.eq("password", encryptedPassword).findUnique();
	}
}
