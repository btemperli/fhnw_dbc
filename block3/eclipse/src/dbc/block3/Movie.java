/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */

package dbc.block3;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
@Inheritance(strategy=InheritanceType.JOINED)
public class Movie implements java.io.Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "movie_id")
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	private Set<Client> clients = new HashSet<Client>(0);


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
	
	public Set<Client> getClients() {
		return this.clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	
}
