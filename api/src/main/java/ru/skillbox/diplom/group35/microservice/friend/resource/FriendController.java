package ru.skillbox.diplom.group35.microservice.friend.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.microservice.friend.dto.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * FriendController
 *
 * @author Grigory Dyakonov
 */

@RequestMapping("/api/v1/friends")
public interface FriendController extends BaseController<FriendShortDto, FriendSearchDto> {

    @PutMapping("/{id}/approve")
    ResponseEntity<FriendShortDto> approveFriend(@PathVariable(name = "id") UUID id);

    @PutMapping("/block/{id}")
    ResponseEntity<FriendShortDto> block(@PathVariable(name = "id") UUID id);

    @PutMapping("/unblock/{id}")
    ResponseEntity<FriendShortDto> unBlock(@PathVariable(name = "id") UUID id);

    @PostMapping("/{id}/request")
    ResponseEntity<FriendShortDto> requestFriend(@PathVariable(name = "id") UUID id);

    @PostMapping("/subscribe/{id}")
    ResponseEntity<FriendShortDto> subscribe(@PathVariable(name = "id") UUID id);

    @Override
    @GetMapping
    ResponseEntity<Page<FriendShortDto>> getAll(FriendSearchDto searchDto, Pageable page);

    @Override
    @GetMapping("/{id}")
    ResponseEntity<FriendShortDto> getById(@PathVariable(name = "id") UUID id);

    @Override
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable(name = "id") UUID id);

    @GetMapping("/recommendations")
    ResponseEntity<List<FriendShortDto>> getRecommendations(FriendSearchDto searchDto);

    @GetMapping("/friendId")
    ResponseEntity<List<UUID>> getFriendId();

    @GetMapping("/count")
    ResponseEntity<CountDTO> getCount();

    @GetMapping("/blockFriendId")
    ResponseEntity<List<UUID>> getBlockFriendId();

    @GetMapping("/check")
    ResponseEntity<Map<UUID, String>> checkFriend(@RequestParam(value = "ids", required=false) List<UUID> ids);

    @GetMapping("/status")
    ResponseEntity<List<UUID>> statusFriend(@RequestParam(value = "status", required=false) String status);


}
