/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.montealegreluis.yelpv3.businesses.PricingLevel;
import com.montealegreluis.yelpv3.search.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRequest {
    private static final int PAGE_SIZE = 5;
    private String location;
    private Integer offset;
    private String categories;
    private String pricing;
    private Double latitude;
    private Double longitude;
    private String openNow;
    private String openAt;
    private Integer distance;
    private String sorting;
    private List<String> attributes;

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
        if (sorting != null) criteria.sortBy(SortingMode.valueOf(sorting.toUpperCase()));
        if (openAt != null) addOpenAtTo(criteria);
        if (attributes != null) addAttributesTo(criteria);

        return criteria;
    }

    private void addOpenAtTo(SearchCriteria criteria) {
        try {
            Date openAtDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse(openAt);
            criteria.openAt(openAtDateTime.toInstant().getEpochSecond());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAttributesTo(SearchCriteria criteria) {
        Attribute[] businessAttributes = attributes
            .stream()
            .map(attribute -> Attribute.valueOf(attribute.toUpperCase()))
            .collect(Collectors.toList())
            .toArray(new Attribute[attributes.size()])
        ;
        criteria.withAttributes(businessAttributes);
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

    public String getOpenNow() {
        return openNow;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }
    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }
}
