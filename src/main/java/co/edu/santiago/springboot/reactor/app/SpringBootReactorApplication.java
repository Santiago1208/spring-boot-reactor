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
		// We declare our User flux.
		// When a String arrives, map that string to a user name.
		// Check if the user was created. Throws an error if not.
		// Transform each user name to uppercase form
		Flux<User> names = Flux.just("Jhon", "Diana", "Kate", "Mark")
				.map(name -> new User(name.toUpperCase(), null))
				.doOnNext(user -> {
					if (user == null) {
						throw new RuntimeException("Error: Empty user was found");
					} else {
						log.info("Checking user {}", user.toString());
					}
				})
				.map(user -> {
					user.setName(user.getName().toLowerCase());
					return user;
				});

		// We got subscribed to our flux.
		// Each user is printed out as string
		// If an error occurs in the producer side, prints out the message of the error.
		// When the producer completes the transmission, prints out a success message.
		names.subscribe(user -> log.info("Consuming user {}", user.toString()), error -> log.error(error.getMessage()), () ->
				log.info("Producer execution had finished successfully!"));

	}
}
