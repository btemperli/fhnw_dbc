/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */

package dbc.block4;

public class RealMovie extends Movie {
	
	private String actor;
	
	
	// methods
	public void print() {
		System.out.println("ID " + this.getId()
				+ "   RealMovie, " + this.getTitle()
				+ ". Year: " + this.getYear()
				+ ". Actor: " + actor );
	}
	
	// constructors
	RealMovie() {
		super();
		this.actor = "an actor";
	}
	
	RealMovie(Integer id, String title, Integer year, String actor) {
		super(id, title, year);
		this.actor = actor;
	}
	
	// getter and setter
	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}
	
}
