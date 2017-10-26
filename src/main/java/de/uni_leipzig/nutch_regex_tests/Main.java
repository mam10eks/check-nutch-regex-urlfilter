package de.uni_leipzig.nutch_regex_tests;

import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		new RegexCreator(new File(args[0])).writeRegexFileTo(new File(args[1]));
	}
}
