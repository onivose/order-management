package com.hexaware.ordermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @UpdateTimestamp
    private Timestamp timeSubmitted;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp timeCreated;

    @Column (columnDefinition = "float default 0")
    private Double total;

    @Column (columnDefinition = "boolean default false")
    private Boolean submitted;

    @ManyToOne
    @JoinColumn(
            name = "customerIdFk",
            referencedColumnName = "customerId",
            nullable = false
    )
    private Customer customer;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "orderFk")
    @JsonIgnoreProperties({"orderFk"})
    private List<Product> products = new ArrayList<>();


}
