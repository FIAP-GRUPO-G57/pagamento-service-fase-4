package br.com.fiap.pagamento.service.main.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "gateway.data.payment")
@Configuration("paymentProperties")
public class GatewayPaymentConfiguration {
    private String notification;
    private String sku;
    private String token;
    private Long seller;
    private String pos;
}
