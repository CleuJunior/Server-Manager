package io.getarrays.server.resource;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Response;
import io.getarrays.server.model.Server;
import io.getarrays.server.service.implementations.ServerServiceImpl;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/server")
public class ServerResource {
    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timeStamp(now())
                        .data(Map.of("servers", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping sucess" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );

    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) throws IOException {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timeStamp(now())
                        .data(Map.of("servers", server))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.get(id)))
                        .message("Server retrieved")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response
                        .builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", serverService.delete(id)))
                        .message("Server deleted")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );

    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Download/images/" + fileName));

    }
}
