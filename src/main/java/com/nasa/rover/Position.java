package com.nasa.rover;

public class Position {

	public Position(){}
	public Position(long x, long y){
		this.x = x;
		this.y = y;
	}
	private long x;
	private long y;
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	
	public boolean equals(Object object){
		if ( object instanceof Position && ((Position)object).getX() == this.x && ((Position)object).getY()==this.y ){
			return true;
		} else {
			return false;
		}
				
	}	
	
}
