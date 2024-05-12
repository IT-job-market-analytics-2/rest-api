package dev.lxqtpr.linda.restapi.authentication;

import dev.lxqtpr.linda.restapi.core.exceptions.JwtException;
import dev.lxqtpr.linda.restapi.core.exceptions.PasswordDoesNotMatchException;
import dev.lxqtpr.linda.restapi.core.exceptions.ResourceNotFoundException;
import dev.lxqtpr.linda.restapi.core.exceptions.UserAlreadyExistException;
import dev.lxqtpr.linda.restapi.doman.user.UserEntity;
import dev.lxqtpr.linda.restapi.doman.user.UserRepository;
import dev.lxqtpr.linda.restapi.doman.user.dto.CreateUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.LoginUserDto;
import dev.lxqtpr.linda.restapi.doman.user.dto.ResponseUserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseUserDto registration(CreateUserDto createUserDto) {
        try {
            var userToSave = modelMapper.map(createUserDto, UserEntity.class);

            userToSave.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

            var res = modelMapper.map(userRepository.save(userToSave), ResponseUserDto.class);

            res.setAccessToken(jwtService.generateAccessToken(createUserDto.getUsername()));
            res.setRefreshToken(jwtService.generateRefreshToken(createUserDto.getUsername()));

            return res;
        }
        catch (DbActionExecutionException e){
            log.debug("User already exist");
            throw new UserAlreadyExistException("User with this username already exist");
        }

    }

    public ResponseUserDto login(LoginUserDto loginUserDto){
        var user = userRepository.findByUsername(loginUserDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())){
            throw new PasswordDoesNotMatchException("Password does not match");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );
        var res = modelMapper.map(user, ResponseUserDto.class);
        res.setAccessToken(jwtService.generateAccessToken(loginUserDto.getUsername()));
        res.setRefreshToken(jwtService.generateRefreshToken(loginUserDto.getUsername()));
        return res;
    }

    public ResponseUserDto refreshTokens(HttpServletRequest request){
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtException("invalid refreshToken");
        }
        var refreshToken = authHeader.substring(7);
        if (!jwtService.validateRefreshToken(refreshToken)){
            throw new JwtException("Refresh token does not valid");
        }
        var username = jwtService.getUsernameFromRefreshClaims(refreshToken);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        var res = modelMapper.map(user, ResponseUserDto.class);

        res.setAccessToken(jwtService.generateAccessToken(username));
        res.setRefreshToken(jwtService.generateRefreshToken(username));
        return res;
    }
}
