package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.ProjectMembersDto;
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


    public Profile userToProfile(User user, @Past @NotNull LocalDate dateOfBirth, @NotNull Role role) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setDateOfBirth(dateOfBirth);
        profile.setRole(role);
        return profile;
    }

    public ProfileResponseDto toProfileResponseDto(Profile profile) {

        Integer id = profile.getId();
        UserResponseDto userResponseDto = userMapper.toUserResponse(profile.getUser());
        LocalDate dateOfBirth = profile.getDateOfBirth();
        String role = profile.getRole().name();
        List<Project> projects = profile.getProjects();
        List<Task> tasks = profile.getTasks();

        return new ProfileResponseDto(
                id,
                userResponseDto,
                dateOfBirth,
                role,
                projects,
                tasks
        );
    }

    public List<ProfileResponseDto> toListProfileResponseDto(List<Profile> profiles) {
        return profiles.stream().map(this::toProfileResponseDto).toList();
    }

    public SimpleProfileResponseDto toSimpleProfileResponseDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        Integer id = profile.getId();
        UserResponseDto userResponseDto = userMapper.toUserResponse(profile.getUser());
        LocalDate dateOfBirth = profile.getDateOfBirth();
        String role = profile.getRole().name();

        return new SimpleProfileResponseDto(
                id,
                userResponseDto,
                dateOfBirth,
                role
        );
    }

    public List<SimpleProfileResponseDto> toListSimpleProfileResponseDto(List<Profile> profiles) {
        return profiles.stream().map(this::toSimpleProfileResponseDto).toList();
    }

    public ProjectMembersDto toProjectMembersDto(Profile owner, List<Profile> profiles) {
        SimpleProfileResponseDto ownerDto = toSimpleProfileResponseDto(owner);
        List<SimpleProfileResponseDto> membersDto = toListSimpleProfileResponseDto(profiles);
        return new ProjectMembersDto(ownerDto, membersDto);
    }
}
