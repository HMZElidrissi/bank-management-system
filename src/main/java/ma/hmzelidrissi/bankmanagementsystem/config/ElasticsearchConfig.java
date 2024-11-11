package ma.hmzelidrissi.bankmanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

import java.time.Duration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "ma.hmzelidrissi.bankmanagementsystem.repositories")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;

    @Value("${spring.elasticsearch.connection-timeout}")
    private Duration connectionTimeout;

    @Value("${spring.elasticsearch.socket-timeout}")
    private Duration socketTimeout;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .withConnectTimeout(connectionTimeout)
                .withSocketTimeout(socketTimeout)
                .build();
    }
}