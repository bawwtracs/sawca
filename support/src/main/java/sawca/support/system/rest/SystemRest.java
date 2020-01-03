package sawca.support.system.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import sawca.support.system.entity.User;
import sawca.support.system.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class SystemRest {

    @Resource
    private UserRepository userRepository;

    @PostMapping("/user")
    public User createUser(@RequestBody User request) {
        return userRepository.save(request);
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userRepository.getOne(id);
    }

    @PutMapping("/user/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User
            request) {
        request.setId(id);
        return userRepository.save(request);
    }

}
