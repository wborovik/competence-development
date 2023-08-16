package ru.axbit.service.config.soap;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.axbit.vborovik.competence.userservice.v1.UserService;
import ru.axbit.vborovik.competence.userservice.v1.UserServicePortType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Profile("userService")
public class SoapConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        ServletRegistrationBean<CXFServlet> servletReg = new ServletRegistrationBean<>(cxfServlet, "/services/*");
        servletReg.setLoadOnStartup(1);
        return servletReg;
    }

    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "/";
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint createUserServiceEndpoint(SpringBus bus, @Qualifier("userServiceSoap") UserServicePortType userServicePortType) {
        EndpointImpl endpoint = new EndpointImpl(bus, userServicePortType, SOAPBinding.SOAP12HTTP_BINDING);
        endpoint.setProperties(getEndpointProps());
        endpoint.setServiceName(UserService.SERVICE);
        endpoint.setWsdlLocation("classpath:wsdl/v1/userService/userService.wsdl");
        endpoint.publish("competence/userService/v1");

        return endpoint;
    }

    private Map<String, Object> getEndpointProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("schema-validation-enabled", "REQUEST");

        return props;
    }
}
