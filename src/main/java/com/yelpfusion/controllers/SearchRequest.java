/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.yelpfusion.controllers;

import com.montealegreluis.yelpv3.search.SearchCriteria;

public class SearchRequest {
    private static final int PAGE_SIZE = 5;
    private String location;
    private Integer offset;
    private String categories;

    public SearchRequest() {
    }

    public SearchCriteria criteria() {
        SearchCriteria criteria = SearchCriteria.byLocation(location).limit(PAGE_SIZE);
        if (offset != null) criteria.offset(offset);
        if (!"".equals(categories)) criteria.inCategories(categories);

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
}
