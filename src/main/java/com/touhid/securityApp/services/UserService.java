    package com.touhid.securityApp.services;

    import com.touhid.securityApp.dto.SignUpDto;
    import com.touhid.securityApp.entities.User;
    import com.touhid.securityApp.dto.UserDto;
    import com.touhid.securityApp.repositories.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.modelmapper.ModelMapper;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class UserService implements UserDetailsService {

        private final UserRepository userRepository;
        private final ModelMapper modelMapper;
        private final PasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException("user with email "+username+" is not found"));
        }

        public User getUserById(Long userId){
            return userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("userId is not found"));
        }

        public UserDto signUp(SignUpDto signUpDto) {
            if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
              throw new BadCredentialsException("email"+signUpDto.getEmail()+" is already exist");
            }

            if(signUpDto.getPassword() == null) {
                throw new IllegalArgumentException("Password cannot be null");
            }

            String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
            User newUser = modelMapper.map(signUpDto,User.class);
            newUser.setPassword(encodedPassword);
            UserDto userDto = modelMapper.map(userRepository.save(newUser),UserDto.class);

            return userDto;

        }
    }
