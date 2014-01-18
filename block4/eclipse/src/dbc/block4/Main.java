package dbc.block4;

/**
 * Main class of object as controller of this project.
 * @author btemperli
 * 
 * A project at FHNW - HS2013 - dbC
 * Professor: Alexandre De Spindler
 * Student: Beat Temperli
 */
class Main {
	
	
	public static void main(String[] args) {
		System.out.println("start dbc4 - a db4o example-project");
	
	    Main main = new Main();
		
		main.setContent();

	 }
	
	private void setContent () {
		System.out.println("start construct data");
		System.out.println("************************************************");
		
		RealMovie realMovie1 = new RealMovie(1, "Harry Potter", 2001, "Daniel Radcliffe");
		RealMovie realMovie2 = new RealMovie(2, "Transformers", 2005, "Shia LaBeouf");
		RealMovie realMovie3 = new RealMovie(3, "Terminator", 1984, "Arnold Schwarzenegger");

		realMovie1.print();
		realMovie2.print();
		realMovie3.print();
		
		AnimationMovie animationMovie1 = new AnimationMovie(4, "Brave", 2012, "Pixar");
		AnimationMovie animationMovie2 = new AnimationMovie(5, "Shrek", 2001, "Dreamworks");
		AnimationMovie animationMovie3 = new AnimationMovie(6, "Madagascar", 2005, "Dreamworks");
		
		animationMovie1.print();
		animationMovie2.print();
		animationMovie3.print();
		
		System.out.println("************************************************");
		System.out.println("end construct data");

	}
}