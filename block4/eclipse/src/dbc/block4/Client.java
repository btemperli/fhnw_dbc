/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */

package dbc.block4;

import java.util.List;

//import java.util.HashSet;
//import java.util.Set;

public class Client {
	
	private Integer id;
	private String firstName;
	private String lastName;
	private List<Movie> hiredMovies;
	
	// methods
	public void print() {
		System.out.println("ID " + this.getId()
				+ "   Client " + this.getFirstName()
				+ " " + this.getLastName());
	}
	
	// constructor
	Client() {
		this(0);
	}
	
	Client(Integer id) {
		this.id = id;
	}
	
	Client(Integer id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	// getter and setter
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<Movie> getHiredMovies() {
		return this.hiredMovies;
	}
	
	public void setHiredMovies(List<Movie> hiredMovies) {
		this.hiredMovies = hiredMovies;
	}
}
