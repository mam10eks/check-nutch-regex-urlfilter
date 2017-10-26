package de.uni_leipzig.nutch_regex_tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class RegexCreator
{
	public static String FILE_BLACK = "black.txt";
	
	public static String FILE_WHITE = "white.txt";
	
	public static String FILE_REGEX = "url-regex.txt";
	
	public static void main(String[] args)
	{
		writeRegexFileTo(new File("result_url_regex.txt"));
	}
	
	@SneakyThrows
	public static void writeRegexFileTo(File file)
	{
		if(file.exists())
		{
			file.delete();
		}
		
		file.createNewFile();
		Files.write(file.toPath(), constructConcatenatedRegexFileContent(getAllRegexDirectories()).getBytes());
	}
	
	public static String constructConcatenatedRegexFileContent(File[] regexDirectories)
	{
		return Arrays.asList(regexDirectories).stream()
			.map(f -> "# "+ f.getName() +"\n"+ extractContentOfFile(f.listFiles(), FILE_REGEX))
			.collect(Collectors.joining("\n\n"));
	}
	
	public static final File[] getAllRegexDirectories()
	{
		File resourcesDirectory = Paths.get("src"+ File.separator +"main"+ File.separator +"resources").toFile();
		File[] ret = resourcesDirectory.listFiles();
		
		if(!Arrays.asList(ret).stream().allMatch(RegexCreator::isFileRegexDirectory))
		{
			throw new RuntimeException();
		}
		
		return ret;
	}
	
	private static boolean isFileRegexDirectory(File file)
	{
		String tmpContent = null;
		
		return file != null && file.isDirectory() && file.listFiles() != null && file.listFiles().length == 3
				&& (tmpContent = extractContentOfFile(file.listFiles(), FILE_BLACK)) != null && !tmpContent.trim().isEmpty()
				&& (tmpContent = extractContentOfFile(file.listFiles(), FILE_WHITE)) != null && !tmpContent.trim().isEmpty()
				&& (tmpContent = extractContentOfFile(file.listFiles(), FILE_REGEX)) != null && !tmpContent.trim().isEmpty();
	}
	
	@SneakyThrows
	public static String extractContentOfFile(File[] files, String filename)
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
