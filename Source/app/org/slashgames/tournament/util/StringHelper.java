package org.slashgames.tournament.util;

public class StringHelper
{
    public static boolean IsNullOrEmpty(String s)
    {
        return s == null || s.length() < 1;
    }
}
