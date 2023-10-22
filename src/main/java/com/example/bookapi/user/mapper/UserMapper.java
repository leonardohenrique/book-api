package com.example.bookapi.user.mapper;

import com.example.bookapi.user.dto.CreateUserDTO;
import com.example.bookapi.user.dto.UpdateUserDTO;
import com.example.bookapi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User map(CreateUserDTO createUserDTO) {
        return modelMapper.map(createUserDTO, User.class);
    }

    public void map(UpdateUserDTO updateUserDTO, User user) {
        modelMapper.map(updateUserDTO, user);
    }

}
