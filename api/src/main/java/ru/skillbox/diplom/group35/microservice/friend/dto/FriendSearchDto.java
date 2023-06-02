package ru.skillbox.diplom.group35.microservice.friend.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dto для поиска записей дружбы по условиям")
public class FriendSearchDto extends BaseSearchDto {
    public FriendSearchDto(){}


    public FriendSearchDto(UUID idTo, UUID idFrom, String statusCode) {
        this.idFrom = idFrom;
        this.statusCode = statusCode;
        this.idTo = idTo;
    }
    @Hidden
    private List<UUID> ids;
    @Schema(description = "Идентификатор пользователя, имеющего отношения с текущим пользователем")
    private UUID idFrom;

    @Schema(description = "Статус текущих отношений пользователя")
    private String statusCode;

    @Schema(description = "Идентификатор  текущего пользователя")
    private UUID idTo;

    @Hidden
    private String firstName;

    @Hidden
    private ZonedDateTime birthDateFrom;

    @Hidden
    private ZonedDateTime birthDateTo;

    @Hidden
    private String city;

    @Hidden
    private String country;

    @Hidden
    private Integer ageFrom;

    @Hidden
    private Integer ageTo;

    @Schema(description = "Статус предшествующих отношений пользователя")
    private String previousStatusCode;
}
