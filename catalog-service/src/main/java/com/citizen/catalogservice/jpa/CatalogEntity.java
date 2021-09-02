package com.citizen.catalogservice.jpa;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@Table(name = "catalog")
@Entity
public class CatalogEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false, updatable = false, insertable = false)
    // TODO: 2021-09-02 Default 컬럼에 등록되게 함.
    // TODO: 2021-09-02 CURRENT_TIMESTAMP는 h2의 function으로 현재 시간을 가져옴
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDate createAt;

}
