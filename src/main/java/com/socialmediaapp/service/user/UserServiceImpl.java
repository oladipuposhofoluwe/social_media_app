package com.socialmediaapp.service.user;

import com.socialmediaapp.dto.user.UserDto;
import com.socialmediaapp.exceptions.InvalidRequestException;
import com.socialmediaapp.exceptions.ResourceNotFoundException;
import com.socialmediaapp.model.User;
import com.socialmediaapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto findUserByUsername(String userName) {
        Optional<User> optionalUser = this.userRepository.findUserByUserName(userName);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException(("User not found"));
        }
        User user = optionalUser.get();
        return this.mapUserToDto(user);
    }

    @Override
    @Transactional
    public void updateUserInfo(UserDto userDto, long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException(("User not found"));
        }
        User user = optionalUser.get();
        this.mapUserDtoUserEntity(userDto, user);
    }

    @Override
    @Transactional
    public boolean followUser(User authUser, long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException(("User not found: " + userId));
        }
        User user = optionalUser.get();
        if (Objects.equals(user.getId(), authUser.getId())){
            throw new InvalidRequestException("As a user, you can not follow your herself", "Bad Request");
        }

        authUser.addFollowUser(user);
        userRepository.save(authUser);
        return true;
    }


    private void mapUserDtoUserEntity(UserDto userDto, User user) {
        user.setUserName(userDto.getUserName());
    }

    private UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setUserId(user.getId());
        return userDto;
    }
}
