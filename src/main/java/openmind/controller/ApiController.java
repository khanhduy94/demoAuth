package openmind.controller;

import openmind.service.UserEntity;
import openmind.service.UserMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private UserMapper userMapper;

    public ApiController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/oauth")
    public String findOneBankAccount(@RequestBody UserEntity userEntity) {
        UserEntity cloned = userMapper.findByUsername(userEntity.getEmail());
        return cloned.getId() + " -- "+ cloned.getRole();
    }

}
