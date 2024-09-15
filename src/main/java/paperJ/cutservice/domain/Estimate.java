package paperJ.cutservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import paperJ.cutservice.domain.paper.PaperColor;
import paperJ.cutservice.domain.paper.PaperType;

import java.time.LocalDate;

@Entity @Getter
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESTIMATE_ID")
    private Long id;

    @Column(name = "ISSUE_DATE")
    private LocalDate issueDate;

    @Column(name = "IS_ORDER")
    private boolean isOrder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPER_TYPE_ID")
    private PaperType paperType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPER_COLOR_ID")
    private PaperColor paperColor;

    @Column(name = "SIZE_X")
    private int sizeX;

    @Column(name = "SIZE_Y")
    private int sizeY;

    @Column(name = "CARD_QUANTITY")
    private int cardQuantity;

    @Column(name = "SHEET_QUANTITY")
    private int sheetQuantity;

    @Column(name = "PRICE_PER_SHEET")
    private double pricePerSheet;

    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(mappedBy = "estimate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    // 기본 생성자
    protected Estimate() {}

    // 사용자에게 입력받는 초기 정보로 생성
    public Estimate(PaperType paperType, PaperColor paperColor, int sizeX, int sizeY, int cardQuantity, User user) {
        this.issueDate = LocalDate.now();
        this.isOrder = false;

        this.paperType = paperType;
        this.paperColor = paperColor;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.cardQuantity = cardQuantity;
        this.user = user;
    }

    /*
     ** 견적서 결과에 대한 계산 메서드 추가
     ** sheetQuantity
     ** pricePerSheet
     ** totalPrice
     */

}
