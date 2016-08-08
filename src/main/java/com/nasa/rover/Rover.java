package com.nasa.rover;

import org.apache.log4j.Logger;

public class Rover {
	
	final static Logger logger = Logger.getLogger(Rover.class);
	
	//Direction of the Rover
	private Direction direction;
	
	//Position of the Rover
	private Position position;
	
	//Instruction of the ROver
	private String instruction;
	
	//Boundary of the plateau
	private Position upperRightPosition;
	private Position lowerLeftPosition = new Position(0,0);
	
	public Rover(){}
	
	public Rover(Position position, Direction direction, String instruction){
		this.position = position;
		this.direction= direction;
		this.instruction = instruction;
	}
	
	
	
	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public Direction getDirection(){
		return direction;
	}


	public Position getPosition() {
		return position;
	}


	public void setPosition(Position position) {
		this.position = position;
	}


	public Rover setUpperRightPosition(Position position ){
		upperRightPosition = position;
		return this;
	}
	
	public Rover setLowerLeftPosition(Position position){
		lowerLeftPosition = position;
		return this;
	}
		
	/**
	 * Return the new Cardinal Direction based on turn command
	 * @param turnDirection - value should be "L" or "R" 
	 * @return new cardinal direction
	 */
	public void turn(String turnDirection){
		if ("L".equalsIgnoreCase(turnDirection)){
			//Turn Left
			switch(direction){
			case North:
				setDirection(Direction.West); 
				break;
			case West:
				setDirection(Direction.South);
				break;
			case South:
				setDirection(Direction.East);
				break;
			case East:
				setDirection(Direction.North);
				break;
				
			}
			logger.debug("Rover turn Left to face " + this.getDirection().getDirection());
		} else if ("R".equalsIgnoreCase(turnDirection)){
			//Turn Right
			switch(direction){
			case North:
				setDirection(Direction.East); 
				break;
			case East:
				setDirection(Direction.South);
				break;
			case South:
				setDirection(Direction.West);
				break;
			case West:
				setDirection(Direction.North);
				break;
				
			}	
			logger.debug("Rover turn Right to face " + this.getDirection().getDirection());
		}
	}
	
	/*
	 * Based on current position and direction, calculate the next position after move
	 */
	public void move() throws ApplicationException{
		switch(direction){
		case North:
			if (position.getY()==upperRightPosition.getY()){
				//already reach the north boundary 
				throw new ApplicationException("Reach the north boundary, can not move further");
			}
			position.setY(position.getY()+1);
			break;
		case West:
			if (position.getX()==lowerLeftPosition.getX()){
				//already reach the west boundary 
				throw new ApplicationException("Reach the west boundary, can not move further");
			}
			position.setX(position.getX()-1);
			break;
		case South:
			if (position.getY()==lowerLeftPosition.getY()){
				//already reach the south boundary 
				throw new ApplicationException("Reach the south boundary, can not move further");
			}			
			position.setY(position.getY()-1);
			break;
		case East:
			if (position.getX()==upperRightPosition.getX()){
				//already reach the eest boundary 
				throw new ApplicationException("Reach the east boundary, can not move further");
			}			
			position.setX(position.getX()+1);
			break;
		}
		logger.debug("Rover move to (" + position.getX() + ", "+position.getY()+")");
	}
	
	public Rover startNavigate() throws ApplicationException{
		
		for(int i=0;i<instruction.length();i++){
			String c = String.valueOf(instruction.charAt(i));
			if ( "M".equals(c)){
				move();
			} else {
				turn(c);
			}
		}
		return this;
	}
	
	public boolean equals(Object object){
		if ( object instanceof Rover && ((Rover)object).getPosition().equals(this.getPosition()) && ((Rover)object).getDirection().equals(this.getDirection()) ){
			return true;
		} else {
			return false;
		}
				
	}
	
}
