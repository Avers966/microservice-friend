package ru.skillbox.diplom.group35.microservice.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

import java.util.UUID;

/**
 * FriendShortDto
 *
 * @Author Tretyakov Alexandr
 */

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Dto получения параметров дружбы")
public class FriendShortDto  extends BaseDto {

    @Schema(description = "Статус текущих отношений пользователя")
    private StatusCodeDto statusCode;

    @Schema(description = "Идентификатор пользователя-партнера")
    private UUID friendId;

    @Hidden
    private UUID idFriend; // ToDo delete

    @Schema(description = "Статус предшествующих отношений пользователя")
    private StatusCodeDto previousStatusCode;

    @Schema(description = "Рейтинг пользователя, рекомендуемого в друзья")
    private Integer rating;
}
