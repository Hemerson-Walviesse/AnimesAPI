package com.animes.AnimeAPI.users.DTO;

import com.animes.AnimeAPI.users.roles.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
