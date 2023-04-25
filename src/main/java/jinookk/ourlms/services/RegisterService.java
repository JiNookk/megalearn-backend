package jinookk.ourlms.services;

import exceptions.UserNameAlreadyExist;
import jinookk.ourlms.dtos.RegisterRequestDto;
import jinookk.ourlms.models.entities.Account;
import jinookk.ourlms.models.entities.Cart;
import jinookk.ourlms.models.entities.Course;
import jinookk.ourlms.models.entities.Like;
import jinookk.ourlms.models.vos.UserName;
import jinookk.ourlms.models.vos.ids.AccountId;
import jinookk.ourlms.models.vos.ids.CourseId;
import jinookk.ourlms.repositories.AccountRepository;
import jinookk.ourlms.repositories.CartRepository;
import jinookk.ourlms.repositories.CourseRepository;
import jinookk.ourlms.repositories.LikeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegisterService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final CourseRepository courseRepository;
    private final LikeRepository likeRepository;

    public RegisterService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
                           CartRepository cartRepository, CourseRepository courseRepository, LikeRepository likeRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.courseRepository = courseRepository;
        this.likeRepository = likeRepository;
    }

    public Account register(RegisterRequestDto registerRequestDto) {
        if (accountRepository.existsByUserName(new UserName(registerRequestDto.getUserName()))) {
            throw new UserNameAlreadyExist(registerRequestDto.getUserName());
        }

        List<Course> courses = courseRepository.findAll();

        List<CourseId> courseIds = courses.stream()
                .map(course -> new CourseId(course.id()))
                .toList();

        Account account = Account.of(registerRequestDto);

        account.changePassword(registerRequestDto.getPassword(), passwordEncoder);

        Account saved = accountRepository.save(account);

        AccountId accountId = new AccountId(account.id());

        Cart cart = Cart.of(accountId);

        cartRepository.save(cart);

        List<Like> likes = Like.listOf(accountId, courseIds);

        likeRepository.saveAll(likes);

        return saved;
    }
}
