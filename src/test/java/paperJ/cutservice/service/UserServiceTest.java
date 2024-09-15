package paperJ.cutservice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.User;
import paperJ.cutservice.repository.UserRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 사용자_가입() throws Exception {
        // Given -> passkey 입력
        String passkey = "testPasskey";

        // When -> 사용자를 DB에 등록
        User user = userService.joinOrFindUser(passkey);

        // Then -> 사용자가 잘 등록되고 passkey가 일치하는지 확인
        User foundUser = userRepository.findByPasskey(passkey);
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getPasskey()).isEqualTo(passkey);
    }

    @Test
    public void 사용자_조회() throws Exception {
        // Given -> passkey로 사용자 가입
        String passkey = "testPasskey";
        User user = userService.joinOrFindUser(passkey);

        // When -> 사용자를 DB에 등록
        User foundUser = userService.joinOrFindUser(passkey);

        // Then -> 사용자가 잘 조회되는지 확인
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundUser.getPasskey()).isEqualTo(user.getPasskey());
    }

    @Test
    public void 사용자_견적서_발급() throws Exception {

    }

    @Test
    public void 사용자_견적서_조회() throws Exception {

    }
}
