package com.tiquetera.infrastructure.config;

import com.tiquetera.application.usecase.EventUseCaseImpl;
import com.tiquetera.application.usecase.VenueUseCaseImpl;
import com.tiquetera.domain.ports.in.ManageEventUseCase;
import com.tiquetera.domain.ports.in.ManageVenueUseCase;
import com.tiquetera.domain.ports.out.EventRepositoryPort;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ManageVenueUseCase manageVenueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new VenueUseCaseImpl(venueRepositoryPort);
    }

    @Bean
    public ManageEventUseCase manageEventUseCase(EventRepositoryPort eventRepositoryPort, VenueRepositoryPort venueRepositoryPort) {
        return new EventUseCaseImpl(eventRepositoryPort, venueRepositoryPort);
    }
}