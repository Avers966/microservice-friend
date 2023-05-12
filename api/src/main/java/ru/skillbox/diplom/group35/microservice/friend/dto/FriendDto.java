package ru.skillbox.diplom.group35.microservice.friend.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * FriendDto
 *
 * @author Grigory Dyakonov
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FriendDto extends BaseDto {
    public FriendDto() {}

    public FriendDto(UUID idTo, UUID idFrom, StatusCodeDto statusCode) {
        super.setIsDeleted(false);
        this.statusCode = statusCode;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.previousStatusCode = StatusCodeDto.NONE;
    }

    private String photo;
    private StatusCodeDto statusCode;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private ZonedDateTime birthDate;
    private Boolean isOnline;
    private UUID idFrom;
    private UUID idTo;
    private StatusCodeDto previousStatusCode;
    private Integer rating;
}
