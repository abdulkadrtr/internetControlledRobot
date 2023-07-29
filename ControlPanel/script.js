var map;

function onPageLoad() {
    var longitude = 28.979530
    var latitude = 41.015137
    map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([longitude,latitude]),
        zoom: 6
    })
    });
}

document.addEventListener("DOMContentLoaded", onPageLoad);

function connect() {
  phoneIp = document.getElementById("phone-ip").value;
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
          alert("Bağlantı kuruldu");
          document.getElementById("video").src = "http://" + phoneIp + ":4747/video";
          setInterval(updateSensors,500);
      }
  };
  xhr.open("GET", "http://" + phoneIp + ":8080/light", true);
  xhr.send();
}

function sendControlSignal(signal) {
    var data = JSON.stringify({
        "controlSignal" : signal.toString()
    })
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://" + phoneIp + ":8080/control", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                console.log(response);
            } else {
                console.log('There was a problem with the request.');
            }
        }
    };
    xhr.send(data);
}
function updateGyroSensor(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var gyro = JSON.parse(this.responseText);
            document.getElementById("gyroscope-x").innerHTML = gyro.gyroscopeX;
            document.getElementById("gyroscope-y").innerHTML = gyro.gyroscopeY;
            document.getElementById("gyroscope-z").innerHTML = gyro.gyroscopeZ;
        }
    };
    xhr.open("GET", "http://" + phoneIp + ":8080/gyro", true);
    xhr.send();
}
function updateAccelerometerSensor(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var accel = JSON.parse(this.responseText);
            document.getElementById("accelerometer-x").innerHTML = accel.accelerometerX;
            document.getElementById("accelerometer-y").innerHTML = accel.accelerometerY;
            document.getElementById("accelerometer-z").innerHTML = accel.accelerometerZ;
        }
    };
    xhr.open("GET", "http://" + phoneIp + ":8080/accelerometer", true);
    xhr.send();
}
function updateOrientationSensor(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var orient = JSON.parse(this.responseText);
            document.getElementById("orientation-alpha").innerHTML = orient.orientationX;
            document.getElementById("orientation-beta").innerHTML = orient.orientationY;
            document.getElementById("orientation-gamma").innerHTML = orient.orientationZ;
        }
    };
    xhr.open("GET", "http://" + phoneIp + ":8080/orientation", true);
    xhr.send();
}
function updateLightSensor(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200) {
            var lightV = JSON.parse(this.responseText);
            document.getElementById("light-value").innerHTML = lightV.light;
        }
    };
    xhr.open("GET", "http://" + phoneIp + ":8080/light", true);
    xhr.send();
}
function updateGpsSensor(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);
            var view = map.getView();
            longitude = parseFloat(data.longitude);
            latitude = parseFloat(data.latitude);
            if(longitude == 0.0){
                longitude = 28.979530
                latitude = 41.015137
            }
            map.getView().setCenter(ol.proj.fromLonLat([longitude,latitude]));
            map.getView().setZoom(18);
            var markerLayer = map.getLayers().getArray().find(layer => layer instanceof ol.layer.Vector);
            if (markerLayer) {
            var markerSource = markerLayer.getSource();
            markerSource.clear();
            } else {
            markerLayer = new ol.layer.Vector({
                source: new ol.source.Vector(),
                style: new ol.style.Style({
                image: new ol.style.Icon({
                    anchor: [0.5, 1],
                    src: 'https://openlayers.org/en/latest/examples/data/icon.png'
                })
                })
            });
            map.addLayer(markerLayer);
            }
            var newMarker = new ol.Feature(new ol.geom.Point(ol.proj.fromLonLat([longitude, latitude])));
            markerLayer.getSource().addFeature(newMarker);
        }
    };
    xhr.open("GET", "http://" + phoneIp + ":8080/gps", true);
    xhr.send();
}

function updateSensors() {
  updateGyroSensor();
  updateAccelerometerSensor();
  updateOrientationSensor();
  updateLightSensor();
  updateGpsSensor();
}