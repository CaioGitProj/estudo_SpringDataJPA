package br.com.caiogit.datajpa.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration
{
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    // Datasource MAIS SIMPLES possível(NÃO RECOMENDADO)

    /**
     *    @Bean
     *     public DataSource datasource()
     *     {
     *         DriverManagerDataSource dataSource = new DriverManagerDataSource();
     *
     *         dataSource.setPassword(password);
     *         dataSource.setUsername(username);
     *         dataSource.setUrl(url);
     *         dataSource.setDriverClassName(driver);
     *
     *         return dataSource;
     *     }
     * @return
     */

    // Datasource que é RECOMENDADO E SEGURO
    @Bean
    public DataSource hikariDataSource()
    {
        HikariConfig config = new HikariConfig();

        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driver);


        config.setMaximumPoolSize(10); // Máximo de conecções liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-database-pool"); // Nome do pool
        config.setMaxLifetime(1800000); // Quanto de tempo uma conexão pode durar no máximo(Em milisegundos)
        config.setConnectionTimeout(100000); // Quanto tempo até iniciar uma conexão

        config.setConnectionTestQuery("SELECT 1"); //query de teste (só retorna o número 1)

        return new HikariDataSource(config);
    }

}
