package com.spring.springbootcrud.domain.entity;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id")
public abstract class Model implements Persistable<UUID> {

  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private Boolean enabled;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @PrePersist
  public void beforePersist() {
    id = randomUUID();
    createdAt = now();
    updatedAt = createdAt;
    enabled = true;
  }

  @PreUpdate
  public void beforeUpdate() {
    updatedAt = now();
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return id == null;
  }
}
