/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
"use strict";

function Map(canvas, center) {
    const map = new google.maps.Map(canvas, {
        zoom: 15,
        center: {lat: center.latitude, lng: center.longitude}
    });

    this.changeTypeTo = function (newType) {
        map.setMapTypeId(newType);
    };

    this.addMarkerAt = function (latitude, longitude) {
        return new google.maps.Marker({position: {lat: latitude, lng: longitude}, map: map});
    };

    this.addMarkers = function (coordinates) {
        var bounds = new google.maps.LatLngBounds();
        var marker;
        for (var i = 0; i < coordinates.length; i++) {
            marker = this.addMarkerAt(coordinates[i].latitude, coordinates[i].longitude);
            bounds.extend(marker.getPosition());
        }
        map.fitBounds(bounds);
    }
}
