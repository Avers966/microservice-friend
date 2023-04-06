package ru.skillbox.diplom.group35.microservice.friend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * FriendSearchDto
 *
 * @Author Tretyakov Alexandr
 */
@Getter
@Setter
@ToString
public class FriendSearchDto extends BaseSearchDto {
    public FriendSearchDto(){}

    public FriendSearchDto(UUID idTo, UUID idFrom, String statusCode) {
        this.idFrom = idFrom;
        this.statusCode = statusCode;
        this.idTo = idTo;
    }

    private List<UUID> ids;
    private UUID idFrom;
    private String statusCode;
    private UUID idTo;
    private String firstName;
    private ZonedDateTime birthDateFrom;
    private ZonedDateTime birthDateTo;
    private String city;
    private String country;
    private Integer ageFrom;
    private Integer ageTo;
    private String previousStatusCode;
}
