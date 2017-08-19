/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
function Map(canvas, latitude, longitude) {
    this.show = function () {
        const business = {lat: latitude, lng: longitude};
        const map = new google.maps.Map(canvas, {
            zoom: 19,
            center: business,
            mapTypeId: google.maps.MapTypeId.SATELLITE
        });
        new google.maps.Marker({position: business, map: map});
    }
}
