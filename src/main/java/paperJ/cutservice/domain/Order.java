package paperJ.cutservice.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity @Getter
@Table(name = "CUT_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @Embedded
    private UserInfo userInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTIMATE_ID")
    private Estimate estimate;
}
