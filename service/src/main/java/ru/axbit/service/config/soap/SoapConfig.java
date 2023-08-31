package ru.axbit.service.config.soap;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.axbit.service.service.journal.impl.ActionLogPrepareInInterceptor;
import ru.axbit.service.service.journal.impl.LogInputInterceptor;
import ru.axbit.service.service.journal.impl.LogOutputInterceptor;
import ru.axbit.vborovik.competence.userservice.v1.UserService;
import ru.axbit.vborovik.competence.userservice.v1.UserServicePortType;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс SOAP конфигурации сервиса userService.
 */
@Configuration
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SoapConfig {
    private final ActionLogPrepareInInterceptor actionLogPrepareInInterceptor;
    private final LogInputInterceptor logInputInterceptor;
    private final LogOutputInterceptor logOutputInterceptor;

    /**
     * Регистрация сервлета {@link ServletRegistrationBean}.
     *
     * @return {@link ServletRegistrationBean}.
     */
    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        ServletRegistrationBean<CXFServlet> servletReg = new ServletRegistrationBean<>(cxfServlet, "/services/*");
        servletReg.setLoadOnStartup(1);
        return servletReg;
    }

    /**
     * Путь к контексту сервлета.
     *
     * @return {@link DispatcherServletPath}
     */
    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "/";
    }

    /**
     * Получение компонента {@link SpringBus}, который предоставляет расширения для Apache CXF
     * для работы с Spring Framework.
     *
     * @return {@link SpringBus}.
     */
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * Получение компонента {@link Endpoint}, который используется для публикации конечной точки
     * по-заданному HTTP-адресу.
     *
     * @param bus                 принимает {@link SpringBus} компонент, предоставляющий расширения для Apache CXF.
     * @param userServicePortType принимает {@link UserServicePortType} SOAP интерфейс, описывающий методы
     *                            для взаимодействия приложения с БД.
     * @return возвращает {@link Endpoint} компонент.
     */
    @Bean
    @Profile("userService")
    public Endpoint createUserServiceEndpoint(SpringBus bus, @Qualifier("userServiceSoap")
    UserServicePortType userServicePortType) {
        EndpointImpl endpoint = new EndpointImpl(bus, userServicePortType, SOAPBinding.SOAP12HTTP_BINDING);
        endpoint.setProperties(getEndpointProps());
        endpoint.setServiceName(UserService.SERVICE);
        endpoint.getInInterceptors().add(actionLogPrepareInInterceptor);
        endpoint.getInInterceptors().add(new LoggingInInterceptor());
        endpoint.getOutInterceptors().add(new LoggingOutInterceptor());
        endpoint.getInInterceptors().add(logInputInterceptor);
        endpoint.getOutInterceptors().add(logOutputInterceptor);
        endpoint.getInFaultInterceptors().add(new LoggingInInterceptor());
        endpoint.getOutFaultInterceptors().add(new LoggingOutInterceptor());
        endpoint.setWsdlLocation("classpath:wsdl/v1/userService/userService.wsdl");
        endpoint.publish("competence/userService/v1");

        return endpoint;
    }

    /**
     * Получение характеристик конечной точки {@link Endpoint}.
     *
     * @return {@link Map} характеристик.
     */
    private Map<String, Object> getEndpointProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("schema-validation-enabled", "REQUEST");
        return props;
    }
}
