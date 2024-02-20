package in.gov.forest.wildlifemis;

import in.gov.forest.wildlifemis.appUser.AppUserManagementRepository;
import in.gov.forest.wildlifemis.domian.AppUser;
import in.gov.forest.wildlifemis.domian.Role;
import in.gov.forest.wildlifemis.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class WildlifeMisApplication {
	public static void main(String[] args) {
		SpringApplication.run(WildlifeMisApplication.class, args);
	}
}
