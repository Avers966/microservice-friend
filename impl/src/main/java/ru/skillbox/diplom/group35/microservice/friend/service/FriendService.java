package ru.skillbox.diplom.group35.microservice.friend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.utils.SecurityUtil;
import ru.skillbox.diplom.group35.microservice.friend.dto.*;
import ru.skillbox.diplom.group35.microservice.friend.mapper.FriendMapper;
import ru.skillbox.diplom.group35.microservice.friend.model.Friend;
import ru.skillbox.diplom.group35.microservice.friend.model.Friend_;
import ru.skillbox.diplom.group35.microservice.friend.repository.FriendRepository;
import javax.transaction.Transactional;
import java.util.*;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.*;


/**
 * FriendService
 *
 * @author Grigory Dyakonov
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final SecurityUtil securityUtil;


    public FriendShortDto friendApprove(UUID idTo) {
        UUID myId = securityUtil.getAccountDetails().getId();
        List<Friend> listPair = friendRepository.findFriendPair(myId, idTo);
        if (listPair.isEmpty()) return new FriendShortDto();
        for (Friend f : listPair) {
            friendRepository.save(f.setPreviousStatusCode(f.getStatusCode()).setStatusCode("FRIEND"));
        }
        log.info("Пользователь " + myId + " подтвердил запрос на дружбу от " + idTo);
        return (new FriendShortDto()).setFriendId(idTo).setStatusCode(StatusCodeDto.FRIEND);
    }


    public FriendShortDto  friendBlocked(UUID idTo) {
        UUID myId = securityUtil.getAccountDetails().getId();
        List<Friend>  listPair = friendRepository.findFriendPair(myId, idTo);
        if (listPair.isEmpty()) {
            friendRepository.save(new Friend(myId, idTo, "BLOCKED"));
            friendRepository.save(new Friend(idTo, myId,  "NONE"));
            return (new FriendShortDto()).setFriendId(idTo).setStatusCode(StatusCodeDto.BLOCKED);
        }
        for (Friend f : listPair) {
            if (f.getIdTo().equals(myId)) friendRepository.save(f.setPreviousStatusCode(f.getStatusCode()).setStatusCode("BLOCKED"));
            else friendRepository.save(f.setPreviousStatusCode(f.getStatusCode()).setStatusCode("NONE"));
        }
        log.info(myId + " заблокировал " + idTo);
        return (new FriendShortDto()).setFriendId(idTo).setStatusCode(StatusCodeDto.BLOCKED);
    }

    public FriendShortDto  friendUnblock(UUID idTo) {
        UUID myId = securityUtil.getAccountDetails().getId();
        List<Friend>  listPair = friendRepository.findFriendPair(myId, idTo);
        if (listPair.isEmpty()) return new FriendShortDto();
        for (Friend f : listPair) {
            if (f.getPreviousStatusCode().equals("NONE")) friendRepository.hardDeleteById(f.getId());
            else friendRepository.save(f.setStatusCode(f.getPreviousStatusCode()));
        }
        log.info(myId + " разблокировал " + idTo);
        return (new FriendShortDto()).setFriendId(idTo);    }


    public FriendShortDto friendRequest(UUID idTo) {
        UUID myId = securityUtil.getAccountDetails().getId();

        FriendSearchDto searchDto = new FriendSearchDto(myId, idTo,  null);     //запись с отношениями уже существует ?
        List<Friend> listStatuses = friendRepository.findAll(getSpecification(searchDto));
        if (!listStatuses.isEmpty()) return new FriendShortDto(); //отмена запроса

        friendRepository.save(new Friend(idTo, myId, "REQUEST_FROM"));
        friendRepository.save(new Friend(myId, idTo,  "REQUEST_TO"));

        log.info(myId + " создал запрос на добавление друга " + idTo);
        return  (new FriendShortDto()).setFriendId(idTo).setStatusCode(StatusCodeDto.REQUEST_TO);
    }

    public FriendShortDto friendSubscribe(UUID idTo) {
        UUID myId = securityUtil.getAccountDetails().getId();

        FriendSearchDto searchDto = new FriendSearchDto(myId, idTo, null);        //уже есть отношения ?
        List<Friend> listStatuses = friendRepository.findAll(getSpecification(searchDto));
        if (!listStatuses.isEmpty()) return new FriendShortDto(); //отмена

        friendRepository.save(new Friend(idTo, myId, "SUBSCRIBED"));
        friendRepository.save(new Friend(myId, idTo, "WATCHING"));

        log.info(myId + " создал подписку на пользователя " + idTo);
        return (new FriendShortDto()).setFriendId(idTo).setStatusCode(StatusCodeDto.WATCHING);
    }

    public Page<FriendShortDto> search(FriendSearchDto searchDto, Pageable pageable) {
        searchDto.setIdTo(securityUtil.getAccountDetails().getId()); // определяем собственный id
        Page<Friend> friendsPage = friendRepository.findAll(getSpecification(searchDto), pageable);
        log.info("Произведен поиск по совокупности заданных параметров ");
        return friendsPage.map(friendMapper::friendToFriendShortDto);
    }


    public FriendShortDto getById(UUID id) {
        Friend friend = new Friend();
        if (friendRepository.existsById(id)) friend = friendRepository.getById(id);
        return friendMapper.friendToFriendShortDto(friend);
    }

    public void deleteById(UUID idTo) {
        UUID myId = securityUtil.getAccountDetails().getId();
        List<Friend> listPair = friendRepository.findFriendPair(myId, idTo);
        if (!listPair.isEmpty()) {
            for (Friend f : listPair) {
                if (f.getIdTo().equals(myId) && f.getStatusCode().equals("SUBSCRIBED")) return; // Не можем удалить своего подписчика
                if (f.getStatusCode().equals("BLOCKED") || f.getStatusCode().equals("NONE")) return; // Не должны удалять блокировку в этом методе
            }
            friendRepository.hardDeleteById(listPair.get(1).getId());
            friendRepository.hardDeleteById(listPair.get(0).getId());
            log.info("Удалена пара " + myId + " " + idTo);
        }
    }

    public List<FriendShortDto> getRecommendations(FriendSearchDto searchDto) {
        UUID myId = securityUtil.getAccountDetails().getId();
        FriendSearchDto friendSearchDto = new FriendSearchDto(myId, null,"FRIEND");
        List<Friend> listFriends = friendRepository.findAll(getSpecification(friendSearchDto)); // находим своих друзей
        List<FriendShortDto> recommendation = new ArrayList<>();
        Map<UUID, Integer> mapFriendsOfFriends = new HashMap<>();
        if (!listFriends.isEmpty()) {
            for (Friend f : listFriends) {
                FriendSearchDto friendsOfFriendsSearchDto = new FriendSearchDto(f.getIdFrom(), null, "FRIEND");
                List<Friend> listFriendsOfFriends = friendRepository.findAll(getSpecification(friendsOfFriendsSearchDto)); // находим друзей своих друзей
                if (!listFriendsOfFriends.isEmpty()) {
                    for (Friend fOfF : listFriendsOfFriends) {
                        if (fOfF.getIdFrom().equals(myId)) continue;  // удаляем себя из списка друзей друзей
                        mapFriendsOfFriends.put(fOfF.getIdFrom(), mapFriendsOfFriends.getOrDefault(fOfF.getIdFrom(), 0) + 1); // убираем дубли, считаем рейтинг
                    }
                }
            }
        }
        for (Friend f : listFriends) mapFriendsOfFriends.keySet().removeIf(k -> k.equals(f.getIdFrom()));  //Из списка друзей друзей удаляем своих друзей
        for (UUID k : mapFriendsOfFriends.keySet()) recommendation.add((new FriendShortDto()).setFriendId(k).setRating(mapFriendsOfFriends.get(k)));
        log.info("Выданы рекомендации " + mapFriendsOfFriends.toString());
        return recommendation;
    }

    public List<UUID> getFriendId() {
        UUID myId = securityUtil.getAccountDetails().getId();

        List <UUID> listIdFriends = new ArrayList<>();
        FriendSearchDto friendSearchDto = new FriendSearchDto(myId, null,"FRIEND");
        List<Friend> listFriend = friendRepository.findAll(getSpecification(friendSearchDto)); // находим друзей
        if (!listFriend.isEmpty()) {
            for (Friend f : listFriend) {
              listIdFriends.add(f.getIdFrom());
            }
        }
        log.info("Выдан список друзей " + listIdFriends.toString());
        return listIdFriends;
    }

<<<<<<< HEAD
=======

>>>>>>> daf277c (оптимизация кода v6)
    public CountDTO getCount() {
        UUID myId = securityUtil.getAccountDetails().getId();

        CountDTO countDTO = new CountDTO();
        FriendSearchDto friendSearchDto = new FriendSearchDto(myId, null,"REQUEST_FROM");
        List<Friend> listRequest = friendRepository.findAll(getSpecification(friendSearchDto)); // находим заявки
        if (!listRequest.isEmpty()) countDTO.setCount(listRequest.size());
        log.info("Выдано количество заявок в друзья - " + countDTO.getCount());

        return countDTO;
    }


    public List<UUID> getBlockFriendId() {
        UUID myId = securityUtil.getAccountDetails().getId();

        List <UUID> listIdBlocked = new ArrayList<>();
        FriendSearchDto friendSearchDto = new FriendSearchDto(null, myId,"BLOCKED");
        List<Friend> listBlocked = friendRepository.findAll(getSpecification(friendSearchDto)); // находим друзей
        if (!listBlocked.isEmpty()) {
            for (Friend f : listBlocked) {
                listIdBlocked.add(f.getIdTo());
            }
        }
        log.info("Выдан список блокируемых " + listIdBlocked.toString());
        return listIdBlocked;

    }


    public Map<UUID, String> checkFriend(List<UUID> ids) {
        UUID myId = securityUtil.getAccountDetails().getId();

        Map<UUID, String> friendsMap = new HashMap<>();
        for (UUID idTo: ids) {
            FriendSearchDto friendSearchDto = new FriendSearchDto(myId, idTo,null);
            List<Friend> listStatus = friendRepository.findAll(getSpecification(friendSearchDto));
            if (!listStatus.isEmpty()) {
                for (Friend f : listStatus) friendsMap.put(f.getIdFrom(), f.getStatusCode());
            }
        }
        log.info("Выдан список проверки на дружбу " + friendsMap.toString());
        return friendsMap;
    }

    public List<UUID> statusFriend(String status) {
        UUID myId = securityUtil.getAccountDetails().getId();

        List <UUID> listIdStatus = new ArrayList<>();
        FriendSearchDto friendSearchDto = new FriendSearchDto(myId,null, status);
        List<Friend> listStatus = friendRepository.findAll(getSpecification(friendSearchDto)); // находим друзей
        if (!listStatus.isEmpty()) {
            for (Friend f : listStatus) {
                listIdStatus.add(f.getIdFrom());
            }
        }
        log.info("По статусу " + status + " выдан список " + listIdStatus.toString());
        return listIdStatus;
    }


    public static Specification<Friend> getSpecification(FriendSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(equal(Friend_.idFrom, searchDto.getIdFrom(), true))
                .and(equal(Friend_.idTo, searchDto.getIdTo(), true))
                .and(equal(Friend_.statusCode, searchDto.getStatusCode(), true));

    }
}
