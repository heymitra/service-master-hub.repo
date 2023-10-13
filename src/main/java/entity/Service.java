package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "services")
public class Service extends BaseEntity<Long> {
    private String serviceName;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<SubService> subServices = new ArrayList<>();
}

