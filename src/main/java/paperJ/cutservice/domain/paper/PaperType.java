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

    @OneToMany(mappedBy = "paperType", cascade = CascadeType.ALL)
    private List<PaperColor> colors = new ArrayList<>();
}
