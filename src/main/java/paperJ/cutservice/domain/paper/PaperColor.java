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

    @ManyToOne
    @JoinColumn(name = "PAPER_TYPE_ID")
    private PaperType paperType;
}
