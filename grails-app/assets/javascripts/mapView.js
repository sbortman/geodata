/**
 * Created by sbortman on 4/27/15.
 */

//= require openlayers/ol-debug.js
//= require jquery.js
//= require_self

MapView = (function ()
{
    var map;
    var layers;

    return {
        initialize: function ()
        {
            layers = [
                new ol.layer.Tile( {
                    source: new ol.source.TileWMS( {
                        url: 'http://demo.boundlessgeo.com/geoserver/wms',
                        params: {
                            'LAYERS': 'ne:NE1_HR_LC_SR_W_DR'
                        }
                    } )
                } ),
                new ol.layer.Tile( {
                    source: new ol.source.TileWMS( {
                        url: '/geodata/city/plotCities',
                        params: {LAYERS: 'city', VERSION: '1.1.1'}
                    } )
                } )
            ];

            map = new ol.Map( {
                controls: ol.control.defaults().extend( [
                    new ol.control.ScaleLine( {
                        units: 'degrees'
                    } )
                ] ),
                layers: layers,
                target: 'map',
                view: new ol.View( {
                    projection: 'EPSG:4326',
                    center: [0, 0],
                    zoom: 2
                } )
            } );
        }
    };
})();