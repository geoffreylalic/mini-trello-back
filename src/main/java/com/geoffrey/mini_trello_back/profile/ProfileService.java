package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileUserAlreadyExistsException;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserRepository;
import com.geoffrey.mini_trello_back.user.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.userRepository = userRepository;
    }

    public ProfileResponseDto createProfile(CreateProfileDto profileDto) {
        Integer userId = profileDto.userId();
        User userRequested = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserDoesNotExistsException(userId));
        long nbProfiles = profileRepository.countByUser_Id(userId);
        if (nbProfiles > 0) {
            throw new ProfileUserAlreadyExistsException(userId);
        }
        Profile newProfile = profileMapper.userToProfile(userRequested, profileDto.dateOfBirth(), profileDto.role());
        Profile profile = profileRepository.save(newProfile);

        return profileMapper.toProfileResponseDto(profile);
    }

    public List<ProfileResponseDto> listProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profileMapper.toListProfileResponseDto(profiles);
    }
}
