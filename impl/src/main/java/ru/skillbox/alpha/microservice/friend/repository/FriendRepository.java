package ru.skillbox.alpha.microservice.friend.repository;

import org.springframework.stereotype.Repository;
import ru.skillbox.alpha.microservice.friend.model.Friend;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;

/**
 * FriendRepository
 *
 * @author Grigory Dyakonov
 */

@Repository
public interface FriendRepository extends BaseRepository<Friend> {
}
