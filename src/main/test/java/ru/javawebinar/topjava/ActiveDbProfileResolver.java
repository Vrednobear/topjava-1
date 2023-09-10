package ru.javawebinar.topjava;

import org.springframework.lang.NonNull;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;
import ru.javawebinar.topjava.util.Profiles;

import java.util.Arrays;

public class ActiveDbProfileResolver extends DefaultActiveProfilesResolver {
    @Override
    public @NonNull
    String[] resolve(@NonNull Class<?> aClass) {
        String[] activeProfiles = super.resolve(aClass);
        String[] activeProfilesWithDb = Arrays.copyOf(activeProfiles, activeProfiles.length + 1);
        activeProfilesWithDb[activeProfiles.length] = Profiles.getActiveDbProfile();
        return activeProfilesWithDb;
    }
}
