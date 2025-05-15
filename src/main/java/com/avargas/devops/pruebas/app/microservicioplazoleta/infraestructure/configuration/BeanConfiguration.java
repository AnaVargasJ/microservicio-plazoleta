package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.configuration;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.categorias.ICategoriaServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.platos.IPlatoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.categorias.CategoriaPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.categorias.CategoriaUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.platos.PlatoUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.restaurante.RestauranteUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.categorias.CategoriaJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.platos.PlatoJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.restaurante.RestauranteJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.categorias.ICategoriaEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos.IPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias.CategoriaRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.platos.PlatoRepository;
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
    private final PlatoRepository platoRepository;
    private final IPlatoEntityMapper iPlatoEntityMapper;
    private final CategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper iCategoriaEntityMapper;
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

    @Bean
    public PlatoPersistencePort platoPersistencePort(){
        return new PlatoJpaAdapter(platoRepository, iPlatoEntityMapper);
    }

    @Bean
    public IPlatoServicePort iPlatoServicePort(){
        return new PlatoUseCase(platoPersistencePort());
    }

    @Bean
    public CategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaJpaAdapter(categoriaRepository, iCategoriaEntityMapper);
    }

    @Bean
    public ICategoriaServicePort iCategoriaServicePort(){
        return new CategoriaUseCase(categoriaPersistencePort());
    }

}
