package dbc.block4;

import java.util.ArrayList;
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
		
		/*
		 * usecase 2:
		 * Client (id:3) didn't pay his bills, so we have to remove all his hiredMovies.
		 */
		main.useCase2();
		
		/*
		 * usecase 3:
		 * The company has a problem with "DreamWorks", we have to remove all the movies from Dreamworks.
		 */
		main.useCase3();
		
		/*
		 * usecase 4:
		 * The company adds a new Film and gives it to all Users.
		 */
		main.useCase4();

		/*
		 * usecase 5:
		 * Client (id:2) hires all AnimationMovies.
		 */
		main.useCase5();
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
		System.out.println();
	}
	
	
	/**
	 * usecase 2:
	 * Client (id:3) didn't pay his bills, so we have to remove all his hiredMovies.
	 */
	private void useCase2 () {
		
		boolean check = true;
		final Integer clientId = 3;
		
		Client searchClient = new Client(clientId);
		Client client;
		
		System.out.println("usecase 2");
		System.out.println("************************************************");
		
		container = Db4oEmbedded.openFile(DATABASE);
		tryLoop: {
			try {
				
				// get Client
				client = findClient(container, searchClient);
				if (client == null) {
					System.out.println("No client found with id: " + clientId);
					break tryLoop;
				}
				System.out.println("Found Client: " + client.getName());
				
				// create empty List
				List<Movie> emptyMovieList = new ArrayList<Movie>();
				client.setHiredMovies(emptyMovieList);
				container.store(client);
				
				// tests
				System.out.println();
				System.out.println("--- TEST ---");
				
				Client testClient = findClient(container, searchClient);
				if (testClient.getHiredMovies().size() != 0) {
					check = false;
				}
				System.out.println("testEmptyList: " + check);
				
			} finally {
				container.close();
			}
		}
		
		System.out.println("Result: " + check);
		System.out.println();
	}
	
	/**
	 * usecase 3:
	 * The company has a problem with "DreamWorks", we have to remove all the movies from Dreamworks.
	 */
	private void useCase3 () {
		
		boolean check = true;
		AnimationMovie searchMovie = new AnimationMovie();
		searchMovie.setId(null);
		searchMovie.setTitle(null);
		searchMovie.setYear(null);
		searchMovie.setDrawer("Dreamworks");
		
		
				
		
		System.out.println("usecase 3");
		System.out.println("************************************************");
		
		container = Db4oEmbedded.openFile(DATABASE);
		tryLoop: {
			try {
				
				List<Movie> movies = findMovies(container, searchMovie);
				List<Client> clients = findAllClients(container);
				
				if (movies == null) {
					System.out.println("Found no movies.");
					check = false;
					break tryLoop;
				}
				
				if (clients.isEmpty()) {
					System.out.println("Found no clients.");
					check = false;
					break tryLoop;
				}
				
				for (Movie m : movies) {
					System.out.println("Found movie " + m.getTitle());
					for (Client c : clients) {
						List<Movie> itsMovies = c.getHiredMovies();
						if (itsMovies.contains(m)) {
							System.out.println("Remove movie from Client " + c.getName());
							itsMovies.remove(m);
							c.setHiredMovies(itsMovies);
							container.store(c);
						}
					}
					container.delete(m);
				}
				
				// tests
				System.out.println();
				System.out.println("--- TEST ---");
				movies = findMovies(container, searchMovie);
				
				if (movies != null) {
					check = false;
				}
			
				System.out.println("testEmptyList: " + check);

			} finally {
				container.close();
			}
		}
		
		System.out.println("Result: " + check);
		System.out.println("(Hint: Usecase 3 works only once. When the movies are deleted already, it returns false.)");
		System.out.println();
	}
	
	/**
	 * usecase 4:
	 * The company adds a new Film and gives it to all Users.
	 */
	private void useCase4 () {
		
		boolean check = true;
		RealMovie movie = new RealMovie(7, "The Hunger Games", 2012, "Jennifer Lawrence");
		Movie searchMovie = new Movie(7);
		Client searchClient = new Client(1);
		
		System.out.println("usecase 4");
		System.out.println("************************************************");
		
		container = Db4oEmbedded.openFile(DATABASE);
		tryLoop: {
			try {
			    Movie findMovieResult = findMovie(container, searchMovie);
				if (findMovieResult == null) {
					System.out.println("add Movie to List.");
					container.store(movie);
				    findMovieResult = findMovie(container, searchMovie);
				}
				
				System.out.println("Movie to add: " + findMovieResult.getTitle());
				
				List<Client> allClients = findAllClients(container);
				
				if (allClients.isEmpty()) {
					System.out.println("Found no clients.");
					check = false;
					break tryLoop;
				}
				
				for (Client c : allClients) {
					List<Movie> hiredMovies = c.getHiredMovies();
					hiredMovies.add(findMovieResult);
					c.setHiredMovies(hiredMovies);
					container.store(c);
					System.out.println("add Movie to hiredMovies of " + c.getName());
				}
				
				// tests
				System.out.println();
				System.out.println("--- TEST ---");
				Client testClient = findClient(container, searchClient);
				List<Movie> checkHiredMovies = testClient.getHiredMovies();
				
				check = checkHiredMovies.contains(findMovieResult);
				System.out.println("test to find the hired movie: " + check);
				
				
			} finally {
				container.close();
			}
		}
		
		System.out.println("Result: " + check);
		System.out.println();
	}

	/**
	 * usecase 5:
	 * Client (id:2) hires all AnimationMovies.
	 */
	private void useCase5 () {
		
		boolean check = true;
		Client searchClient = new Client(1);
		AnimationMovie lastMovie = new AnimationMovie();

		
		System.out.println("usecase 5");
		System.out.println("************************************************");
		
		container = Db4oEmbedded.openFile(DATABASE);
		tryLoop: {
			try {
				
				Client c = findClient(container, searchClient);
				List<AnimationMovie> allAnimationMovies = findAllAnimationMovies(container);
				
				List<Movie> hiredMovies = c.getHiredMovies();
				
				if (allAnimationMovies.isEmpty()) {
					System.out.println("Found no animation Movies.");
					check = false;
					break tryLoop;
				}
				
				for (AnimationMovie a: allAnimationMovies) {
					hiredMovies.add(a);
					lastMovie = a;
					System.out.println("Added to Client " + c.getName() + " the movie " + a.getTitle());
				}
				
				c.setHiredMovies(hiredMovies);
				
				// tests
				System.out.println();
				System.out.println("--- TEST ---");
				c = findClient(container, searchClient);
			    List<Movie> checkHiredMovies = c.getHiredMovies();
			    
			    check = checkHiredMovies.size() == hiredMovies.size();
			    System.out.println("test size of hiredMovies: " + check);
			    
			    check = checkHiredMovies.contains(lastMovie);
			    System.out.println("test checkMovieIsHired: " + check);
			    
				
			} finally {
				container.close();
			}
		}
		
		System.out.println("Result: " + check);
		System.out.println();
		
		finished();

	}
	
	public void finished() {
		System.out.println("************************************************");
		System.out.println("finished.");

	}
	
	
	// some standard methods
	// -------------------------------------------------------------------------------- //
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
	
	public List<Client> findClients(ObjectContainer cont, Client findClient) {
		final ObjectSet<Client> c = cont.queryByExample(findClient);
		
		if (c.isEmpty()) {
			return null;
		}
		
		return c;
	}
	
	public List<Client> findAllClients(ObjectContainer cont) {
		ObjectSet<Client> result = cont.query(new Predicate<Client>() {
		    @Override
		    public boolean match(Client c) {
		        return c.getId() > 0;
		    }
		});
		
		return result;
	}
	
	public Movie findMovie(ObjectContainer cont, Movie findMovie) {
		final ObjectSet<Movie> m = cont.queryByExample(findMovie);
		
		if (m.isEmpty()) {
			return null;
		}
		
		return m.get(0);
	}
	
	public List<Movie> findMovies(ObjectContainer cont, Movie findMovie) {
		final ObjectSet<Movie> m = cont.queryByExample(findMovie);
		
		if (m.isEmpty()) {
			return null;
		}
		
		return m;
	}
	
	public List<AnimationMovie> findAllAnimationMovies(ObjectContainer cont) {
		final ObjectSet<AnimationMovie> result = cont.query(new Predicate<AnimationMovie>() {
			@Override
			public boolean match(AnimationMovie a) {
				return a.getId() > 0;
			}
		});
		
		return result;
	}
}