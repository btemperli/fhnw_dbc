package dbc.block4;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
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
	
	final String DATABASE = "databaseProject4.db4o";
	private boolean hasDatabaseContent = false;
	private ObjectContainer container;
	
	public static void main(String[] args) {
		System.out.println("start dbc4 - a db4o example-project");
		System.out.println();

	    Main main = new Main();
		
	    // set a minimal test-content in the database.
		main.setContent();
		
		/* usecase 1:
		 * Client (id:2) hires a movie (id:1)
		 */
		main.useCase1();

	 }
	
	private void setContent () {
				
		// make testRequest to check if we have to fill database.
		container = Db4oEmbedded.openFile(DATABASE);
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
			
		    Main main = new Main();

			main.store(realMovie1);
			main.store(realMovie2);
			main.store(realMovie3);
			main.store(animationMovie1);
			main.store(animationMovie2);
			main.store(animationMovie3);
			main.store(client1);
			main.store(client2);
			main.store(client3);
			main.store(client4);
			main.store(client5);
			main.store(client6);

		}
		
		// read objects
		System.out.println("read data");
		System.out.println("************************************************");
		container = Db4oEmbedded.openFile(DATABASE);
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
	
	/**
	 * useCase 1:
	 * Client (id:2) hires a movie (id:1)
	 */
	private void useCase1 () {
		
		boolean check = true;
		final Integer clientId = 2;
		final Integer movieId = 1;
		
		Client searchClient = new Client(clientId);
		Movie searchMovie = new Movie(movieId);
		Client client;
		Movie movie;
		
		System.out.println("usecase 1");
		System.out.println("************************************************");
		
//		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
//		configuration.common().objectClass(AnimationMovie.class).updateDepth(2);
//		configuration.common().objectClass(Client.class).updateDepth(2);
//		configuration.common().objectClass(Movie.class).updateDepth(2);
//		configuration.common().objectClass(RealMovie.class).updateDepth(2);


		container = Db4oEmbedded.openFile(DATABASE);
		tryLoop: {
			try {
		
				// get Movie with id
				movie = findMovie(container, searchMovie);

				if (movie == null) {
					System.out.println("No movie found with id: " + movieId);
					break tryLoop;
				}
				
				System.out.println("Found Movie: " + movie.getTitle());
			
				// get Client with id
				client = findClient(container, searchClient);
				
				if (client == null) {
					System.out.println("No client found with id: " + clientId);
					break tryLoop;
				}
				
				System.out.println("Found Client: " + client.getName());

				// update client
				List<Movie> hiredMovies = client.getHiredMovies();
				hiredMovies.add(movie);
				client.setHiredMovies(hiredMovies);
				container.store(client);
				
				// update movie
				List<Client> clients = movie.getClients();
				clients.add(client);
				movie.setClients(clients);
				container.store(movie);
				
				// tests
				System.out.println();
				System.out.println("--- TEST ---");
				
				Client testClient = findClient(container, searchClient);
				check = testClient.getHiredMovies().get(testClient.getHiredMovies().size() - 1).equals(movie);
				System.out.println("hiredMovies: " + check);

				Movie testMovie = findMovie(container, searchMovie);
				check = testMovie.getClients().get(testMovie.getClients().size() - 1).equals(client);
				System.out.println("useCase1: " + check);
				
			} finally {
				container.close();
			}
		}
		
		
		System.out.println("Result: " + check);
	}
	
	public void store(Object object) {
		container = Db4oEmbedded.openFile(DATABASE);
		try {
			container.store(object);
		} finally {
			container.close();
		}
	}
	
	public Client findClient(ObjectContainer cont, Client findClient) {
		final ObjectSet<Client> c = cont.queryByExample(findClient);
		
		if (c.isEmpty()) {
			return null;
		}
		
		return c.get(0);

	}
	
	public Movie findMovie(ObjectContainer cont, Movie findMovie) {
		final ObjectSet<Movie> m = cont.queryByExample(findMovie);
		
		if (m.isEmpty()) {
			return null;
		}
		
		return m.get(0);
	}
}