package fi.vm.yti.groupmanagement.security;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import fi.vm.yti.security.config.FakeUserLogin;
import fi.vm.yti.security.config.FakeUserLoginProvider;
import fi.vm.yti.security.config.SecurityBaseConfig;

@Configuration
@Import(SecurityBaseConfig.class)
public class SecurityConfig {

    private final @Nullable String fakeLoginMail;
    private final @Nullable String fakeLoginFirstName;
    private final @Nullable String fakeLoginLastName;

    SecurityConfig(@Value("${fake.login.mail:}") @Nullable final String fakeLoginMail,
                   @Value("${fake.login.firstName:}") @Nullable final String fakeLoginFirstName,
                   @Value("${fake.login.lastName:}") @Nullable final String fakeLoginLastName) {

        this.fakeLoginMail = fakeLoginMail;
        this.fakeLoginFirstName = fakeLoginFirstName;
        this.fakeLoginLastName = fakeLoginLastName;
    }

    @Bean
    @ConditionalOnProperty("fake.login.mail")
    FakeUserLoginProvider fakeUserLoginProvider() {
        return () -> new FakeUserLogin(fakeLoginMail, fakeLoginFirstName, fakeLoginLastName);
    }
}
