package dbc.block4;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

/**
 * Main class of object as controller of this project.
 * @author btemperli
 * 
 * A project at FHNW - HS2013 - dbC
 * Professor: Alexandre De Spindler
 * Student: Beat Temperli
 */
class Main {
	
	private String database = "databaseProject4.db4o";
	private boolean hasDatabaseContent = false;

	
	
	public static void main(String[] args) {
		System.out.println("start dbc4 - a db4o example-project");
		System.out.println();

	    Main main = new Main();
		
		main.setContent();

	 }
	
	private void setContent () {
		
		ObjectContainer container;
		
		// make testRequest to check if we have to fill database.
		container = Db4oEmbedded.openFile(database);
		try {
			List<RealMovie> checkMovies = container.query(new Predicate<RealMovie>() {
				public boolean match(RealMovie r) {
					return r.getId() > 0;
				}
			});
			
			hasDatabaseContent = !checkMovies.isEmpty();
			
		} finally {
			container.close();
		}
				
		if (!hasDatabaseContent) {
			
			// construct data
			System.out.println("construct data");
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
			
			Client client1 = new Client(1, "Ursina", "Blattmann");
			Client client2 = new Client(2, "Nadine", "Thelen");
			Client client3 = new Client(3, "Karin", "Kahlert");
			Client client4 = new Client(4, "Tara", "Stiller");
			Client client5 = new Client(5, "Elisabeth", "Gottwald");
			Client client6 = new Client(6, "Carole", "Reisert");

			client1.print();
			client2.print();
			client3.print();
			client4.print();
			client5.print();
			client6.print();
			
			System.out.println();
			
			// save data
			System.out.println("save data");
			System.out.println("************************************************");
			System.out.println("");
			
			container = Db4oEmbedded.openFile(database);
			
			try {
				
				container.store(realMovie1);
				container.store(realMovie2);
				container.store(realMovie3);
				container.store(animationMovie1);
				container.store(animationMovie2);
				container.store(animationMovie3);
				container.store(client1);
				container.store(client2);
				container.store(client3);
				container.store(client4);
				container.store(client5);
				container.store(client6);
				
			} finally {
				
				container.close();
			}

		}
		
		// read objects
		System.out.println("read data");
		System.out.println("************************************************");
		container = Db4oEmbedded.openFile(database);
		try {
			List<RealMovie> realMovies = container.query(new Predicate<RealMovie>() {
				public boolean match(RealMovie r) {
					return r.getYear() > 0;
				}
			});
			for (RealMovie realMovie : realMovies) {
				realMovie.print();
			}
			List<AnimationMovie> animationMovies = container.query(new Predicate<AnimationMovie>() {
				public boolean match(AnimationMovie a) {
					return a.getYear() > 0;
				}
			});
			for (AnimationMovie animationMovie : animationMovies) {
				animationMovie.print();
			}
			List<Client> clients = container.query(new Predicate<Client>() {
				public boolean match(Client c) {
					return c.getId() > 0;
				}
			});
			for (Client client : clients) {
				client.print();
			}
		} finally {
			container.close();
		}
		
		System.out.println();
	}
}