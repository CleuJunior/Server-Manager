package io.getarrays.server;

import io.getarrays.server.model.Server;
import io.getarrays.server.repositories.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import static io.getarrays.server.enumeration.Status.SERVER_DOWN;
import static io.getarrays.server.enumeration.Status.SERVER_UP;

@SpringBootApplication
@EnableSwagger2
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(
					null,
					"192.168.1.160",
					"Ubuntu Linux",
					"16 GB",
					"Personal PC",
					"http://localhost:8080/api/v1/server/image/server1.png",
					SERVER_UP));

			serverRepository.save(new Server(
					null,
					"192.168.1.58",
					"Fedora Linux",
					"16 GB",
					"Dell Tower",
					"http://localhost:8080/api/v1/server/image/server2.png",
					SERVER_DOWN));

			serverRepository.save(new Server(
					null,
					"192.168.1.21",
					"MS 2008",
					"32 GB",
					"Web Server",
					"http://localhost:8080/api/v1/server/image/server3.png",
					SERVER_DOWN));

			serverRepository.save(new Server(
					null,
					"192.168.1.14",
					"Red Hat Enterprise Linux",
					"64 GB",
					"Mail server",
					"http://localhost:8080/api/v1/server/image/server4.png",
					SERVER_UP));


		};
	}
}

