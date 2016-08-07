package com.penner.architecture;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by penneryu on 16/8/6.
 */
@Module
public class DomainDaggerModel {

    @Singleton
    @Provides
    String provierFirstName(Context context) {
        return "Penner";
    }

    @Singleton
    @Provides
    int providerLastName(Context context) {
        return 12;
    }

    @Singleton
    public static class DomainDaggerProvider {

        private String firstName;
        private int lastName;

        @Inject
        public DomainDaggerProvider(String firstName, int lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getDomainDagger() {
            return firstName + " " + lastName + " haha";
        }
    }
}
