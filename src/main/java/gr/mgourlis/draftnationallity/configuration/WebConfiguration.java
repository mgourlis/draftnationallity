package gr.mgourlis.draftnationallity.configuration;

import gr.mgourlis.draftnationallity.filters.changePasswordFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, 10));
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public FilterRegistrationBean resetPasswordBean() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new changePasswordFilter());
        filterRegBean.addUrlPatterns("/**");
        filterRegBean.setEnabled(Boolean.TRUE);
        filterRegBean.setName("Reset Password");
        filterRegBean.setAsyncSupported(Boolean.TRUE);
        return filterRegBean;
    }

}
