package de.uni_leipzig.nutch_regex_tests;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IOUtils
{
	@SneakyThrows
	public static String extractContentOfFileWithMatchingName(File[] files, String filename)
	{
		if(files == null || filename == null)
		{
			return null;
		}

		List<File> matchingFiles = Arrays.asList(files).stream()
			.filter(f -> filename.equals(f.getName()))
			.collect(Collectors.toList());
		
		return matchingFiles.size() == 1 ? new String(Files.readAllBytes(matchingFiles.get(0).toPath())) : null;
	}
}
