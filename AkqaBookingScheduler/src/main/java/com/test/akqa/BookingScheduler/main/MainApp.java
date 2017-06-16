package com.test.akqa.BookingScheduler.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class MainApp 
{
    @SuppressWarnings("unchecked")
	public static void main( String[] args ) throws IOException
    {
  	  File currDir = new File("");
	  String path = currDir.getAbsolutePath();
    	String fileName = path + "\\data\\input.txt";
    	List<Object> list = new ArrayList<>();
    	try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

    		//br returns as stream and convert it into a List
    		list = br.lines().collect(Collectors.toList());
    				
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	BookingProcessor processor = new BookingProcessor();
    	processor.process((List<String>)(Object)list);
  
    }
}
