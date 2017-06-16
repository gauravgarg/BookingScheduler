package com.test.akqa.BookingScheduler.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.test.akqa.BookingScheduler.main.BookingProcessor;

public class BookingSchedulerJob implements Job
{
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		System.out.println("Job start executing on : " + new Date());	
	  	  File currDir = new File("");
		  String path = currDir.getAbsolutePath();
	    	String fileName = path + "\\data\\input.txt";  // input file path 
	    	List<Object> list = new ArrayList<>();

	    	try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
	    		list = br.lines().collect(Collectors.toList());
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    	BookingProcessor processor = new BookingProcessor();
	    	processor.process((List<String>)(Object)list);
	    	System.out.println("Job end executing on : " + new Date());
	}
	
}
