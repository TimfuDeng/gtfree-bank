var gtmapMap = (function(windows,$,undefined){
    var _map = null;
    var _geoFetched=false;
    var _geoLayer=null;
    var _popupContentOption={
        autoClose: false,
        opacity: 0.8
    };

    var _tiandituBaseUrl = "http://t0.tianditu.cn";
    var _tiandituUrlParams = "/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}";
    var tiandituMinZoom = 4;
    var tiandituMaxZoom = 18;
    var _initializeMap=function(){
        var lt5 = L.tileLayer(_tiandituBaseUrl+"/vec_w" +_tiandituUrlParams+"&LAYER=vec", {
            minZoom: tiandituMinZoom,
            maxZoom: tiandituMaxZoom
        });

        var ter = L.tileLayer(_tiandituBaseUrl+"/ter_w" + _tiandituUrlParams+"&LAYER=ter", {
            minZoom: tiandituMinZoom,
            maxZoom: tiandituMaxZoom
        });

        var img = L.tileLayer(_tiandituBaseUrl+"/img_w" + _tiandituUrlParams+"&LAYER=img" , {
            minZoom: tiandituMinZoom,
            maxZoom: tiandituMaxZoom
        });

        var lt2 = L.tileLayer(_tiandituBaseUrl+"/cva_w"+ _tiandituUrlParams+"&LAYER=cva", {
            minZoom: tiandituMinZoom,
            maxZoom: tiandituMaxZoom
        });

        var baseMaps = {
            "地图": lt5,
            "地形": ter,
            "影像": img
        };
        var overlayMaps = {
            "标注": lt2
        };
        var layersControl = new L.Control.Layers(baseMaps, overlayMaps);

        var layers = [lt5, lt2];

        _map = L.map('map',{
            center:[33.304756, 120.038600],
            zoom:8,
            minZoom:7,
            maxZoom:18,
            layers:layers
        });
        _map.addControl(layersControl);
        L.control.scale({'position':'bottomleft','metric':true,'imperial':false}).addTo(_map);
        _addGraphicLayer();
    };

    var _resizeMapAndLocateGeometry=function(url){
        if(_geoFetched==false){
            _map.invalidateSize();
            $.get(url,function(data){
                _geoFetched = true;
                if(data==null||data=='')return;
                _addGeo(data);
            });
        }


    };
    var _geoToPolygon=function (geo) {
        return L.polygon(L.GeoJSON.coordsToLatLngs(geo.geometry.coordinates, geo.geometry.type === 'Polygon' ? 1 : 2, L.GeoJSON.coordsToLatLng));
    };
    var _getBounds=function (geo) {
        var b1, b2, sw1, ne1, sw2, ne2;
        var sw, ne;
        var geov;
        for (var i = 0; i < geo.length; i++) {
            geov = geo[i];
            if (typeof b1 === 'undefined') {
                b1 = _geoToPolygon(geo[i]).getBounds();
                sw1 = b1.getSouthWest(), ne1 = b1.getNorthEast();
            } else if (typeof b2 === 'undefined') {
                b2 = _geoToPolygon(geo[i]).getBounds();
                sw2 = b2.getSouthWest(), ne2 = b2.getNorthEast();
                sw = sw1.lng <= sw2.lng && sw1.lat <= sw2.lat ? sw1 : sw2, ne = ne1.lng >= ne2.lng && ne1.lat >= ne2.lat ? ne1 : ne2;
            }
            if (typeof sw !== 'undefined' && typeof ne != 'undefined') {
                var b3 = _geoToPolygon(geo[i]).getBounds();
                var sw3 = b3.getSouthWest(), ne3 = b3.getNorthEast();
                sw = sw.lng <= sw3.lng && sw.lat <= sw3.lat ? sw : sw3;
                ne = ne.lng >= ne3.lng && ne.lat >= ne3.lat ? ne : ne3;
            }
        }
        var result = typeof sw === 'undefined' || typeof ne === 'undefined' ? typeof geov === 'undefined' ? [] : _geoToPolygon(geo[0]).getBounds() : L.latLngBounds(sw, ne);
        return result;
    };
    var _fitGeo=function(geo) {
        var b = _getBounds(geo);
        _map.fitBounds(b);
    };
    var _addGeo=function(geo) {
        _geoLayer.addData(geo);
        _fitGeo(geo);
        _showInfo(geo);
    };
    var _addGraphicLayer=function(){
        var options = {
            style: function () {
                return {color: '#ff0000'};
            },
            onEachFeature: function (feature, layer) {
                layer.bindPopup(_getPopupContent(feature),_popupContentOption);
            }
        };
        _geoLayer = L.geoJson([], options).addTo(_map);
    };
    var _getPopupContent=function(feature){
        var popupContent = "<p>地块编号： " +
            feature.properties.title + "<p>";

        if (feature.properties && feature.properties.popupContent) {
            popupContent += feature.properties.popupContent;
        }
        return popupContent;
    };
    var _showInfo =function(geo) {
        var b = _getBounds(geo);
        _geoLayer.openPopup(b.getCenter());
    };
    return{
        initializeMap:_initializeMap,
        resizeMapAndLocateGeometry:_resizeMapAndLocateGeometry
    }
})(window,jQuery);