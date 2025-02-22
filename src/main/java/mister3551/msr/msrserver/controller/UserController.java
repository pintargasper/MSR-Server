package mister3551.msr.msrserver.controller;

import jakarta.validation.Valid;
import mister3551.msr.msrserver.entity.UserData;
import mister3551.msr.msrserver.record.DeleteUser;
import mister3551.msr.msrserver.record.ResetPassword;
import mister3551.msr.msrserver.record.UpdateUser;
import mister3551.msr.msrserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/data")
    public UserData userData(Authentication authentication) {
        return userService.getUserData(authentication);
    }

    @PostMapping("/user/update")
    public String updateUserData(Authentication authentication, @Valid @ModelAttribute UpdateUser updateUser) throws IOException {
        return userService.updateUserData(authentication, updateUser);
    }

    @PostMapping("/user/password/change")
    public String updatePassword(Authentication authentication, @Valid @RequestBody ResetPassword resetPassword) {
        return userService.updatePassword(authentication, resetPassword);
    }

    /*@PostMapping("/user/password/reset")
    public String resetPassword(Authentication authentication, @Valid @RequestBody ResetPasswordWithToken resetPasswordWithToken) {
        return userService.resetPassword(authentication, resetPasswordWithToken);
    }*/

    @PostMapping("/user/delete")
    public String deleteUserData(Authentication authentication, @Valid @RequestBody DeleteUser deleteUser) throws IOException {
        return userService.deleteUserData(authentication);
    }
}