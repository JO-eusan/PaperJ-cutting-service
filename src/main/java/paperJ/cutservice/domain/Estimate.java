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

    @OneToOne
    @JoinColumn(name = "PAPER_TYPE_ID")
    private PaperType paperType;

    @OneToOne
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(mappedBy = "estimate")
    private Order order;
}
