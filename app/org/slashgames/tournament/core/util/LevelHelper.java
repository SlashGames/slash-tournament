package org.slashgames.tournament.core.util;

public class LevelHelper {
    public static double XpToLevel(int xp) {
    	return 0.1*(Math.sqrt(5)*Math.sqrt(2*xp + 405) -45) + 1;
    }
    
    public static double LevelToXp(int level) {
    	level = level - 1;
    	return 10*level*level+90*level;
    }
}
