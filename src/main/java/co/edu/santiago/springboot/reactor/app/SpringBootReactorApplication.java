package co.edu.santiago.springboot.reactor.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// We declare our String flux.
		// When a String arrives, then print out the name.
		Flux<String> names = Flux.just("Jhon", "Diana", "Kate", "Mark")
				.doOnNext(System.out::println);

		// We got subscribed to our flux. Without subscribers, the onNextMethod won't work.
		names.subscribe();

	}
}
