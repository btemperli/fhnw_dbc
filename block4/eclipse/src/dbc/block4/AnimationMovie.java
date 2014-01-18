/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */
package dbc.block4;

public class AnimationMovie extends Movie {
	
	private String drawer;
	
	
	// methods
	public void print() {
		System.out.println("ID " + this.getId()
				+ "   AnimationMovie, " + this.getTitle() 
				+ ". Year: " + this.getYear()
				+ ". Drawer: " + drawer );
	}
	
	// constructors
	AnimationMovie() {
		super();
		this.drawer = "a drawer";
	}
	
	AnimationMovie(Integer id, String title, Integer year, String drawer) {
		super(id, title, year);
		this.drawer = drawer;
	}
	
	
	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}
}
