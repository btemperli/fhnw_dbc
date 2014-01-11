/**
 *  Written by Beat Temperli
 *  (c) 2013, beat@temper.li
 */

package dbc.block3;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
 
public class Usecase {
 
 /**
  * @param args
  */
 public static void main(String[] args) {
   
  Usecase usecase = new Usecase();
  
  usecase.fillUpDatabase(usecase);
  usecase.usecaseOne(usecase);
  usecase.usecaseTwo(usecase);
  usecase.usecaseThree(usecase);

 }
 
 private void fillUpDatabase(Usecase usecase) {
	  /**
	   * adding some content to the database. 
	   */
	  usecase.addRealMovie("Terminator", "Arnold Schwarzenegger");
	  usecase.addRealMovie("Transformers", "Shia LaBeouf");
	  usecase.addRealMovie("Harry Potter", "Daniel Radcliffe");
	  usecase.addRealMovie("Die Hard", "Bruce Willis");
	  usecase.addAnimationMovie("Brave", "pixar");
	  usecase.addAnimationMovie("Cars", "pixar");
	  usecase.addAnimationMovie("Shrek", "dreamworks");
	  usecase.addAnimationMovie("Madagascar", "dreamworks");
	  
	  usecase.addClient("Peter", "Muster");
	  usecase.addClient("Franz", "Gutmann");
	  usecase.addClient("Heinz", "Meier");
	  usecase.addClient("Hans", "im Glück");
 }
 
 /**
  * Usecase 1
  * ===========================================================================
  * Ein neuer Kunde wird erstellt und mietet einen Animationsfilm.
  * @param usecase
  */
 private void usecaseOne(Usecase usecase) {
	 
	 String firstName = "Max";
	 String filmTitle = "Cars";
	 /* add new client */
	 usecase.addClient(firstName, "Mueller");
	 
	 Client client = usecase.getClient("Max", "Mueller");
	 AnimationMovie movie = usecase.getAnimationMovie(filmTitle);
	 
	 usecase.hire(client, movie);
	 
	 // some tests.
	 if (!firstName.equals(client.getFirstName())) {
		 showMessage("Error during add Person to db");
	 } else if (!filmTitle.equals(movie.getTitle())) {
		 showMessage("Error during receive Movie.");
	 } else {
		 showMessage("usecase One: okay!");
	 }
 }
 
 /**
  * Usecase 2
  * ===========================================================================
  * 2 Real-Filme werden aus der Datenbank gelöscht.
  * @param usecase
  */
 private void usecaseTwo(Usecase usecase) {
	 Boolean test = true;
	 /* add new client */

	 Movie movie1 = usecase.getMovie("Die Hard");
	 Movie movie2 = usecase.getMovie("Transformers");
	 
	 usecase.deleteMovie(movie1);
	 usecase.deleteMovie(movie2);
	 
	 // some tests.
	 try {
		movie1.getTitle();
		movie2.getTitle();
	 } catch(Exception e) {
		 test = false;
	 }
	 if (!test) {
		 showMessage("Error during deleting movies.");
	 } else {
		 showMessage("usecase Two okay!");
	 }
 }
 /**
  * Usecase 3
  * ===========================================================================
  * Ein bestehender Kunde mietet alle Animations-Filme.
  * @param usecase
  */
 private void usecaseThree(Usecase usecase) {
	 /* add new client */
	 Client client = usecase.getClient("Max", "Mueller");
	 
	 List<AnimationMovie> movies = getAllAnimationMovies();
	 
	 for (Iterator<AnimationMovie> iter = movies.iterator(); iter.hasNext();) {
		 usecase.hire(client, iter.next());;
	 }
	 
	 if (movies.isEmpty()) {
		 showMessage("No Movies to hire.");
	 } else {
		 showMessage("Usecase Three okay!");
	 }
	 
 }
 
 private void addClient(String firstName, String lastName) {
   
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	 
	 Client client = new Client();
	 client.setFirstName(firstName);
	 client.setLastName(lastName);
	 
	 session.save(client);
	 
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 }
 
 private void addRealMovie(String title, String actor) {
	   
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	    
	 RealMovie movie = new RealMovie();
	 movie.setActor(actor);
	 movie.setTitle(title);
	 
	 session.save(movie);
	    
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 }
 
 private void addAnimationMovie(String title, String drawer) {
	   
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	    
	 AnimationMovie movie = new AnimationMovie();
	 movie.setDrawer(drawer);
	 movie.setTitle(title);
	 session.save(movie);
	    
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 }
 
 private Client getClient(String firstName, String lastName) {
	 
	 Client client = null;
	 
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	    
	 List<Client> clients = session.createQuery("from Client as u where u.firstName = :firstName and u.lastName = :lastName")
			 .setString( "firstName", firstName )
			 .setString( "lastName", lastName)
			 .setMaxResults(1)
			 .list();
	 
	 for (Iterator<Client> iter = clients.iterator(); iter.hasNext();) {
		 client = iter.next();
	 }
	 	    
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
	 
	 return client;
 }
 
 private Movie getMovie(String title) {
	 Movie movie = null;
	 
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	    
	 List<Movie> movies = session.createQuery("from Movie as u where u.title = :title")
			 .setString( "title", title )
			 .setMaxResults(1)
			 .list();
	 
	 for (Iterator<Movie> iter = movies.iterator(); iter.hasNext();) {
		 movie = iter.next();
	 }
	 	    
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
	 
	 return movie;
 }
 
 private AnimationMovie getAnimationMovie(String title) {
	 AnimationMovie movie = null;
	 
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	    
	 List<AnimationMovie> movies = session.createQuery("from AnimationMovie as u where u.title = :title")
			 .setString( "title", title )
			 .setMaxResults(1)
			 .list();
	 
	 for (Iterator<AnimationMovie> iter = movies.iterator(); iter.hasNext();) {
		 movie = iter.next();
	 }
	 	    
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
	 
	 return movie;
 }
 
 private List<AnimationMovie> getAllAnimationMovies() {
	 
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	    
	 List<AnimationMovie> movies = session.createQuery("from AnimationMovie")
			 .list();
	 	    
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
	 
	 return movies;
 }
 
 private void getFullName(String firstName) {
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	 
	 List<Client> clients = session.createQuery("from Client as u where u.firstName = :firstName")
			 .setString( "firstName", firstName )
			 .list();
	 for (Iterator<Client> iter = clients.iterator(); iter.hasNext();) {
		 Client client = iter.next();
		 System.out.println(client.getFirstName() +" " + client.getLastName());
	 }
	 
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 } 
  
 private void deleteClient(Client client) {
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
    
	 session.delete(client);
 
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 }
 
 private void deleteMovie(Movie movie) {
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
    
	 session.delete(movie);
 
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 }
 
 private void hire(Client client, Movie movie) {
	 
	 SessionFactory sf = HibernateUtil.getSessionFactory();
	 Session session = sf.openSession();
	 session.beginTransaction();
	 
     Set<Client> clients = new HashSet<Client>();
     clients.add(client);

     movie.setClients(clients);
     
     session.update(movie);
     
	 session.getTransaction().commit();
	 session.flush();
	 session.close();
 }
 
 private void showMessage(String message) {
	 System.out.println(message);
 }
}