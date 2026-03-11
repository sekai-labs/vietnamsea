package org.vietnamsea.identity.config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "org.vietnamsea.identity.infra.persistence" })
public class DatabaseBaseConfig {

}
