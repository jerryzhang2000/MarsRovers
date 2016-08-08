package com.nasa.rover;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class is used to test the FileParser functions
 * @author Jerry Zhang
 *
 */
public class FileControllerTest {

	@Test(dataProvider = "successPosition")
	public void successReadPosition(String line) throws ApplicationException{
		FileController parser = new FileController();
		parser.readMaxPosition(line);
	}
	
	@Test(dataProvider = "failedPosition", expectedExceptions=ApplicationException.class)
	public void failedReadPosition(String line) throws ApplicationException{
		FileController parser = new FileController();
		parser.readMaxPosition(line);
	}
	
	@Test(dataProvider = "successRover")
	public void successReadRover(String positionLine, String instructionLine) throws ApplicationException{
		FileController parser = new FileController();
		parser.readRover(positionLine, instructionLine, 1);
		
	}
	
	@Test(dataProvider = "failedRover", expectedExceptions=ApplicationException.class)
	public void failedReadRover(String positionLine, String instructionLine) throws ApplicationException {
		FileController parser = new FileController();
		parser.readRover(positionLine, instructionLine, 1);
		
	}
	
	@DataProvider(name="successPosition")
	public Object[][] successPosition(){
		return new Object[][]{
			{"10 10"}
			, {"5 15"}
			, {"25  15"}
		};
		
	}

	@DataProvider(name="failedPosition")
	public Object[][] failedPosition(){
		return new Object[][]{
			{"10 10 20"}
			, {"5,15"}
			, {"a 12"}
			, {"-20 12"}
			, {"20 -12"}
		};
	}	

	@DataProvider(name="successRover")
	public Object[][] successRover(){
		return new Object[][]{
			{"10 10 N", "LRM"}
			, {"5 5 S", "LRMRL"}
		};
	}

	@DataProvider(name="failedRover")
	public Object[][] failedRover(){
		return new Object[][]{
			{"10 10 A", "LRM"}
			,{"-10 10 N", "LRM"}
			,{"10 -10 N", "LRM"}
			, {"A 5 S", "LRMRL"}
			, {"10 10 S", "A"}
			, {"10,10 S", "LMR"}
			, {"10 10 S", "L M R"}
			, {"10 10 S", "L,M,R"}
			, {"10 10 S", "10MR"}
			
		};
	}
	
}
