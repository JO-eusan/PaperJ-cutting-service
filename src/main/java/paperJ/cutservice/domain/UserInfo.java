package paperJ.cutservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable @Getter
public class UserInfo {

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_CONTACT")
    private String contact;

    // 기본 생성자
    protected UserInfo() {}

    // passkey를 설정하는 생성자
    public UserInfo(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }
}
