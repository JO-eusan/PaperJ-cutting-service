package paperJ.cutservice.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "APP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "PASSKEY")
    private String passkey;

    @OneToMany(mappedBy = "user")
    private List<Estimate> estimates = new ArrayList<>();
}
