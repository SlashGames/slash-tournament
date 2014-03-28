package org.slashgames.tournament.auth.formdata;

import org.slashgames.tournament.auth.modelcontrollers.UserModelController;

public class LoginData {
	public String email;
	public String password;

	public String validate() {
		if (UserModelController.getUser(email, password) == null) {
			return "Ungültiger Benutzername oder falsches Passwort";
		}
		return null;
	}
}
