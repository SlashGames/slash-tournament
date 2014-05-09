package org.slashgames.tournament.auth.controllers;

import static play.data.Form.form;

import org.slashgames.tournament.auth.formdata.ForgotPasswordData;
import org.slashgames.tournament.auth.formdata.LoginData;
import org.slashgames.tournament.auth.formdata.ResetPasswordData;
import org.slashgames.tournament.auth.formdata.SignupData;
import org.slashgames.tournament.auth.modelcontrollers.TokenModelController;
import org.slashgames.tournament.auth.modelcontrollers.UserModelController;
import org.slashgames.tournament.auth.models.Token;
import org.slashgames.tournament.auth.models.Token.TokenType;
import org.slashgames.tournament.auth.models.User;
import org.slashgames.tournament.auth.util.PasswordEncryption;

import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import com.typesafe.plugin.*;

public class LoginController extends Controller {
	public static final String SESSION_CURRENT_USER = "currentUser";

	public static User getCurrentUser() {
		String email = session(SESSION_CURRENT_USER);
		return UserModelController.getUser(email);
	}

	public static boolean isLoggedIn() {
		return getCurrentUser() != null;
	}

	public static boolean isAdmin() {
		User currentUser = getCurrentUser();
		return currentUser != null && currentUser.isAdmin;
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
			String name = data.name;
			String email = data.email;
			String password = PasswordEncryption.encryptPassword(data.password);
			User newUser = UserModelController.addUser(name, email, password);
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
	
	public static Result forgotPassword() {
		return ok(org.slashgames.tournament.auth.views.html.forgotPassword
				.render(form(ForgotPasswordData.class)));
	}
	
	public static Result resetPassword() {
		Form<ForgotPasswordData> form = form(ForgotPasswordData.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(org.slashgames.tournament.auth.views.html.forgotPassword
					.render(form));
		}
		
		// Get form data.
		ForgotPasswordData data = form.get();
		String email = data.email;
		User user = UserModelController.getUser(email);
		
		if (user != null) {
			// Create token.
			Token token = TokenModelController.addToken(user, TokenType.PASSWORD_RESET);
			
			// Send mail.
			String mailBody = org.slashgames.tournament.auth.views.txt.mails.resetPasswordMail.render(user.name, token.code).body();
			
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("Passwort vergessen");
			mail.addRecipient(String.format("%s <%s>", user.name, email));
			mail.addFrom("slash games Turniersystem <noreply@slash-tournament.slashgames.cloudbees.net");
			mail.send(mailBody);
		}

		return ok(org.slashgames.tournament.auth.views.html.resetPassword
				.render(form(ResetPasswordData.class), email));
	}
	
	public static Result resetPasswordSubmit(String email) {
		Form<ResetPasswordData> form = form(ResetPasswordData.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(org.slashgames.tournament.auth.views.html.resetPassword
					.render(form, email));
		}
		
		ResetPasswordData data = form.get();
		
		User user = UserModelController.getUser(email);
		String code = data.code;
		String password = PasswordEncryption.encryptPassword(data.password);
		
		Token token = TokenModelController.getUserToken(user, TokenType.PASSWORD_RESET);
		
		if (token == null || !token.isValid() || !token.code.equals(code.toUpperCase())) {
			TokenModelController.deleteToken(token);
			
			return ok(org.slashgames.tournament.core.views.html.message
					.render("Fehler", "Der angegebene Code ist abgelaufen oder ungültig. Bitte fordere einen neuen an!", "Zurück zum Login", org.slashgames.tournament.auth.controllers.routes.LoginController.login()));
		} else {
			TokenModelController.deleteToken(token);
			UserModelController.changePasswort(user, password);
			
			return ok(org.slashgames.tournament.core.views.html.message
					.render("Erfolg", "Dein Password wurde geändert!", "Zurück zum Login", org.slashgames.tournament.auth.controllers.routes.LoginController.login()));
		}
	}
}
