package de.uni_leipzig.nutch_regex_tests;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.nutch.net.URLFilter;
import org.apache.nutch.urlfilter.regex.RegexURLFilter;
import org.junit.Assert;

import lombok.SneakyThrows;

public class CheckThatAllRegexesTogetherYieldsToTheExpectedResult
{
	private final URLFilter urlFilter;
	
	@SneakyThrows
	public CheckThatAllRegexesTogetherYieldsToTheExpectedResult(RegexCreator regexCreator)
	{
		urlFilter = new RegexURLFilter(regexCreator.constructConcatenatedRegexFileContent());
	}
	
	public void checkThatFilterBehavesAsExpected(List<BlackAndWhiteExampleDirectory> tests)
	{
		for(BlackAndWhiteExampleDirectory test : tests)
		{
			checkThatAllWhitelistUrlsAreNotFiltered(test);
			checkThatAllBlacklistUrlsAreFiltered(test);
		}
	}
	
	public void checkThatAllWhitelistUrlsAreNotFiltered(BlackAndWhiteExampleDirectory exampleDirectory)
	{
		List<String> whiteListUrls = extractUrls(exampleDirectory.getContentOfWhiteListFile());
		
		Assert.assertNotEquals("White and Blacklist must at least contain one example", 0, whiteListUrls.size());
		Assert.assertEquals(whiteListUrls, applyUrlFilter(whiteListUrls));
	}
	
	public void checkThatAllBlacklistUrlsAreFiltered(BlackAndWhiteExampleDirectory exampleDirectory)
	{
		List<String> blacklistUrls = extractUrls(exampleDirectory.getContentOfBlackListFile());
		
		Assert.assertNotEquals("White and Blacklist must at least contain one example", 0, blacklistUrls.size());
		Assert.assertEquals(0, applyUrlFilter(blacklistUrls).size());
	}
	
	private List<String> applyUrlFilter(List<String> urls)
	{	
		return urls.stream()
			.filter(url -> urlFilter.filter(url) != null)
			.collect(Collectors.toList());
	}
	
	private List<String> extractUrls(String fileContent)
	{
		return Stream.of(StringUtils.split(fileContent, "\n"))
			.map(StringUtils::trim)
			.filter(s -> !StringUtils.startsWith(s, "#") && !s.isEmpty())
			.collect(Collectors.toList());
	}
}
