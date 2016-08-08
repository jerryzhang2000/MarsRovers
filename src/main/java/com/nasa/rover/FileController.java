package com.nasa.rover;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;


import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;



/*
 * This is the Parse Class which will parse the user input file, if the file format is invalid, this will through exception from parseFile function.
 */
public class FileController {
	
	public FileController(){
	}
	
    final static Logger logger = Logger.getLogger(FileController.class);
    
	private Position maxPosition;
	private List<Rover> rovers;

	//ask user to select file
	public String openFile(){
	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Text file", "txt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       return chooser.getSelectedFile().getAbsolutePath();
	    } 		
	    return null;
	}
	
	/**
	 * Parse the user input file, the result will be stored in maxPosition and rovers
	 * @param fileName location of the filen to be parsed.
	 * @throws ApplicationException When file format is invalid
	 */
	public void parseFile(String fileName) throws ApplicationException{
		try {
			logger.debug("Open File "+ fileName);
			FileInputStream is = new FileInputStream(fileName);
			parse(is);
		} catch (FileNotFoundException e){
			throw new ApplicationException ("The file specified is not existing", e);
		}
		
	}
	
	
	private void parse(InputStream is)throws ApplicationException{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String line2 = null;
		rovers = new ArrayList<Rover>();
		try {
			//Read first line for range of plateau
			logger.debug("Read line 1 from text file");
			if ((line=br.readLine())!=null){
				maxPosition = readMaxPosition(line);
			}
			
			int roverIndex = 1;
			//Start reading Rover List
			while((line=br.readLine())!=null){
				logger.debug("Read line " + roverIndex*2+" from text file");
				if ((line2=br.readLine())!=null){
					logger.debug("Read line " + (roverIndex*2+1) + " from text file");
					Rover rover= readRover(line, line2, roverIndex);
					rovers.add(rover);
					roverIndex++;
				} else {
					//Can not read second line for Rover
					throw new ApplicationException("Invalid Format in Line " + (roverIndex * 2+1));
				}
			}

		} catch (IOException e){
			throw new ApplicationException("Error when reading file", e);
		}
	}
	
	/**
	* Read the upper-right coordinate of the plateau, will throw ApplicationException if the format is invalid
	* The format of the line should be contain 2 numbers separate by space
	*/
	public Position readMaxPosition(String line) throws ApplicationException{
		logger.debug("Read upper-right coordinates from text file ");
		String[] positions = line.trim().split("\\s+");
		if (positions.length != 2){
			throw new ApplicationException("Invalid Format in Line 1");
		}
		try {
			long x = Long.parseLong(positions[0]);
			long y = Long.parseLong(positions[1]);	
			if ( x>=0 && y>=0 ) {
				logger.debug("Read upper-right coordinates success with value ("+ x +", "+ y);
				return new Position(x,y);
			} else {
				throw new ApplicationException("Invalid Format in Line 1");
			}
		} catch (NumberFormatException e ){
			throw new ApplicationException("Invalid Format in Line 1", e);
		}
		
	}
	
	/**
	 * Read Rover Object by position and instruction
	 * @param positionLine - String contains position information
	 * @param instructionLine - String contains instruction
	 * @param roverIndex - index of the Rover object
	 * @return Rover Object
	 * @throws ApplicationException 
	 */
	public Rover readRover(String positionLine, String instructionLine, int roverIndex) throws ApplicationException{
		logger.debug("Start read Rover " + roverIndex + "position" );
		String[] positions = positionLine.trim().split("\\s+");
		if (positions.length != 3){
			throw new ApplicationException("Invalid Format for Rover position in Line " + roverIndex*2);
		}
		try {
			long x = Long.parseLong(positions[0]);
			long y = Long.parseLong(positions[1]);
			if ( x<0 || y<0 ) {
				throw new ApplicationException("Invalid Format for Rover position in Line " + roverIndex*2);
			}
			Position position = new Position(x,y);
			Direction direction = Direction.fromDirection(positions[2]);
			if (null == direction ){
				throw new ApplicationException("Invalid Format for orientation in Line " + roverIndex*2);
			}
			logger.debug("Rover "+ roverIndex+" position read success with value (" + x+", "+ y +") direction="+direction.getDirection());
			
			logger.debug("Start read Rover " + roverIndex + "instruction" );
			if ( ! Pattern.matches("[LRM]*", instructionLine) ){
				throw new ApplicationException("Invalid Format for instruction in Line " + (roverIndex*2 +1) );
			}
			logger.debug("Rover "+ roverIndex+" instruction read success");
			
			return new Rover(position, direction, instructionLine); 
		} catch (NumberFormatException e ){
			throw new ApplicationException("Invalid Format for position in Line " + roverIndex*2, e);
		}
	}
	
	public Position getMaxPosition(){
		return maxPosition;
	}
	
	public List<Rover> getRovers(){
		return rovers;
	}
	
}
