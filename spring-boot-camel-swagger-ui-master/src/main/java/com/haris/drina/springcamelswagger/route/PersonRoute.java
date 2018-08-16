package com.haris.drina.springcamelswagger.route;

import com.haris.drina.springcamelswagger.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class PersonRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception{
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest("/person").get().outType(Person.class)
                .to("direct:talk");
        from("direct:talk")
                .process(exchange -> {
                    Person p = new Person();
                    p.setFirstname("Haris");
                    p.setLastname("Drina");
                    exchange.getIn().setBody(p);
                });

        rest("/person").post().consumes("application/json").type(Person.class)
                .to("direct:person");
        from("direct:person")
                .process(exchange -> {
                    Person p = new Person();
                    exchange.getIn().setBody(p);
                });

    }
}
