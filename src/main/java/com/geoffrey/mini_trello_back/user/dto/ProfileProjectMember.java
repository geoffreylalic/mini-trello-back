package com.geoffrey.mini_trello_back.user.dto;

import com.geoffrey.mini_trello_back.profile.Profile;

public record ProfileProjectMember(Integer projectId, Profile profile) {
}
