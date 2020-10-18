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
		// When a String arrives, then print out the name.
		Flux<String> names = Flux.just("Jhon", "", "Diana", "Kate", "Mark", "")
				.doOnNext(name -> {
					if (name.isEmpty()) {
						throw new RuntimeException("Error: Empty name was found");
					}
				});

		// We got subscribed to our flux. Without subscribers, the onNextMethod won't work.
		names.subscribe(log::info, error -> log.error(error.getMessage()));

	}
}
