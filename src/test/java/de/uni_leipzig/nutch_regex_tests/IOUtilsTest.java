package de.uni_leipzig.nutch_regex_tests;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import lombok.SneakyThrows;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IOUtils.class, Files.class})
public class IOUtilsTest
{
	private static final File[] TEST_FILES = new File[] {new File("FileOne"), new File("FileTwo"), new File("FileThree") };
	
	@Before
	@SneakyThrows
	public void setUpTestEnvironment()
	{
		PowerMockito.mockStatic(Files.class);
		
		Mockito.when(Files.readAllBytes(Mockito.any(Path.class))).then(new Answer<byte[]>()
		{
			@Override
			public byte[] answer(InvocationOnMock invocation) throws Throwable
			{
				return ((Path)invocation.getArguments()[0]).getFileName().toFile().getName().getBytes();
			}
		});
	}
	
	@Test
	@SneakyThrows
	public void checkEnvironment()
	{
		Assert.assertArrayEquals("test".getBytes(), Files.readAllBytes(Paths.get("test")));
	}
	
	@Test
	public void checkNullIsValidInput()
	{
		Assert.assertNull(IOUtils.extractContentOfFileWithMatchingName(null, null));
	}
	
	@Test
	public void checkEmptyInputIsValid()
	{
		Assert.assertNull(IOUtils.extractContentOfFileWithMatchingName(new File[] {}, null));
		Assert.assertNull(IOUtils.extractContentOfFileWithMatchingName(null, ""));
		Assert.assertNull(IOUtils.extractContentOfFileWithMatchingName(new File[] {}, ""));
	}
	
	@Test
	public void checkRequestForFileOneReturnsFileOne()
	{
		Assert.assertEquals("FileOne", IOUtils.extractContentOfFileWithMatchingName(TEST_FILES, "FileOne"));
	}
	
	@Test
	public void checkRequestForFileTwoReturnsFileTwo()
	{
		Assert.assertEquals("FileTwo", IOUtils.extractContentOfFileWithMatchingName(TEST_FILES, "FileTwo"));
	}
	
	@Test
	public void checkRequestForFileThreeReturnsFileTrhe()
	{
		Assert.assertEquals("FileThree", IOUtils.extractContentOfFileWithMatchingName(TEST_FILES, "FileThree"));
	}
	
	@Test
	public void checkRequestForNonExistingFileReturnsNull()
	{
		Assert.assertNull(IOUtils.extractContentOfFileWithMatchingName(TEST_FILES, "NotExisting"));
	}
}
