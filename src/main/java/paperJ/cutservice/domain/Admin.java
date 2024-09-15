package paperJ.cutservice.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private Long id;

    @Column(name = "PASSKEY")
    private String passkey = "admin";

    @Column(name = "CHECKKEY")
    private String checkkey = "0330";
}
