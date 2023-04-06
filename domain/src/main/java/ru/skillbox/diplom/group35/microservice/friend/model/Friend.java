package ru.skillbox.diplom.group35.microservice.friend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Friend
 *
 * @author Grigory Dyakonov
 */

@Getter
@Setter
@Entity
@Table(name = "friend")
@Accessors(chain = true)
@NoArgsConstructor
public class Friend extends BaseEntity {

  public Friend(UUID idTo, UUID idFrom, String statusCode) {
    super.setIsDeleted(false);
    this.statusCode = statusCode;
    this.idFrom = idFrom;
    this.idTo = idTo;
    this.previousStatusCode = "NONE";
  }

  @Column(name = "photo")
  private String photo;

  @Column(name = "status_code")
  private String statusCode;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "city")
  private String city;

  @Column(name = "country")
  private String country;

  @Column(name = "birth_date")
  private ZonedDateTime birthDate;

  @Column(name = "is_online")
  private Boolean isOnline;

  @Column(name = "account_from")
  private UUID idFrom;

  @Column(name = "account_to")
  private UUID idTo;

  @Column(name = "previous_status")
  private String previousStatusCode;

  @Column(name = "rating")
  private Integer rating;
}
