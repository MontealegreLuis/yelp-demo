/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.montealegreluis.yelpv3.search.SearchCriteria;

public class SearchRequest {
    private String location;

    public SearchRequest() {
    }

    public SearchCriteria criteria() {
        return SearchCriteria.byLocation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
