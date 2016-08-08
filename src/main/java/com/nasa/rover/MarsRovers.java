package com.nasa.rover;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Application Entry Point
 *
 */
public class MarsRovers 
{
	final static Logger logger = Logger.getLogger(MarsRovers.class);
			
    public static void main( String[] args )
    {

		if ( args.length>=1 && "y".equalsIgnoreCase(args[0]))
    		Logger.getRootLogger().setLevel(Level.DEBUG);
    	else 
    		Logger.getRootLogger().setLevel(Level.INFO);
    	
		//FileController is used to parse the input file and generate the MaxPosition Object and List of Rover objects
    	FileController controller = new FileController();

    	try {
    		String fileName = controller.openFile();
    		if (null==fileName || fileName.trim().length()==0){
    			System.out.println("Please select the input text file.");
	    	} else {
	    		logger.debug("Open file "+fileName);
	    		controller.parseFile(fileName);
	    		//Read the upperRight position from input File
	    		Position p = controller.getMaxPosition();
	    		
	    		//Read the list of Rover objects.
	    		List<Rover> rovers = controller.getRovers();
	    		
	    		for(int i=0;i<rovers.size();i++){
	    			Rover rover = (Rover)rovers.get(i);
	    			rover.setUpperRightPosition(p);
	    			logger.debug("Rover "+ (i+1)+" start navigation");
	    			rover.startNavigate();
	    			logger.debug("Rover "+ (i+1)+" finish navigation");
	    			System.out.println(rover.getPosition().getX()+ " " + rover.getPosition().getY()+ " " + rover.getDirection().getDirection());
	
	    		}
	    	}
    	} catch ( Exception e){
    		logger.error(e.getMessage(), e);
    		logger.debug(e.getMessage(),e);
    	}
    }
    
}
