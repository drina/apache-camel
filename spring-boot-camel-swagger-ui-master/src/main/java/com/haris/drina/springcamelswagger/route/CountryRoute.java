package com.haris.drina.springcamelswagger.route;

import com.haris.drina.springcamelswagger.model.Country;
import com.haris.drina.springcamelswagger.model.Person;
import com.haris.drina.springcamelswagger.processor.CountrySelectProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CountryRoute extends RouteBuilder {

    @Autowired
    CountrySelectProcessor countrySelectProcessor;

    @Override
    public void configure() throws Exception{
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);


        rest("/test").get().outType(Country.class)
                .to("direct:restCall");
        from("direct:restCall")
                .process(countrySelectProcessor)
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI,simple("http://restcountries.eu/rest/v2/alpha/${header.countryId}"))
                .to("http://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class);
    }
}