package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User extends BaseEntity<Long> {

    @NotNull(message = "cannot be null")
    private String name;
    @NotNull(message = "cannot be null")
    private String surname;

    @NotNull(message = "cannot be null")
    @Column(unique = true)
    private String email;

    @NotNull(message = "cannot be null")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private Password password;

    private LocalDateTime registrationDateTime;
}