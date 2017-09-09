/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.montealegreluis.yelpv3.businesses.PricingLevel;
import com.montealegreluis.yelpv3.search.Limit;
import com.montealegreluis.yelpv3.search.Offset;
import com.montealegreluis.yelpv3.search.Radius;
import com.montealegreluis.yelpv3.search.SearchCriteria;

public class SearchRequest {
    private static final int PAGE_SIZE = 5;
    private String location;
    private Integer offset;
    private String categories;
    private String pricing;
    private Double latitude;
    private Double longitude;
    private String openNow;
    private Integer distance;

    public SearchRequest() {
    }

    public SearchCriteria criteria() {
        SearchCriteria criteria;
        if (location != null) criteria = SearchCriteria.byLocation(location);
        else criteria = SearchCriteria.byCoordinates(latitude, longitude);
        criteria.limit(Limit.of(PAGE_SIZE));
        if (offset != null) criteria.offset(Offset.of(offset));
        if (!"".equals(categories)) criteria.inCategories(categories);
        if (pricing != null) criteria.withPricing(PricingLevel.fromSymbol(pricing));
        if (openNow != null) criteria.openNow();
        if (distance != null) criteria.withinARadiusOf(Radius.inMiles(distance));

        return criteria;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setOpenNow(String openNow) {
        this.openNow = openNow;
    }

    public String getOpenNow(String openNow) {
        return openNow;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
