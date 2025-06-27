package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;
    @RestController
    @RequestMapping("/profile")
    @PreAuthorize("isAuthenticated()") // Make sure only logged-in users can access
    public class ProfileController {
        private final ProfileDao profileDao;
        private final UserDao userDao;

        @Autowired
        public ProfileController(ProfileDao profileDao, UserDao userDao) {
            this.profileDao = profileDao;
            this.userDao = userDao;
        }

        @GetMapping
        public Profile getProfile(Principal principal) {
            try {
                String username = principal.getName();
                User user = userDao.getByUserName(username);

                Profile profile = profileDao.getByUserId(user.getId());
                if (profile == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
                }

                return profile;
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting profile");
            }
        }

        @PutMapping
        public void updateProfile(@RequestBody Profile profile, Principal principal) {
            try {
                String username = principal.getName();
                User user = userDao.getByUserName(username);

                profile.setUserId(user.getId()); // Ensure the profile belongs to the logged-in user
                profileDao.update(profile);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating profile");
            }
        }
    }

