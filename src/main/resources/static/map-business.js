/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
"use strict";

function Map(canvas, center) {
    const map = new google.maps.Map(canvas, {
        zoom: 15,
        center: {lat: center.latitude, lng: center.longitude}
    });
    const window = new google.maps.InfoWindow();

    this.changeTypeTo = function (newType) {
        map.setMapTypeId(newType);
    };

    this.addMarkerAt = function (latitude, longitude) {
        return new google.maps.Marker({position: {lat: latitude, lng: longitude}, map: map});
    };

    this.addMarkers = function (businesses) {
        var bounds = new google.maps.LatLngBounds();
        var marker;
        for (var i = 0; i < businesses.length; i++) {
            marker = this.addMarkerAt(
                businesses[i].coordinates.latitude,
                businesses[i].coordinates.longitude
            );
            this.addInfoWindowTo(marker, businesses[i]);
            bounds.extend(marker.getPosition());
        }
        map.fitBounds(bounds);
    };

    this.addInfoWindowTo = function (marker, business) {
        marker.addListener('click', function() {
            window.setContent("<p><a href='#" + business.id + "'>" + business.name + "</a></p>");
            window.open(map, marker);
        });
    };
}
