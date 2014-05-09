package org.slashgames.tournament.auth.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Token extends Model {
	public String email;
	
	public String code;
	
    /**
     * Unique id of this token action.
     */
    @Id
    public Long id;
    
    @Enumerated(EnumType.STRING)
    public TokenType type;
    
    /**
     * Time this token action expires.
     */
    public Date expires;
    
    /**
     * Token action type (e.g. token for mail verification or passwort reset).
     * 
     * @author Nick Pruehs
     */
    public enum TokenType
    {
        PASSWORD_RESET
    }
    
    public boolean isValid()
    {
        return this.expires.after(new Date());
    }
}
