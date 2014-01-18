/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */

package dbc.block4;

import java.util.List;

public class Movie {
	
	private Integer id;
	private String title;
	private Integer year;
	private List<Client> clients;

	// Constructor
	Movie() {
		this(0, "", 0);
	}
	
	Movie(Integer id, String title, Integer year) {
		this.id = id;
		this.title = title;
		this.year = year;
	}
	
	// Setter and Getter
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public List<Client> getClients() {
		return this.clients;
	}
	
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
}
