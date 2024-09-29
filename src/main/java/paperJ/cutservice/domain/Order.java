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

    // 기본 생성자
    protected Order() {}

    // 사용자에게 입력받는 초기 정보로 생성
    public Order(UserInfo userInfo, Estimate estimate) {
        this.userInfo = userInfo;
        this.estimate = estimate;

        this.orderDate = LocalDate.now();
        this.status = OrderStatus.ORDER_RECEIVED; // 초기 주문 상태
    }

    /* 주문 상태 변경 로직 */
    public void setStatus(String orderStatus) {
        this.status = OrderStatus.valueOf(orderStatus);
    }
}
