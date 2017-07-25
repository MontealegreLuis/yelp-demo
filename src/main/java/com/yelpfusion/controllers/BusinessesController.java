/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.montealegreluis.yelpv3.Credentials;
import com.montealegreluis.yelpv3.SearchCriteria;
import com.montealegreluis.yelpv3.Yelp;
import com.yelpfusion.serializers.EnumSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Controller
public class BusinessesController {
    private final Yelp yelp;
    private final ObjectWriter writer;

    public BusinessesController(
        @Value("${yelp.api.client_id}") String yelpId,
        @Value("${yelp.api.client_secret}") String yelpSecret
    ) {
        yelp = new Yelp(new Credentials(yelpId, yelpSecret));
        ObjectMapper mapper = new ObjectMapper().setVisibility(FIELD, ANY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Enum.class, new EnumSerializer(Enum.class));
        mapper.registerModule(module);
        writer = mapper.writerWithDefaultPrettyPrinter();
    }

    @GetMapping(value = "/businesses/{location}", produces = "application/json")
    @ResponseBody
    public String showBusinesses(@PathVariable String location) throws IOException {
        return writer.writeValueAsString(yelp.search(SearchCriteria.byLocation(location).limit(5)));
    }

    @GetMapping(value = "/business/{yelpId}", produces = "application/json")
    @ResponseBody
    public String showBusiness(@PathVariable String yelpId) throws IOException {
        return writer.writeValueAsString(yelp.searchById(yelpId));
    }
}
