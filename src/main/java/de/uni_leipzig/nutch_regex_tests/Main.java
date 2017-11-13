package de.uni_leipzig.nutch_regex_tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main
{
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args)
	{
		LOGGER.info("Create file '{}' out of examples located in: '{}'", args[1], args[0]);
		new RegexCreator(new File(args[0])).writeRegexFileTo(new File(args[1]));
	}
}
