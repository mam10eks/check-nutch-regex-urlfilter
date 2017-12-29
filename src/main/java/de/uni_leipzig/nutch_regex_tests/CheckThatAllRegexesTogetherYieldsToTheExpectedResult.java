package de.uni_leipzig.nutch_regex_tests;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.nutch.net.URLFilter;
import org.apache.nutch.urlfilter.regex.RegexURLFilter;

import org.junit.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.SneakyThrows;

public class CheckThatAllRegexesTogetherYieldsToTheExpectedResult
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckThatAllRegexesTogetherYieldsToTheExpectedResult.class);

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
			LOGGER.info("Check black and white examples for '{}'", test.getExampleDirectory());
			checkThatAllWhitelistUrlsAreNotFiltered(test);
			checkThatAllBlacklistUrlsAreFiltered(test);
		}
	}
	
	public void checkThatAllWhitelistUrlsAreNotFiltered(BlackAndWhiteExampleDirectory exampleDirectory)
	{
		List<String> whiteListUrls = extractUrls(exampleDirectory.getContentOfWhiteListFile());
		
		Assert.assertNotEquals("White and Blacklist must at least contain one example", 0, whiteListUrls.size());
		List<String> listAfterFiltering = applyUrlFilter(whiteListUrls);
		Assert.assertEquals("The Following urls are removed, but they are within a whitelist: "+  
			whiteListUrls.stream().filter(url -> !listAfterFiltering.contains(url)).collect(Collectors.toList())
			,whiteListUrls, listAfterFiltering);
	}
	
	public void checkThatAllBlacklistUrlsAreFiltered(BlackAndWhiteExampleDirectory exampleDirectory)
	{
		List<String> blacklistUrls = extractUrls(exampleDirectory.getContentOfBlackListFile());
		
		Assert.assertEquals("Following urls should be filtered: "+ applyUrlFilter(blacklistUrls),0, applyUrlFilter(blacklistUrls).size());
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
