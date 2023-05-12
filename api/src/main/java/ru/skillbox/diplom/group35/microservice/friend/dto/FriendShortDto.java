package ru.skillbox.diplom.group35.microservice.friend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class FriendShortDto  extends BaseDto {
    private StatusCodeDto statusCode;
    private UUID friendId;
    private UUID idFriend; // ToDo delete
    private StatusCodeDto previousStatusCode;
    private Integer rating;
}
