package com.shiraz.prime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Test;
import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.lang.StringBuffer;


public class PrimeFactorsTest 
{

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final String testFilesPath = ".\\testfiles\\";

    @Before
	public  void setUpStreams() 
    {	
	System.setOut(new PrintStream(outContent));
	System.setErr(new PrintStream(errContent));
	//System.out.flush();
	//System.err.flush();
    }
    
    @After
	public   void cleanUpStreams() 
    {
	System.setOut(null);
	System.setErr(null);	
    }
    
    /* testing with an input file which does not exist */
    @Test
	public void testFileNotFoundExceptionIsThrown() 
    {
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile("non-existance-file");
	String s =  errContent.toString();
	assertTrue("With a non-existing file, program must return 'FileNotFoundException':",
		   s.contains("FileNotFoundException"));
    }
    


  /* testing with an input file which exists but is empty */
        @Test
	public void testEmptyFile() 
    {
	String testFileName = testFilesPath + "testnumbers1.txt";
	File file = new File(testFileName);
        try 
	    {
		file.createNewFile();
	    } 
	catch (IOException ioe) 
	    {
		System.out.println("Error while creating empty file: " + ioe);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	assertEquals("With an empty file program must return an empty string", "", outContent.toString());
    }
  
    /* testing with an input file which contains a number bigger that Max Int. */ 
   @Test
    public void testLargeNumber() 
    {
	String testFileName = testFilesPath + "testnumbers2.txt";
	try
	    {
		PrintWriter writer = new PrintWriter(testFileName);
		writer.println("7777777777777777777777777");
		writer.close();
	    }
	catch (Exception ex) 
	    {
		System.out.println("Error while creating a file: " + ex);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	String s = errContent.toString();
	assertTrue("file '7777777777777777777777777' must return 'INFO: got NumberFormatException for '7777777777777777777777777':" ,
		   s.contains("got NumberFormatException for"));
    }


    /* testing with an input file which contains non-numbers . */ 
   @Test
    public void testNonNumber() 
    {
	String testFileName = testFilesPath + "testnumbers3.txt";
	try
	    {
		PrintWriter writer = new PrintWriter(testFileName);
		writer.println("32s343");
		writer.close();
	    }
	catch (Exception ex) 
	    {
		System.out.println("Error while creating a file: " + ex);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	String s = errContent.toString();
	assertTrue("file '32s343' must return 'INFO: got NumberFormatException for '32s343':" ,
		   s.contains("got NumberFormatException for '32s343'"));
    }

    /* testing with an input file which contains multiple numbers on the same row . */ 
   @Test
    public void testMultipleNumbers() 
    {
	String testFileName = testFilesPath + "testnumbers4.txt";
	try
	    {
		PrintWriter writer = new PrintWriter(testFileName);
		writer.println("32 343 12");
		writer.close();
	    }
	catch (Exception ex) 
	    {
		System.out.println("Error while creating a file: " + ex);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	String s = errContent.toString();
	assertTrue("file '32 343 12' must return 'INFO: got NumberFormatException for '32 343 12':" ,
		   s.contains("got NumberFormatException for '32 343 12'"));
    }


     
    /* testing with an input file which contains valid entries */ 
    @Test
    public void testValidFile() 
    {
	String testFileName = testFilesPath + "testnumbers5.txt";
	try
	    {
		PrintWriter writer = new PrintWriter(testFileName);
		writer.println("17");
		writer.println("24");
		writer.close();
	    }
	catch (Exception ex) 
	    {
		System.out.println("Error while creating a file: " + ex);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	assertEquals("file with 17 & 24 must return", "17: 17,\r\n24: 2,2,2,3,\r\n", outContent.toString());      
    }

    /* testing with an input file which contains empty lines */ 
    @Test
    public void testEmptyLines() 
    {
	String testFileName = testFilesPath + "testnumbers6.txt";
	try
	    {
		PrintWriter writer = new PrintWriter(testFileName);
		writer.println("");
		writer.close();
	    }
	catch (Exception ex) 
	    {
		System.out.println("Error while creating a file: " + ex);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	String s = errContent.toString();
	assertTrue("file '' must return 'INFO: got NumberFormatException for '':" ,
		   s.contains("got NumberFormatException for ''"));
    }

    /* testing with an input file which contains valid entries with sign ('+' or '-') */ 
    //    @Test
    public void testSignedFile() 
    {
	String testFileName = testFilesPath + "testnumbers7.txt";
	try
	    {
		PrintWriter writer = new PrintWriter(testFileName);
		writer.println("-32");
		writer.println("+34");
		writer.close();
	    }
	catch (Exception ex) 
	    {
		System.out.println("Error while creating a file: " + ex);
		return;
	    }	
	PrimeFactors tester = new PrimeFactors();
	tester.processInputFile(testFileName);
	assertEquals("file with -32 & +34 must return", "-32: 2,2,2,2,2,\r\n34: 2,17,\r\n", outContent.toString());      
    }
  
}
