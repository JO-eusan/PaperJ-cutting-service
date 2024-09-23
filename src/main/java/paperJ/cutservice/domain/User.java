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

    @Column(name = "PASSKEY", nullable = false, unique = true)
    private String passkey;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Estimate> estimates = new ArrayList<>();

    // 기본 생성자
    protected User() {}

    // passkey를 설정하는 생성자
    public User(String passkey) {
        this.passkey = passkey;
    }
}
