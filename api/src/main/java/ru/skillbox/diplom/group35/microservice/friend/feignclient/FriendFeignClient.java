package ru.skillbox.diplom.group35.microservice.friend.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * FriendFeignClient
 *
 * @Author Tretyakov Alexandr
 */

@FeignClient(value = "FriendFeignClient",
//        url = "http://localhost:8085",
        url = "http://microservice-friend",
        path = "/api/v1/friends")
public interface FriendFeignClient {

    @GetMapping("/check")
    ResponseEntity<Map<UUID, String>> checkFriend(@RequestParam(value = "ids", required=false) List<UUID> ids);

    @GetMapping("/blockFriendId")
    ResponseEntity<List<UUID>> getBlockFriendId();

    @GetMapping("/friendId")
    ResponseEntity<List<UUID>> getFriendId();

    @GetMapping("/friendId/id")
    ResponseEntity<List<UUID>> getFriendIdById(@RequestParam(value = "id", required=false) UUID id);

    @GetMapping("/status")
    ResponseEntity<List<UUID>> statusFriend(@RequestParam(value = "status", required=false) String status);
}