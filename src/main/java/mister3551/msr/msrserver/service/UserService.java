package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.entity.UserData;
import mister3551.msr.msrserver.record.ResetPassword;
import mister3551.msr.msrserver.record.ResetPasswordWithToken;
import mister3551.msr.msrserver.record.UpdateUser;
import mister3551.msr.msrserver.repository.UserRepository;
import mister3551.msr.msrserver.security.security.CustomUser;
import mister3551.msr.msrserver.security.service.AuthService;
import mister3551.msr.msrserver.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final FileService fileService;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService, FileService fileService, AuthService authService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.fileService = fileService;
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String updateUserData(Authentication authentication, UpdateUser updateUser) throws IOException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        UserData userData = getUserData(authentication);

        String imageName = getImageName(updateUser, userData);

        int updateResult = userRepository.updateUserData(userData.getUsername(), userData.getEmailAddress(), updateUser.fullName(), updateUser.username(), updateUser.emailAddress(), updateUser.birthdate(), imageName, updateUser.country());

        if (updateResult == 1) {
            customUser.setUsername(updateUser.username());
            customUser.setEmailAddress(updateUser.emailAddress());

            if (updateUser.image() != null) {
                fileService.deleteImage(userData.getUsername(), FileService.Types.PROFILE);
                fileService.storeImage(imageName, updateUser.image(), FileService.Types.PROFILE);
            }

            if (!userData.getImage().equals("basic-image.jpg")) {
                fileService.updateImageName(userData.getImage(), imageName, FileService.Types.PROFILE);
            }
            return tokenService.generateToken(authentication);
        }
        return "Something went wrong";
    }

    public String updatePassword(Authentication authentication, ResetPassword resetPassword) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        if (userRepository.updatePassword(customUser.getUsername(), customUser.getEmailAddress(), bCryptPasswordEncoder.encode(resetPassword.newPassword())) == 1) {
            userRepository.updatePasswordChangeTimer(customUser.getUsername(), customUser.getEmailAddress());
            return "Password updated successfully";
        }
        return "Something went wrong";
    }

    public String resetPassword(Authentication authentication, ResetPasswordWithToken resetPasswordWithToken) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        if (userRepository.resetPassword(customUser.getUsername(), customUser.getEmailAddress(), resetPasswordWithToken.token(), bCryptPasswordEncoder.encode(resetPasswordWithToken.newPassword())) == 1) {
            return "SUCCESS";
        }
        return "Something went wrong";
    }

    public String deleteUserData(Authentication authentication) throws IOException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        UserData userData = userRepository.findByUsernameAndEmailAddress(customUser.getUsername(), customUser.getEmailAddress());
        if (authService.clearData(userData.getId())) {
            fileService.deleteImage(customUser.getUsername(), FileService.Types.PROFILE);
            return "SUCCESS";
        }
        return "Something went wrong";
    }

    public UserData getUserData(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return userRepository.findByUsernameAndEmailAddress(customUser.getUsername(), customUser.getEmailAddress());
    }

    private static String getImageName(UpdateUser updateUser, UserData userData) {
        String imageName;

        if (updateUser.image() != null && !updateUser.image().isEmpty()) {
            String extension = Objects.requireNonNull(updateUser.image().getOriginalFilename()).replaceAll("^.*\\.(.*)$", "$1");
            imageName = String.format("%s.%s", updateUser.username(), extension);
        } else {
            String extension = (userData.getImage().replaceAll("^.*\\.(.*)$", "$1"));
            imageName = String.format("%s.%s", updateUser.username(), extension);
        }
        return imageName;
    }
}