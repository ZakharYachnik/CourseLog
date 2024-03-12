package by.yachnikzakhar.courselog.config;


import by.yachnikzakhar.courselog.model.Course;
import by.yachnikzakhar.courselog.model.Lesson;
import by.yachnikzakhar.courselog.model.User;
import by.yachnikzakhar.courselog.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import static org.hibernate.cfg.Environment.*;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScans(value = {
        @ComponentScan("by.yachnikzakhar.courselog.service"),
        @ComponentScan("by.yachnikzakhar.courselog.dao"),
        @ComponentScan("by.yachnikzakhar.courselog.model")
})
public class AppConfig {
    @Autowired
    private Environment environment;
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        Properties props = new Properties();

        // Setting JDBC properties
        props.put(DRIVER, "com.mysql.cj.jdbc.Driver"); // Corrected driver class
        props.put(URL, environment.getProperty("mysql.jdbcUrl"));
        props.put(USER, environment.getProperty("mysql.username"));
        props.put(PASS, environment.getProperty("mysql.password"));

        // Setting Hibernate properties
        props.put(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        props.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));

        // Setting C3P0 properties (check for typo)
        props.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        props.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        props.put(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        props.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements")); // Ensure this matches your property file

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(User.class, Course.class, Lesson.class, UserRole.class);

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

}

