/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.montealegreluis.yelpv3.Yelp;
import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.SearchResult;
import com.montealegreluis.yelpv3.businesses.distance.Distance;
import com.montealegreluis.yelpv3.client.Credentials;
import com.montealegreluis.yelpv3.jsonparser.SearchCategoryParser;
import com.montealegreluis.yelpv3.search.SearchCategories;
import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Controller
public class YelpController {
    private final Yelp yelp;
    private SearchCategories categories;
    private final ObjectWriter writer;

    public YelpController(
        @Value("${yelp.api.client_id}") String yelpId,
        @Value("${yelp.api.client_secret}") String yelpSecret
    ) {
        yelp = new Yelp(new Credentials(yelpId, yelpSecret));
        categories = SearchCategoryParser.all()
            .main()
            .availableAt(new Locale("en", "US"))
        ;
        ObjectMapper mapper = new ObjectMapper()
            .setVisibility(ALL, NONE)
            .setVisibility(FIELD, ANY)
        ;
        writer = mapper.writerWithDefaultPrettyPrinter();
    }

    @GetMapping("/")
    public String showSearchForm(Model viewModel) {
        viewModel.addAttribute("categories", categories);
        return "search";
    }

    @GetMapping("/search")
    public String showSearchResults(
        @ModelAttribute SearchRequest request,
        Model viewModel
    ) throws JsonProcessingException {
        SearchCriteria criteria = request.criteria();
        SearchResult result = yelp.search(criteria).searchResult();
        viewModel.addAttribute("categories", categories);
        viewModel.addAttribute("result", result);
        viewModel.addAttribute(
            "businesses",
            writer.writeValueAsString(result.businessesToMap(businesses -> businesses
                .stream()
                .map(business -> {
                    Map<String, Object> businessInformation = new HashMap<>();
                    businessInformation.put("id", business.id);
                    businessInformation.put("name", business.name);
                    businessInformation.put("coordinates", business.coordinates);
                    return businessInformation;
                })
                .collect(Collectors.toList()))
            )
        );
        viewModel.addAttribute("mapCenter", writer.writeValueAsString(result.region.center));
        viewModel.addAttribute("criteria", criteria);
        viewModel.addAttribute("pagination", criteria.pagination(result.total));
        viewModel.addAttribute("queryString", criteria.toQueryString());
        return "search";
    }

    @GetMapping("/business/{businessId}")
    public String viewBusiness(
        @PathVariable String businessId,
        Model viewModel
    ) throws JsonProcessingException {
        Business business = yelp.searchById(businessId).business();
        SearchCriteria criteria = SearchCriteria.byLocation(business.location.city);
        SearchCriteria similarBusinessCriteria = SearchCriteria
            .byCoordinates(business.coordinates)
            .inCategories(business.categories.toCsv())
            .withinARadiusOf(Distance.inMiles(5))
            .limit(3)
        ;

        viewModel.addAttribute("business", business);
        viewModel.addAttribute("mapCenter", writer.writeValueAsString(business.coordinates));
        viewModel.addAttribute("reviews", yelp.reviews(businessId).reviews());
        viewModel.addAttribute("criteria", criteria);
        viewModel.addAttribute("queryString", criteria.toQueryString());
        viewModel.addAttribute("today", LocalDate.now().getDayOfWeek());
        viewModel.addAttribute("format", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"));
        viewModel.addAttribute(
            "similarBusinesses",
            yelp.search(similarBusinessCriteria).searchResult().businesses
        );

        return "business";
    }
}
