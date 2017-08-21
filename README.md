# Spring Boot + Yelp API V3 demo

[![codebeat badge](https://codebeat.co/badges/0704b6a2-cd9a-446b-bc67-4db792c641a0)](https://codebeat.co/projects/github-com-montealegreluis-yelp-demo-master)

This is a Spring Boot application to demonstrate how to use this
[Java Client for the Yelp API v3](https://github.com/MontealegreLuis/yelpv3-java-client).

The client can be used in the following ways:

* As a proxy to the original API
* To produce a custom JSON response
* To produce a server side HTML response

It currently support the following end-points

* [Search](https://www.yelp.com/developers/documentation/v3/business_search)
* [Business](https://www.yelp.com/developers/documentation/v3/business)
* [Reviews](https://www.yelp.com/developers/documentation/v3/business_reviews)

## Installation

Use maven as usual

```
mvn package
./mvnw spring-boot:run
```

### Use

#### As a proxy to the original API

Visit the following URLs

* `/yelp/search/{location}` to search a business by its location for instance `San Antonio`. Take
  a look at the method `BusinessController#searchYelp` for more details
* `/yelp/business/{yelpId}` to search a business by its Yelp ID, for instance
  `zócalo-mio-san-antonio-2`. Take a look a the method `BusinessController#yelpBusiness` for more
  details
  
#### Custom JSON response

The client comes with a set of DTOs that an be used to generate a JSON response  different from the
one returned by Yelp

Visit the following URLs

* `/businesses/{location}` to search a business by its location for instance `San Antonio`. Take
  a look at the method `BusinessController#showBusinesses` for more details
* `/business/{yelpId}.json` to search a business by its Yelp ID, for instance
  `zócalo-mio-san-antonio-2`. Take a look a the method `BusinessController#showBusiness` for more
  details

#### Server-side HTML response

The third alternative is to pass the DTOs provided by the library to the view layer, in this case
Thymeleaf to produces an HTML response.

Visit the following URLs

* `/` use the form to search a business by category and location, for instance `San Antonio`.
  Take a look at the methods `YelpController#showSearchForm` and `YelpController#showSearchResults`
  for more details
* `/business/{businessId}` to search a business by its Yelp ID, for instance 
  `zócalo-mio-san-antonio-2`. Take a look a the method `YelpController#viewBusiness` for more
  details
