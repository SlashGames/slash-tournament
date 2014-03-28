package org.slashgames.tournament.auth.controllers;

import static play.data.Form.form;

import org.slashgames.tournament.auth.formdata.LoginData;
import org.slashgames.tournament.auth.formdata.SignupData;
import org.slashgames.tournament.auth.modelcontrollers.UserModelController;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.auth.util.PasswordEncryption;

import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class LoginController extends Controller {
	public static final String SESSION_CURRENT_USER = "currentUser";

	public static User getCurrentUser() {
		String email = session(SESSION_CURRENT_USER);
		return UserModelController.getUser(email);
	}

	public static boolean isLoggedIn() {
		return getCurrentUser() != null;
	}

	public static Result login() {
		return ok(org.slashgames.tournament.auth.views.html.login
				.render(form(LoginData.class)));
	}

	public static Result loginSubmit() {
		Form<LoginData> loginForm = form(LoginData.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(org.slashgames.tournament.auth.views.html.login
					.render(loginForm));
		} else {
			session(SESSION_CURRENT_USER, loginForm.get().email);
			return redirect(org.slashgames.tournament.core.controllers.routes.Application
					.index());
		}
	}

	public static Result signup() {
		return ok(org.slashgames.tournament.auth.views.html.signup
				.render(form(SignupData.class)));
	}

	public static Result signupSubmit() {
		Form<SignupData> signupForm = form(SignupData.class).bindFromRequest();
		if (signupForm.hasErrors()) {
			return badRequest(org.slashgames.tournament.auth.views.html.signup
					.render(signupForm));
		} else {
			SignupData data = signupForm.get();
			String email = data.email;
			String password = PasswordEncryption.encryptPassword(data.password);
			User newUser = UserModelController.addUser(email, password);
			Logger.info("User added: " + email + " - " + password);
			session(SESSION_CURRENT_USER, newUser.email);

			return redirect(org.slashgames.tournament.core.controllers.routes.Application
					.index());
		}
	}

	public static Result logout() {
		session().clear();
		return redirect(org.slashgames.tournament.core.controllers.routes.Application
				.index());
	}
}
