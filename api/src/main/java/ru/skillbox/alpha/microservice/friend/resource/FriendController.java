package ru.skillbox.alpha.microservice.friend.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.alpha.microservice.friend.dto.FriendDto;

import java.util.UUID;

/**
 * FriendController
 *
 * @author Grigory Dyakonov
 */

@RequestMapping("/api/v1/friends")
public interface FriendController {

    @GetMapping("/{id}")
    ResponseEntity<FriendDto> getById(@PathVariable(name = "id") UUID id);

}
