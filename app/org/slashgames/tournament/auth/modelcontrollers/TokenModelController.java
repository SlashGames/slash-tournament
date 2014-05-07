package org.slashgames.tournament.auth.modelcontrollers;

import java.util.Date;
import java.util.Random;

import org.slashgames.tournament.auth.models.Token;
import org.slashgames.tournament.auth.models.Token.TokenType;
import org.slashgames.tournament.auth.models.User;

import play.Logger;
import play.db.ebean.Model;

import org.joda.time.DateTime;

import com.avaje.ebean.Ebean;

public class TokenModelController {
    private static final char[] codeSymbols = "3456789ABCDEFGHJKLMNPQRSTUVWXY".toCharArray();
    
    private static final Random random = new Random();
    
    private static final int codeLength = 5;
    
	private static Model.Finder<String, Token> find = new Model.Finder<String, Token>(
			String.class, Token.class);

	public static Token addToken(User user, TokenType type) {
		Token token = getUserToken(user, type);
		
		if (token == null) {
			token = new Token();
			token.email = user.email;
			token.type = type;
		}
		
		// Generate new token.
		updateToken(token);

		return token;
	}

	public static void updateToken(Token token) {
		// Generate code.
		token.code = generateCode();
		
		while (findByCode(token.code) != null) {
			Logger.warn(String.format("Token with code %s already exists, generating new one."));
			token.code = generateCode();
		}

		// Set expiration date.
		token.expires = DateTime.now().plusHours(1).toDate();
		
		token.save();
	}
	
	public static void deleteToken(Token token) {
		Ebean.delete(token);
	}
	
	public static Token findByCode(String code) {
		return find.where().eq("code", code).findUnique();
	}
	
	public static Token getUserToken(User user, TokenType type) {
		return find.where().eq("email", user.email).eq("type", type.toString()).findUnique();
	}
	
	private static String generateCode() {
		char[] buffer = new char[codeLength];
		for (int i = 0; i < codeLength; i++)
        {
			buffer[i] = codeSymbols[random.nextInt(codeSymbols.length)];
        }
        return new String(buffer);
	}
}
