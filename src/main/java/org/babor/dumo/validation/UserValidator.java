package org.babor.dumo.validation;

import lombok.RequiredArgsConstructor;
import org.babor.dumo.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserService userService;

    public void isPresent(int userId) {
        if(userService.findById(userId) == null) {
            throw new IllegalArgumentException("User not found");
        }
    }
}
