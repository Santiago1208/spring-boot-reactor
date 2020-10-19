package co.edu.santiago.springboot.reactor.app;

import co.edu.santiago.springboot.reactor.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private final static Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// We declare a String flux.
		Flux<String> names = Flux.just("Jhon Doe", "Diana Hang", "Kate O'Brian", "Mark Roberts", "Bruce Lee", "Bob Marley", "Dave Casper");

		// When a String arrives from the String flux (in the form of name surname), we split the name of the surname.
		// Then maps the name and surname to the User entity.
		// Then selects all the users with a name beginning with B
		// Check if the user was created. Throws an error if not.
		// Transform each user name to uppercase form
		// So, the result of those operators we have a new Flux of type User! Fluxes are immutables.
		Flux<User> users = names.map(fullName -> fullName.split(" "))
				.map(nameArray -> new User(nameArray[0].toUpperCase(), nameArray[1].toUpperCase()))
				.filter(user -> user.getName().startsWith("B"))
				.doOnNext(user -> {
					if (user == null) {
						throw new RuntimeException("Error: Empty user was found");
					} else {
						log.info("Checking user {}", user.toString());
					}
				})
				.map(user -> {
					user.setName(user.getName().toLowerCase());
					user.setSurname(user.getSurname().toLowerCase());
					return user;
				});

		// If we subscribe to the Users' flux the output will be related with the operators applied in the Flux<User>
		// But, if we subscribe to the String's flux the output will be the raw full name, ignoring the fact that we used it to build our users' flux.
		users.subscribe(user -> log.info("Consuming user {}", user.toString()), error -> log.error(error.getMessage()), () ->
				log.info("Producer execution had finished successfully!"));

	}
}
