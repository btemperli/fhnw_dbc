/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */

package dbc.block3;

import java.util.HashSet;
import java.util.Set;

public class Client implements java.io.Serializable {
	private Integer id;
	private String firstName;
	private String lastName;
	private Set<Movie> movies = new HashSet<Movie>(0);
	
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
	
	public Set<Movie> setMovies() {
		return this.movies;
	}

	public void setCategories(Set<Movie> movies) {
		this.movies = movies;
	}
}
