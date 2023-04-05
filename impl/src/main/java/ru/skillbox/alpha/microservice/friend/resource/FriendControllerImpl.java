package ru.skillbox.alpha.microservice.friend.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.alpha.microservice.friend.dto.FriendDto;
import ru.skillbox.alpha.microservice.friend.service.FriendService;

import java.util.UUID;

/**
 * FriendControllerImpl
 *
 * @author Grigory Dyakonov
 */

@RestController
@RequiredArgsConstructor
public class FriendControllerImpl implements FriendController {
    private final FriendService friendService;

    @Override
    public ResponseEntity<FriendDto> getById(UUID id) {
        return null;
    }
}
