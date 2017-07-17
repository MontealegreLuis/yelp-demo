/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montealegreluis.yelpv3.SearchCriteria;
import com.montealegreluis.yelpv3.Yelp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.StringWriter;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Controller
public class BusinessesController {
    private final Yelp yelp;

    public BusinessesController(
        @Value("${yelp.api.client_id}") String yelpId,
        @Value("${yelp.api.client_secret}")String yelpSecret
    ) {
        yelp = new Yelp(yelpId, yelpSecret);
    }

    @GetMapping(value = "/businesses/{location}", produces = "application/json")
    @ResponseBody
    public String showBusinesses(@PathVariable String location) throws IOException {
        return toJson(yelp.search(SearchCriteria.byLocation(location).limit(5)));
    }

    @GetMapping(value = "/business/{yelpId}", produces = "application/json")
    @ResponseBody
    public String showBusiness(@PathVariable String yelpId) throws IOException {
        return toJson(yelp.searchById(yelpId));
    }

    private String toJson(Object response) throws IOException {
        StringWriter sw =new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(FIELD, ANY);
        mapper.writeValue(sw, response);
        String json = sw.toString();
        sw.close();

        return json;
    }
}
