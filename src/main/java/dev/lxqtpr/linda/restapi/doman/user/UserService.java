package dev.lxqtpr.linda.restapi.doman.user;

import dev.lxqtpr.linda.restapi.doman.user.dto.CreateUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<ResponseUserDto> getUserByQuery(String subscribedTo) {
        var users = userRepository.findByQuery(subscribedTo);
        return users.stream()
                .map( el -> modelMapper.map(el, ResponseUserDto.class))
                .toList();
    }
}
