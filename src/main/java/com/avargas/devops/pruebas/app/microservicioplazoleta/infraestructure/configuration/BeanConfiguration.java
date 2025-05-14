package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.configuration;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.RestauranteUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.restaurantes.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final RestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RestaurantePersistencePort restaurantePersistencePort(){
        return new RestauranteJpaAdapter(restauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public RestauranteServicePort restauranteServicePort(){
        return new RestauranteUseCase(restaurantePersistencePort());
    }



}
