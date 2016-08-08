package com.nasa.rover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 * This is the test class for Rover Navigation
 * @author Jerry Zhang
 *
 */
public class RoverNavigationTest {


	
	@Test(dataProvider = "successNavigation")
	public void successMove(Rover start, Rover end) throws ApplicationException{
		Assert.assertEquals(start.startNavigate(), end);
	}	
	
	
	@Test(dataProvider = "failedNavigation", expectedExceptions=ApplicationException.class)
	public void failedMove(Rover rover) throws ApplicationException{
		rover.startNavigate();
		
	}	

	
	@DataProvider(name="successNavigation")
	public Object[][] successNavigation(){
		return new Object[][]{
			{new Rover(new Position(1,1), Direction.North, "L").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.West, "")}
			, {new Rover(new Position(1,1), Direction.North, "LL").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.South, "")}
			, {new Rover(new Position(1,1), Direction.North, "LLL").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.East, "")}
			, {new Rover(new Position(1,1), Direction.North, "LLLL").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.North, "")}
			, {new Rover(new Position(1,1), Direction.North, "R").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.East, "")}
			, {new Rover(new Position(1,1), Direction.North, "RR").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.South, "")}
			, {new Rover(new Position(1,1), Direction.North, "RRR").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.West, "")}
			, {new Rover(new Position(1,1), Direction.North, "RRRR").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,1), Direction.North, "")}
			, {new Rover(new Position(1,1), Direction.North, "M").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,2), Direction.North, "")}
			, {new Rover(new Position(1,1), Direction.East, "M").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(2,1), Direction.East, "")}
			, {new Rover(new Position(1,1), Direction.South, "M").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(1,0), Direction.South, "")}
			, {new Rover(new Position(1,1), Direction.West, "M").setUpperRightPosition(new Position(5,5)) , new Rover(new Position(0,1), Direction.West, "")}
		};
	}

	@DataProvider(name="failedNavigation")
	public Object[][] failedNavigation(){
		return new Object[][]{
			{new Rover(new Position(1,1), Direction.North, "MMMMM").setUpperRightPosition(new Position(5,5)) }
			, {new Rover(new Position(1,1), Direction.West, "MM").setUpperRightPosition(new Position(5,5)) }
			, {new Rover(new Position(1,1), Direction.East, "MMMMM").setUpperRightPosition(new Position(5,5)) }
			, {new Rover(new Position(1,1), Direction.South, "MM").setUpperRightPosition(new Position(5,5)) }
		};
	}

}
