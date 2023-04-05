package ru.skillbox.alpha.microservice.friend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.alpha.microservice.friend.dto.FriendDto;
import ru.skillbox.alpha.microservice.friend.model.Friend;
import ru.skillbox.alpha.microservice.friend.repository.FriendRepository;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * FriendService
 *
 * @author Grigory Dyakonov
 */

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public FriendDto getById(UUID id) {
        Friend friend = friendRepository.getById(id);
        return new FriendDto();
    }
}
