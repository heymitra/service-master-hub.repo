package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "sub_services")
public class SubService extends BaseEntity<Long> {
    @NotNull(message = "cannot be null")
    private String subServiceName;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @NotNull(message = "cannot be null")
    private double basicPrice;

    @NotNull(message = "cannot be null")
    private String description;

    @Override
    public String toString() {
        return "id = " + id +
                "SubService Name = " + subServiceName +
                "Basic Price = " + basicPrice +
                "Description = " + description + "\n-------------";
    }
}
