package com.example.sns.user.application;

import com.example.sns.common.exception.ErrorCode;
import com.example.sns.common.exception.Expected4xxException;
import com.example.sns.user.domain.entity.User;
import com.example.sns.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isDuplicateId(String username) {
        long count = userRepository.countByUsername(username);
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void editLocation(Long userId, String location) {
        User user = this.userInformation(userId);
        user.updateLocation(location);
    }

    public String getProfileImage(Long userId) {
        return userRepository.findProfileImageById(userId);
    }

    public User userInformation(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Expected4xxException(ErrorCode.DONT_FIND_USER));
        return user;
    }
}
