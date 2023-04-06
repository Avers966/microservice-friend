package ru.skillbox.diplom.group35.microservice.friend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group35.microservice.friend.dto.FriendDto;
import ru.skillbox.diplom.group35.microservice.friend.dto.FriendShortDto;
import ru.skillbox.diplom.group35.microservice.friend.model.Friend;

/**
 * FriendMapper
 *
 * @Author Tretyakov Alexandr
 */
@Mapper(componentModel = "spring")
public interface FriendMapper {

    FriendDto friendToFriendDto(Friend friend);

    Friend friendDtoToFriend(FriendDto friendDto);


    @Mapping(target="idFriend" , source="friend.idFrom") // ToDo delete
    @Mapping(target="friendId" , source="friend.idFrom")
    @Mapping(target="id" , source="friend.idFrom")
    FriendShortDto friendToFriendShortDto(Friend friend);
}
