package br.com.fiap.pagamento.service.main.configuration;


import br.com.fiap.pagamento.service.infra.controllers.ContentTypeOptionsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ContentTypeOptionsFilter> contentTypeOptionsFilter() {
        FilterRegistrationBean<ContentTypeOptionsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ContentTypeOptionsFilter());
        registrationBean.addUrlPatterns("/*"); // Aplicar o filtro a todas as URLs
        return registrationBean;
    }
}
