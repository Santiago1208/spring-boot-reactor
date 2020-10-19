package co.edu.santiago.springboot.reactor.app;

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
		// We declare our String flux.
		// When a String arrives, check if it is empty. If so, throws an error.
		Flux<String> names = Flux.just("Jhon", "Diana", "Kate", "Mark")
				.doOnNext(name -> {
					if (name.isEmpty()) {
						throw new RuntimeException("Error: Empty name was found");
					}
				});

		// We got subscribed to our flux. For each element arrived print out that element.
		// If an error occurs in the producer side, prints out the message of the error.
		// When the producer completes the transmission, prints out a sucess message.
		names.subscribe(log::info, error -> log.error(error.getMessage()), new Runnable() {
			@Override
			public void run() {
				log.info("Producer execution had finished successfully!");
			}
		});

	}
}
