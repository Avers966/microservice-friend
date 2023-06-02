package ru.skillbox.diplom.group35.microservice.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * CountDTO
 *
 * @Author Tretyakov Alexandr
 */
@Data
@Schema(description = "Dto получения счетчика заявок в друзья")
public class CountDTO {

    @Schema(description = "Счетчик заявок в друзья")
    private Integer count = 0;

}
