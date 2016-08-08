package com.nasa.rover;

/**
 * Enum Direction Class for Cardinal Directions
 * @author Jerry Zhang
 *
 */
public enum Direction {

	North("N"), South("S"), East("E"), West("W");
	
	private String direction;
	
	private Direction(String direction){
		this.direction = direction;
	}
	

	public String getDirection(){
		return this.direction;
	}
	
	
	public static Direction fromDirection(String direction){
		if ( direction != null) {
			for ( Direction d: Direction.values()){
				if( direction.equalsIgnoreCase(d.getDirection())){
					return d;
				}
			}
		}
		return null;
	}
}
