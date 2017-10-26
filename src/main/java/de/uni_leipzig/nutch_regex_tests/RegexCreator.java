package de.uni_leipzig.nutch_regex_tests;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class RegexCreator
{
	private final File basisDirectory;
	
	@SneakyThrows
	public void writeRegexFileTo(File file)
	{
		if(file.exists())
		{
			file.delete();
		}
		
		file.createNewFile();
		CheckThatAllRegexesTogetherYieldsToTheExpectedResult test = new CheckThatAllRegexesTogetherYieldsToTheExpectedResult(this);
		test.checkThatFilterBehavesAsExpected(getBlackAndWhiteExampleDirectory());
		
		Files.write(file.toPath(), constructConcatenatedRegexFileContent().getBytes());
	}
	
	public String constructConcatenatedRegexFileContent()
	{
		return getBlackAndWhiteExampleDirectory().stream()
			.map(f -> "# "+ f.getExampleDirectory().getName() +"\n"+ f.getContentOfRegexFile())
			.collect(Collectors.joining("\n\n"));
	}
	
	public List<BlackAndWhiteExampleDirectory> getBlackAndWhiteExampleDirectory()
	{
		List<BlackAndWhiteExampleDirectory> ret = new ArrayList<>();
		
		for(File file : getBasisDirectory().listFiles())
		{
			BlackAndWhiteExampleDirectory newExampleDirectory = new BlackAndWhiteExampleDirectory(file);
			
			if(!newExampleDirectory.isValid())
			{
				throw new RuntimeException("Directory '"+ file +"' is not valid");
			}
			
			ret.add(newExampleDirectory);
		}
		
		Collections.sort(ret);
		
		return ret;
	}
}
