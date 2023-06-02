package ru.skillbox.diplom.group35.microservice.friend.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Friend service", description = "Сервис друзей")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
@RequestMapping("/api/v1/friends")
public interface FriendController extends BaseController<FriendShortDto, FriendSearchDto> {

        @PutMapping("/{id}/approve")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Подтверждение запроса на дружбу по идентификатору")
    ResponseEntity<FriendShortDto> approveFriend(@PathVariable(name = "id") UUID id);

    @PutMapping("/block/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Блокировка пользователя по идентификатору")
    ResponseEntity<FriendShortDto> block(@PathVariable(name = "id") UUID id);

    @PutMapping("/unblock/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Разблокировка пользователя по идентификатору")
    ResponseEntity<FriendShortDto> unBlock(@PathVariable(name = "id") UUID id);

    @PostMapping("/{id}/request")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Создание запроса на дружбу по идентификатору")
    ResponseEntity<FriendShortDto> requestFriend(@PathVariable(name = "id") UUID id);

    @SecurityRequirement(name = "JWT")
    @PostMapping("/subscribe/{id}")
    @Operation(description = "Подписка на пользователя по идентификатору")
    ResponseEntity<FriendShortDto> subscribe(@PathVariable(name = "id") UUID id);


    @Override
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение списка друзей по различным условиям поиска")
    ResponseEntity<Page<FriendShortDto>> getAll(FriendSearchDto searchDto, Pageable page);


    @Override
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение записи о дружбе по id записи")
    ResponseEntity<FriendShortDto> getById(@PathVariable(name = "id") UUID id);


    @Override
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Удаление существующих отношений с пользователем по идентификатору")
    void deleteById(@PathVariable(name = "id") UUID id);

    @SecurityRequirement(name = "JWT")
    @GetMapping("/recommendations")
    @Operation(description = "Выдача рекомендаций на дружбу")
    ResponseEntity<List<FriendShortDto>> getRecommendations(FriendSearchDto searchDto);


    @GetMapping("/friendId")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение списка идентификаторов друзей")
    ResponseEntity<List<UUID>> getFriendId();


    @GetMapping("/friendId/id")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение списка идентификаторов друзей для пользователя с id  ")
    ResponseEntity<List<UUID>> getFriendIdById(@RequestParam(value = "id", required=false) UUID id);


    @GetMapping("/count")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение количества заявок в друзья")
    ResponseEntity<CountDTO> getCount();


    @GetMapping("/blockFriendId")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение идентификаторов пользователей, заблокировавших текущего пользователя")
    ResponseEntity<List<UUID>> getBlockFriendId();


    @GetMapping("/check")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение статусов отношений для заданного списка идентификаторов пользователей")
    ResponseEntity<Map<UUID, String>> checkFriend(@RequestParam(value = "ids", required=false) List<UUID> ids);


    @GetMapping("/status")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение идентификаторов пользователей имеющих заданный статус отношений")
    ResponseEntity<List<UUID>> statusFriend(@RequestParam(value = "status", required=false) String status);


}
