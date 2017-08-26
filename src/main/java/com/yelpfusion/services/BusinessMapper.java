/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.services;

import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.Businesses;
import com.montealegreluis.yelpv3.businesses.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BusinessMapper implements SearchResult.CustomBusinessesMapper{
    @Override
    public List<Map<String, Object>> map(Businesses businesses) {
        return businesses
            .stream()
            .map(this::infoForGoogleMap)
            .collect(Collectors.toList())
        ;
    }

    private Map<String, Object> infoForGoogleMap(Business business) {
        Map<String, Object> businessInformation = new HashMap<>();
        businessInformation.put("id", business.id);
        businessInformation.put("name", business.name);
        businessInformation.put("coordinates", business.coordinates);
        return businessInformation;
    }
}
