/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.montealegreluis.yelpv3.Yelp;
import com.montealegreluis.yelpv3.client.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class YelpController {
    private final Yelp yelp;

    public YelpController(
        @Value("${yelp.api.client_id}") String yelpId,
        @Value("${yelp.api.client_secret}") String yelpSecret
    ) {
        yelp = new Yelp(new Credentials(yelpId, yelpSecret));
    }

    @GetMapping("/")
    public String showSearchForm() {
        return "search";
    }

    @PostMapping("/")
    public String showSearchResults(@ModelAttribute SearchRequest request, Model viewModel) {
        viewModel.addAttribute("result", yelp.search(request.criteria()).searchResult());
        return "search";
    }
}
