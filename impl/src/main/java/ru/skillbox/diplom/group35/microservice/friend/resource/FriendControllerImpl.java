package ru.skillbox.diplom.group35.microservice.friend.resource;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.library.core.annotation.EnableExceptionHandler;
import ru.skillbox.diplom.group35.microservice.friend.dto.CountDTO;
import ru.skillbox.diplom.group35.microservice.friend.dto.FriendDto;
import ru.skillbox.diplom.group35.microservice.friend.dto.FriendSearchDto;
import ru.skillbox.diplom.group35.microservice.friend.dto.FriendShortDto;
import ru.skillbox.diplom.group35.microservice.friend.service.FriendService;

import java.util.*;

/**
 * FriendControllerImpl
 *
 * @author Grigory Dyakonov
 */

@Slf4j
@RestController
@EnableExceptionHandler
@RequiredArgsConstructor
public class FriendControllerImpl implements FriendController {
    private final FriendService friendService;



    @Override
    public ResponseEntity<FriendShortDto> approveFriend(UUID id) {
        log.info("PUT/api/v1/friends/{id}/approve принят запрос для подтверждения друга " + id);
        return ResponseEntity.ok(friendService.friendApprove(id));
    }

    @Override
    public ResponseEntity<FriendShortDto> block(UUID id) {
        log.info("PUT/api/v1/friends/block/{id} принят запрос для блокировки пользователя " + id);
        return ResponseEntity.ok(friendService.friendBlocked(id));
    }

    @Override
    public ResponseEntity<FriendShortDto> unBlock(UUID id) {
        log.info("PUT/api/v1/friends/unblock/{id} принят запрос для разблокировки пользователя " + id);
        return ResponseEntity.ok(friendService.friendUnblock(id));
    }

    @Override
    public ResponseEntity<FriendShortDto> requestFriend(UUID id) {
        log.info("POST/api/v1/friends/{id}/request: принят запрос для добавления друга" + id);
        return ResponseEntity.ok(friendService.friendRequest(id));
    }

    @Override
    public ResponseEntity<FriendShortDto> subscribe(UUID id) {
        log.info("POST/api/v1/friends/subscribe/{id} принят запрос на подписку на " + id);
        return ResponseEntity.ok(friendService.friendSubscribe(id));
    }

    @Override
    public ResponseEntity<Page<FriendShortDto>> getAll(FriendSearchDto searchDto, Pageable page) {
        log.info("GET/api/v1/friends принят поисковый запрос: " +  searchDto.toString() + " размер страницы " + page.getPageSize());
        return ResponseEntity.ok(friendService.search(searchDto, page));
    }

    @Hidden
    @Override
    public ResponseEntity<FriendShortDto> create(FriendShortDto dto) {
        return null;
    }

    @Hidden
    @Override
    public ResponseEntity<FriendShortDto> update(FriendShortDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<FriendShortDto> getById(UUID id) {
        log.info("GET/api/v1/friends/{id}");
        return ResponseEntity.ok(friendService.getById(id));
    }

    @Override
    public void deleteById(UUID id) {
        log.info("DELETE/api/v1/friends/{id} принят запрос на удаление записи по Id " + id);
        friendService.deleteById(id);
    }

    @Override
    public ResponseEntity<List<FriendShortDto>> getRecommendations(FriendSearchDto searchDto) {
        log.info("/api/v1/friends/recommendations принят запрос для рекомендаций ");
        return ResponseEntity.ok(friendService.getRecommendations(searchDto));
    }

    @Override
    public ResponseEntity<List<UUID>> getFriendId() {
        log.info("/api/v1/friends/friendId принят запрос на получение количества друзей");
        return ResponseEntity.ok(friendService.getFriendId());
    }

    @Override
    public ResponseEntity<List<UUID>> getFriendIdById(UUID id) {
        log.info("/api/v1/friends/friendId/id принят запрос на получение списка друзей по id " + id);
        return ResponseEntity.ok(friendService.getFriendIdById(id));
    }

    @Override
    public ResponseEntity<CountDTO> getCount() {
        log.info("/api/v1/friends/count принят запрос - количество заявок в друзья ");
        return ResponseEntity.ok(friendService.getCount());
    }

    @Override
    public ResponseEntity<List<UUID>> getBlockFriendId() {
        log.info("/api/v1/friends/count принят запрос на получение заблокированных пользователей");
        return ResponseEntity.ok(friendService.getBlockFriendId());
    }

    @Override
    public ResponseEntity<Map<UUID, String>> checkFriend(List<UUID> ids) {
        log.info("/api/v1/friends/check принят запрос на проверку дружбы списком " + ids.toString());
        return ResponseEntity.ok(friendService.checkFriend(ids));
    }

    @Override
    public ResponseEntity<List<UUID>> statusFriend(String status) {
        log.info("/api/v1/friends/check принят запрос на выдачу id по статусу " + status);
        return ResponseEntity.ok(friendService.statusFriend(status));
    }


}
