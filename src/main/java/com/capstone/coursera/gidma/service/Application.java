package com.capstone.coursera.gidma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.capstone.coursera.gidma.service.auth.OAuth2SecurityConfiguration;
import com.capstone.coursera.gidma.service.model.BloodSugarLevelTimeEvents;
import com.capstone.coursera.gidma.service.model.CheckInQuestion;
import com.capstone.coursera.gidma.service.model.repository.BloodSugarLevelTimeEventsRepository;
import com.capstone.coursera.gidma.service.model.repository.CheckInQuestionRepository;

//Tell Spring to automatically inject any dependencies that are marked in
//our classes with @Autowired
@EnableAutoConfiguration

// Tell Spring to turn on WebMVC (e.g., it should enable the DispatcherServlet
// so that requests can be routed to our Controllers)
@EnableWebMvc

// Tell Spring that this object represents a Configuration for the
// application
@Configuration

// Tell Spring to automatically create a JPA implementation of our
// VideoRepository
@EnableJpaRepositories("com.capstone.coursera.gidma.service.model.repository")

// Tell Spring to go and scan our controller package (and all sub packages) to
// find any Controllers or other components that are part of our applciation.
// Any class in this package that is annotated with @Controller is going to be
// automatically discovered and connected to the DispatcherServlet.
@ComponentScan("com.capstone.coursera.gidma.service")

// We use the @Import annotation to include our OAuth2SecurityConfiguration
// as part of this configuration so that we can have security and oauth
// setup by Spring
@Import(OAuth2SecurityConfiguration.class)

public class Application extends RepositoryRestMvcConfiguration {

    Logger LOG = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    BloodSugarLevelTimeEventsRepository  bslteRepo;

    @Autowired
    CheckInQuestionRepository  checkInQstnRepository;
    
    private static final String MAX_REQUEST_SIZE = "150MB";

    // The app now requires that you pass the location of the keystore and
    // the password for your private key that you would like to setup HTTPS
    // with. In Eclipse, you can set these options by going to:
    // 1. Run->Run Configurations
    // 2. Under Java Applications, select your run configuration for this app
    // 3. Open the Arguments tab
    // 4. In VM Arguments, provide the following information to use the
    // default keystore provided with the sample code:
    //
    // -Dkeystore.file=src/main/resources/private/keystore -Dkeystore.pass=changeit
    //
    // 5. Note, this keystore is highly insecure! If you want more securtiy, you
    // should obtain a real SSL certificate:
    //
    // http://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html
    //
    // Tell Spring to launch our app!
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    
    
    @Bean
    public CommandLineRunner loadCheckInQuestions(CheckInQuestionRepository  checkInQstnRepository) {
        return (args) -> {
            
            checkInQstnRepository.save(new CheckInQuestion("bloodSugarLvlMT",  "What was your blood sugar level at meal time?"));
            checkInQstnRepository.save(new CheckInQuestion("bloodSugarLvlBT","What was your blood sugar level at bedtime?"));
            checkInQstnRepository.save(new CheckInQuestion("bloodSugarLvlFasting","What was your blood sugar level at Morning / Fasting?"));
            checkInQstnRepository.save(new CheckInQuestion("bloodSugarLvlTime", "What time did you check your blood sugar level at "));
            checkInQstnRepository.save(new CheckInQuestion("eatMT", "What did you eat at meal time?"));
            checkInQstnRepository.save(new CheckInQuestion("insulin", "Did you administer insulin?"));
            checkInQstnRepository.save(new CheckInQuestion("whoWithYou", "Who were you with when you checked/should have checked your blood sugar?"));
            checkInQstnRepository.save(new CheckInQuestion("whereWereYou", "Where were you when you checked/should have checked your blood sugar?"));
            checkInQstnRepository.save(new CheckInQuestion("mood", "How was your mood when you checked/should have checked your blood sugar?"));
            checkInQstnRepository.save(new CheckInQuestion("stress", "How was your stress level when you checked/should have checked your blood sugar?"));
            checkInQstnRepository.save(new CheckInQuestion("energyLvl", "How was your energy level when you checked/should have checked your blood sugar?"));
            checkInQstnRepository.save(new CheckInQuestion("bloodSugarLvlTimeEvent", "Were any of these things happening at the time you checked/should have checked your blood sugar"));
 

            // check all BloodSugarLevelTimeEvents
            LOG.info("CheckInQuestion  found with findAll():");
            LOG.info("-------------------------------");
            for (CheckInQuestion checkInQuestion : checkInQstnRepository.findAll()) {
                LOG.info(checkInQuestion.toString());
            }
            LOG.info("");
        };
    }
    
    @Bean
    public CommandLineRunner loadBloodSugarLevelTimeEvents(BloodSugarLevelTimeEventsRepository  bslteRepo) {
        return (args) -> {
            
            bslteRepo.save(new BloodSugarLevelTimeEvents("Rushing"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("feeling tired of diabetes"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("feeling sick"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("on the road"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("really hungry"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("wanting privacy"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("busy and didn’t want to stop"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("without supplies"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("feeling low"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("feeling high"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("having a lot of fun"));
            bslteRepo.save(new BloodSugarLevelTimeEvents("tired"));
            

            // check all BloodSugarLevelTimeEvents
            LOG.info("BloodSugarLevelTimeEvents  found with findAll():");
            LOG.info("-------------------------------");
            for (BloodSugarLevelTimeEvents bslte : bslteRepo.findAll()) {
                LOG.info(bslte.toString());
            }
            LOG.info("");

        };
    }
    

}
