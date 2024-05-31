package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.dto.SignUpRequest;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 삭제
     * 리뷰, 이벤트, 푸드트럭, 메뉴 등 모두 삭제
     *
     * @param id 유저 id
     * @return String "delete success"
     */
    @Transactional
    public String deleteUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        userRepository.delete(user);
        return "delete success";
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
    }

    /**
     * 유저 정보 수정
     *
     * @param userEmail     수정할 유저 이메일
     * @param signUpRequest 수정할 정보
     * @return 수정된 유저 정보
     */
    public User updateUser(String userEmail, SignUpRequest signUpRequest) {
        User findUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        if (signUpRequest.email() != null) findUser.setEmail(signUpRequest.email());
        if (signUpRequest.password() != null) findUser.setPassword(passwordEncoder.encode(signUpRequest.password()));
        if (signUpRequest.name() != null) findUser.setName(signUpRequest.name());
        if (signUpRequest.phoneNumber() != null) findUser.setPhone(signUpRequest.phoneNumber());
        return userRepository.save(findUser);
    }

    public UserInfo findUserInfo(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        List<FoodTruckReview> reviews = foodTruckReviewRepository.findByAuthor(user);

        return new UserInfo(user, reviews);
    }

    public User setUserRole(String userEmail, UserRole role) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));

        List<UserRole> roles = user.getRoles();

        if (!roles.contains(role)) {
            roles.add(role);
        }

        return userRepository.save(user);
    }
}
