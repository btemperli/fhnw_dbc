/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */
package dbc.block3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="animation_movie")
@PrimaryKeyJoinColumn(name="movie_id")
public class AnimationMovie extends Movie {
	
	@Column(name = "drawer")
	private String drawer;
	
	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}
}
