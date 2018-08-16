package com.haris.drina.springcamelswagger.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component

public class CountrySelectProcessor implements Processor{

    List<String> countryList = Arrays.asList("us","in","gb","cn","jp");


    @Override
    public void process(Exchange exchange)  {

        Random random = new Random();
        String countryCode = countryList.get(random.nextInt(countryList.size()-1));
        exchange.getIn().setHeader("countryId", countryCode);
    }
}
