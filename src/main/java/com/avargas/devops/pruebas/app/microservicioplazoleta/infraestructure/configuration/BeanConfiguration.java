package com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.configuration;

import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.categorias.ICategoriaServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.INotificacionServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.pedido.IPedidoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.platos.IPlatoServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.api.restaurante.RestauranteServicePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.categorias.CategoriaPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedido.IPedidoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.pedidoplatos.IPedidoPlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.platos.PlatoPersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.spi.restaurante.RestaurantePersistencePort;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.categorias.CategoriaUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.pedido.PedidoUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.platos.PlatoUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.domain.usecase.restaurante.RestauranteUseCase;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.adapter.NotificacionServiceAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.client.impl.GenericHttpClient;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.categorias.CategoriaJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.pedido.PedidoJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.pedidoplato.PedidoPlatoJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.platos.PlatoJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.adapter.restaurante.RestauranteJpaAdapter;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.categorias.ICategoriaEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedido.IPedidoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato.IPedidoPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.pedidoplato.impl.PedidoPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.platos.IPlatoEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.mapper.restaurantes.IRestauranteEntityMapper;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.categorias.CategoriaRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedido.PedidoEntityRepository;
import com.avargas.devops.pruebas.app.microservicioplazoleta.infraestructure.out.jpa.repositories.pedidoplato.PedidoPlatoRepository;
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
    private final PedidoEntityRepository pedidoRepository;
    private final IPedidoEntityMapper pedidoEntityMapper;
    private final PedidoPlatoRepository pedidoPlatoRepository;
    private final PedidoPlatoEntityMapper pedidoPlatoEntityMapper;
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

    @Bean
    public IPedidoPersistencePort pedidoServicePort(){
        return new PedidoJpaAdapter(pedidoRepository, pedidoEntityMapper);

    }

    @Bean
    public IPedidoPersistencePort pedidoPersistencePort(){
        return new PedidoJpaAdapter(pedidoRepository, pedidoEntityMapper);
    }

    @Bean
    public IPedidoPlatoPersistencePort pedidoPlatoPersistencePort(){
        return new PedidoPlatoJpaAdapter(
                pedidoPlatoRepository,
                pedidoRepository,
                platoRepository,
                pedidoPlatoEntityMapper
        );
    }

    @Bean
    public INotificacionServicePort notificacionServicePort(GenericHttpClient genericHttpClient) {
        return new NotificacionServiceAdapter(genericHttpClient);
    }



    @Bean
    public IPedidoServicePort iPedidoServicePort(GenericHttpClient genericHttpClient){
        return new PedidoUseCase(
                pedidoPersistencePort(),
                platoPersistencePort(),
                pedidoPlatoPersistencePort(),
                notificacionServicePort(genericHttpClient)
        );
    }



}
