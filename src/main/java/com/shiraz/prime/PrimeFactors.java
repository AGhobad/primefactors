    /* Assumptions:
           1-Input file is either in curent dir or includes a full path.
	   2- User want to process as much as possible, so on an error we just log it and continue.
	   3- an error is considered anything which is not a valid "Integer"

    */

package com.shiraz.prime;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class PrimeFactors implements PrimeFactorsIF
{
    private final static Logger LOGGER = Logger.getLogger(PrimeFactors.class.getName()); 
    private final static String myClassName = "PrimeFactors";



    /**
     * A generic method to print content of a list of Integers 
     * @param primeFactors
     * @param entry
     * @return void
     * @throws 
     */
    
    private void displayPrimeFactors (List<Integer> primeFactors, int entry)
    {
	System.out.print(entry + ": ");
	for (Integer i:primeFactors)
	    System.out.print(i + ",");	
	System.out.println();
    }

    /**
     * A  method to parse a file, extract each line, convert the line to an integer and calculate prime factors of that number
     This method is Reading data as String (v.s. Scanner.nextInt()) so that it can recognize those entries which are too big
     as an integer and continue with the rest.
     * @param inputFileName
     * @throws 
     */
    
    
    public void processInputFile(String inputFileName)
    {
	FileReader fileReader = null;
	try {
	    File file = new File(inputFileName);
	    fileReader = new FileReader(file);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    StringBuffer stringBuffer = new StringBuffer();
	    String line;
	    
	    while ((line = bufferedReader.readLine()) != null) 
		{
		    int entry;
		    // if an entry is not a qualified 'integer', then generate a log and move on to the next entry...
		    try
			{
			    entry =  Integer.parseInt(line);
			}
		    catch (NumberFormatException ex)
			{
			    LOGGER.logp(Level.INFO,myClassName ,"processInputFile", "got NumberFormatException for '" + line + "'");
			    continue;
			}
		    displayPrimeFactors(calcPrimeFactors(entry), entry);			     
		}	    
	} 
	catch (FileNotFoundException ex) 
	    {
		LOGGER.logp(Level.INFO,myClassName ,"processInputFile", "got FileNotFoundException for file:'" + inputFileName + "'");
	    }
	catch (IOException ex) 
	    {
		LOGGER.logp(Level.INFO,myClassName ,"processInputFile", "got IOException for file:'" + inputFileName + "'");
	    }
	finally
	    {
		if ( fileReader != null)
		    try
			{
			    fileReader.close();
			}
		    catch (IOException ex) 
			{
			    LOGGER.logp(Level.INFO,myClassName ,"processInputFile", "got IOException while closig file:'" + inputFileName + "'", ex);
			}
	    }	
    }
    
    
      /**
     * A  simple and clasic method to calculate prime factors of an integer.
     * @param number
     * @return List<Integer>
     * @throws 
     */

    protected static List<Integer> calcPrimeFactors(int n) 
    {
	int number = java.lang.Math.abs(n);
	List<Integer> primefactors = new ArrayList<Integer>();
	long copyOfInput = number;
	
	for (int i = 2; i <= copyOfInput; i++) 
	    {
		if (copyOfInput % i == 0) 
		    {
			primefactors.add(i); 
			copyOfInput /= i;
			i--;
		    }
	    }	
	return primefactors;
    }
    
    
    public static void main(String[] args) 
    {
	if (args.length != 1)
	    {
		LOGGER.logp(Level.INFO,myClassName ,"main","Please pass name of the data file.");
		System.exit(1);
	    }
	
	PrimeFactors c = new PrimeFactors();
	c.processInputFile (args[0]);		
	
    }
}
