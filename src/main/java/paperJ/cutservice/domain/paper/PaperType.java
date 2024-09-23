package paperJ.cutservice.domain.paper;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class PaperType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAPER_TYPE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SIZE_X")
    private int sizeX;

    @Column(name = "SIZE_Y")
    private int sizeY;

    @Column(name = "GSM")
    private int GSM;

    @OneToMany(mappedBy = "paperType", cascade = CascadeType.ALL)
    private List<PaperColor> colors = new ArrayList<>();

    // 기본 생성자
    protected PaperType() {}

    // 사용자에게 입력받는 초기 정보로 생성
    public PaperType(String name, int sizeX, int sizeY, int GSM) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.GSM = GSM;
    }
}
