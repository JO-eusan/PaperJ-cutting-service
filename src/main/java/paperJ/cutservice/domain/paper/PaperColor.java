package paperJ.cutservice.domain.paper;

import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class PaperColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAPER_COLOR_ID")
    private Long id;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "PRICE_PER_SHEET")
    private int pricePerSheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAPER_TYPE_ID")
    private PaperType paperType;

    // 기본 생성자
    protected PaperColor() {}

    // 사용자에게 입력받는 초기 정보로 생성
    public PaperColor(String color, int pricePerSheet, PaperType paperType) {
        this.color = color;
        this.pricePerSheet = pricePerSheet;
        this.paperType = paperType;
    }
}
