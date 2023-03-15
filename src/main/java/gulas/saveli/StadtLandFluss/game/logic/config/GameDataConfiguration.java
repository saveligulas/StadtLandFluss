package gulas.saveli.StadtLandFluss.game.logic.config;

import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GameDataConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GameData gameData() {
        return new GameData();
    }
}
