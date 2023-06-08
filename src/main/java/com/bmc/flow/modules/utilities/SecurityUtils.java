package com.bmc.flow.modules.utilities;

import com.bmc.flow.modules.database.entities.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Random;

/**
 * this class contains methods to handle some innocent security code.
 * <p>
 * no security code not utility in the application es secure or safe to use. The security done here is for showcasing purposes and not for
 * production use.
 * security is currently a ways to play with the application from the front end.
 * <p>
 * this is not safe---<br>
 * this is not secure---<br>
 * do not ever use this in any production environment if you clone this repo do not use the app as is, and implement
 * your own sec or wait until I implement it myself<br
 * <p>
 */
@ApplicationScoped
public class SecurityUtils {

    private final Random random = new Random();

    private final ObfuscationConfig config;

    public SecurityUtils(final ObfuscationConfig config) {
        this.config = config;
    }

    /**
     * This "obfuscator" changes a user's personal details.
     * FIXME: might not need this no more as I am taking out first and last name
     * <p>
     * The user's callSign must not be changed.
     */
    public void obfuscateUser(final UserEntity user) {

        List<String> cities  = config.cities();
        List<String> letters = config.alphabet();

        String firstName = letters.get(random.nextInt(letters.size()));
        String lastName  = cities.get(random.nextInt(cities.size()));
        String email     = firstName + "@" + lastName + ".com";

        user.setEmail(email);
    }
}
