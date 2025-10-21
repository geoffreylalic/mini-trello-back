package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.ProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserMapper;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProfileMapper {

    UserMapper userMapper;
    ProjectMapper projectMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setProjectMapper(@Lazy ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }


    public Profile userToProfile(User user, @Past @NotNull LocalDate dateOfBirth) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setDateOfBirth(dateOfBirth);
        return profile;
    }

    public ProfileResponseDto toProfileResponseDto(Profile profile) {

        Integer id = profile.getId();
        UserResponseDto userResponseDto = userMapper.toUserResponse(profile.getUser());
        LocalDate dateOfBirth = profile.getDateOfBirth();
        List<SimpleProjectDto> projects = null;
        List<Task> tasks = null;

        if (profile.getProjects() != null && !profile.getProjects().isEmpty()) {
            projects = profile.getProjects().stream().map(projectMapper::toSimpleProjectDto).toList();
        }
        if (profile.getTasks() != null && !profile.getTasks().isEmpty()) {
            tasks = profile.getTasks();
        }
        return new ProfileResponseDto(
                id,
                userResponseDto,
                dateOfBirth,
                projects,
                tasks
        );
    }

    public SimpleProfileResponseDto toSimpleProfileResponseDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        Integer id = profile.getId();
        UserResponseDto userResponseDto = userMapper.toUserResponse(profile.getUser());
        LocalDate dateOfBirth = profile.getDateOfBirth();

        return new SimpleProfileResponseDto(
                id,
                userResponseDto,
                dateOfBirth
        );
    }

    public ProfileDto toProfileDto(Profile profile) {
        return new ProfileDto(profile.getId(), profile.getDateOfBirth());
    }

}
