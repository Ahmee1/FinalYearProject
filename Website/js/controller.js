var map;
var line = [];
var poss = [];
var searchMarker = [];
var markers = [];
var markers2 = [];

function initMap() {
    var uluru = {
        lat : 22.27897,
        lng : 114.172923
    };
    
    map = new google.maps.Map(document.getElementById('map'), {
                                  zoom: 12,
                                  center: uluru
                                  });

    google.maps.event.addListener(map, 'click', function(event) {
        if(typeof markers !== 'undefined' && markers.length > 0) {
            markers.forEach(function(marker) {
                marker.setMap(null);
            });
        }
        if(typeof markers2 !== 'undefined' && markers2.length > 0) {
            markers2.forEach(function(marker) {
                marker.setMap(null);
            });
        }
        if (searchMarker.length == 2) {
            var oMarker = searchMarker[1];
            searchMarker[0].setMap(null);
            searchMarker = []; 
            searchMarker.push(oMarker);
            document.getElementById('start').value = document.getElementById('end').value;       
            document.getElementById('end').value = event.latLng;        
        } else if (searchMarker.length == 1){
            document.getElementById('end').value = event.latLng;
        } else {
            document.getElementById('start').value = event.latLng;
        }
        marker = new google.maps.Marker({position: event.latLng, map: map});
        searchMarker.push(marker);
    });

    // Create the search box and link it to the UI element.
    var inputs = document.getElementById('pac-input1');
    var inpute = document.getElementById('pac-input2');
    var searchBox1 = new google.maps.places.SearchBox(inputs);
    var searchBox2 = new google.maps.places.SearchBox(inpute);
    var left = false;
    var right = false;
    //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input1);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function() {
      searchBox1.setBounds(map.getBounds());
      searchBox2.setBounds(map.getBounds());
    });
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            for (var x = 0; x < Object.keys(myObj).length; x++) {
                
                var points = [];
                var pos = {
                    lat : parseFloat(myObj[x]['route'][0]['lat']),
                    lng : parseFloat(myObj[x]['route'][0]['lng'])                   
                };
                for (var y = 0; y < Object.keys(myObj[x]['route']).length; y++) {
                    points.push({
                        lat : parseFloat(myObj[x]['route'][y]['lat']),  
                        lng : parseFloat(myObj[x]['route'][y]['lng'])
                    });
                }
                
                var flightPath = new google.maps.Polyline({
                    path : points,
                    geodesic : true,
                    strokeColor : '#FF0000',
                    strokeOpacity : 1.0,
                    strokeWeight : 2
                });
                line.push(flightPath);
                flightPath.setMap(map);
                map.setCenter(pos);
            }
        }
    };
    xmlhttp.open("GET", "./JSON_search.php?action=all", true);
    xmlhttp.send();
    searchBox1.addListener('places_changed', function() {
        if(typeof searchMarker !== 'undefined' && searchMarker.length > 0) {
            searchMarker.forEach(function(marker) {
                marker.setMap(null);
            });
        }
      var places = searchBox1.getPlaces();

      if (places.length == 0) {
        return;
      }
      if (left) {
        // Clear out the old markers.
        markers.forEach(function(marker) {
            marker.setMap(null);
        });
        markers = [];
      } else {
        left = true;
      } 

      // For each place, get the icon, name and location.
      var bounds = new google.maps.LatLngBounds();
      places.forEach(function(place) {
        if (!place.geometry) {
          console.log("Returned place contains no geometry");
          return;
        }
        var icon = {
          url: place.icon,
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(25, 25)
        };

        // Create a marker for each place.
        markers.push(new google.maps.Marker({
          map: map,
          icon: icon,
          title: place.name,
          position: place.geometry.location
        }));
        document.getElementById("start").value = place.geometry.location;

        if (place.geometry.viewport) {
          // Only geocodes have viewport.
          bounds.union(place.geometry.viewport);
        } else {
          bounds.extend(place.geometry.location);
        }
      });
      
      map.fitBounds(bounds);
    });
    searchBox2.addListener('places_changed', function() {
        if(typeof searchMarker !== 'undefined' && searchMarker.length > 0) {
            searchMarker.setMap(null);
        }
        var places = searchBox2.getPlaces();
  
        if (places.length == 0) {
          return;
        }

        if (right) {
            // Clear out the old markers.
            markers2.forEach(function(marker2) {
                marker2.setMap(null);
            });
            markers2 = [];
          } else {
            right = true;
          } 
  
        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function(place) {
          if (!place.geometry) {
            console.log("Returned place contains no geometry");
            return;
          }
          var icon = {
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(25, 25)
          };
  
          // Create a marker for each place.
          markers2.push(new google.maps.Marker({
            map: map,
            icon: icon,
            title: place.name,
            position: place.geometry.location
          }));
          document.getElementById("end").value = place.geometry.location;
  
          if (place.geometry.viewport) {
            // Only geocodes have viewport.
            bounds.union(place.geometry.viewport);
          } else {
            bounds.extend(place.geometry.location);
          }
        });
        map.fitBounds(bounds);
        
    });
}
function initMapHistory() {
    var uluru = {
        lat : 22.27897,
        lng : 114.172923
    };
    map = new google.maps.Map(document.getElementById('map'), {
                                  zoom: 12,
                                  center: uluru
                                  });

    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            for (var x = 0; x < Object.keys(myObj).length; x++) {
                
                var points = [];
                var pos = {
                    lat : parseFloat(myObj[x]['route'][0]['lat']),
                    lng : parseFloat(myObj[x]['route'][0]['lng'])                   
                };
                for (var y = 0; y < Object.keys(myObj[x]['route']).length; y++) {
                    points.push({
                        lat : parseFloat(myObj[x]['route'][y]['lat']),  
                        lng : parseFloat(myObj[x]['route'][y]['lng'])
                    });
                }
                
                var flightPath = new google.maps.Polyline({
                    path : points,
                    geodesic : true,
                    strokeColor : '#FF0000',
                    strokeOpacity : 1.0,
                    strokeWeight : 2
                });
                line.push(flightPath);
                flightPath.setMap(map);

                poss.push(pos);
                map.setCenter(pos);

                var tableRef = document.getElementById("activities");
                var newRow = tableRef.insertRow(tableRef.rows.length);

                var date  = newRow.insertCell(0);
                var dateText  = document.createTextNode(myObj[x]['date']);

                var name  = newRow.insertCell(1);
                var nameText  = document.createTextNode(myObj[x]['name']);

                var action  = newRow.insertCell(2);
                var btn = document.createElement("BUTTON");
                var actionText  = document.createTextNode('View');
                
                btn.setAttribute("id", x);

                btn.onclick = function() { // Note this is a function
                    for (i=0; i<line.length; i++) 
                    {                           
                        line[i].setMap(null); //or line[i].setVisible(false);
                    }
                    line[this.id].setMap(map);
                    map.setCenter(poss[this.id]);
                    map.setZoom(14);
                };

                btn.appendChild(actionText);                
                date.appendChild(dateText);
                name.appendChild(nameText);
                action.appendChild(btn);
            }
            $('#table').each(function () {
                var currentPage = 0;
                var numPerPage = 5;
                var $table = $(this);
                $table.bind('sortEnd repaginate', function () {
                    $table
                        .find('tbody tr').hide()
                        .slice(currentPage * numPerPage, (currentPage + 1) * numPerPage)
                        .show(10, function(){
                            // update zebra striping after rows are visible
                            $table.trigger('applyWidgets');
                        });
                });
                $table.trigger('repaginate');
                var numRows = $table.find('tbody tr').length;
                var numPages = Math.ceil(numRows / numPerPage);
                var $pager = $('<div class="pager"></div>');
                for (var page = 0; page < numPages; page++) {
                    $('<button class="page-number"></button>').text(page + 1).bind('click', {
                        newPage: page
                    }, function (event) {
                        currentPage = event.data['newPage'];
                        $table.trigger('repaginate');
                        $(this).addClass('active').siblings().removeClass('active');
                        $(this).attr("style","background-color:white").siblings().attr("style","");
                    }).appendTo($pager).addClass('clickable');
                }
                $pager.insertAfter($table).find('button.page-number:first').addClass('active');
                $pager.insertAfter($table).find('button.page-number:first').attr("style","background-color:white");
            });
        }
    };
    xmlhttp.open("GET", "./JSON_history.php?action=all", true);
    xmlhttp.send();
}

function initMapLocation() {
    var uluru = {
        lat : 22.27897,
        lng : 114.172923
    };
    map = new google.maps.Map(document.getElementById('map'), {
                                  zoom: 12,
                                  center: uluru
                                  });

    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    LocationMethod();
    setInterval(LocationMethod, 5000);
}

function searchAll() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            for (var x = 0; x < Object.keys(myObj).length; x++) {
                
                var points = [];
                var pos = {
                    lat : parseFloat(myObj[x]['route'][0]['lat']),
                    lng : parseFloat(myObj[x]['route'][0]['lng'])                   
                };
                for (var y = 0; y < Object.keys(myObj[x]['route']).length; y++) {
                    points.push({
                        lat : parseFloat(myObj[x]['route'][y]['lat']),  
                        lng : parseFloat(myObj[x]['route'][y]['lng'])
                    });
                }
                
                var flightPath = new google.maps.Polyline({
                    path : points,
                    geodesic : true,
                    strokeColor : '#FF0000',
                    strokeOpacity : 1.0,
                    strokeWeight : 2
                });
                line.push(flightPath);
                flightPath.setMap(map);
                map.setCenter(pos);
            }
        }
    };
    xmlhttp.open("GET", "./JSON_search.php?action=all", true);
    xmlhttp.send();
    event.preventDefault();
}

var markerLov = null;
function LocationMethod() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            
            var myObj = JSON.parse(this.responseText);
            var pos = {
                lat : parseFloat(myObj['lat']),
                lng : parseFloat(myObj['lng'])                   
            };
            if (markerLov!=null) {
                markerLov.setMap(null);
            }
            map.setCenter(pos);
            var marker = new google.maps.Marker({
                position: pos,
                map: map,
                title: 'Your Mobile Location'
              }); 
            markerLov = marker;      
        }
    };
    xmlhttp.open("GET", "./JSON_location.php?action=all", true);
    xmlhttp.send();
}

function searchByInfor(event) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            for (var x = 0; x < Object.keys(myObj).length; x++) {
                var points = [];
                var pos = {
                    lat : parseFloat(myObj[x]['route'][0]['lat']),
                    lng : parseFloat(myObj[x]['route'][0]['lng'])                   
                };
                for (var y = 0; y < Object.keys(myObj[x]['route']).length; y++) {
                    points.push({
                        lat : parseFloat(myObj[x]['route'][y]['lat']),  
                        lng : parseFloat(myObj[x]['route'][y]['lng'])
                    });
                }
                
                var flightPath = new google.maps.Polyline({
                    path : points,
                    geodesic : true,
                    strokeColor : '#FF0000',
                    strokeOpacity : 1.0,
                    strokeWeight : 2
                });
                flightPath.setMap(map);
                map.setCenter(pos);
                map.setZoom(16);
            }
        }
    };
    if (!document.getElementById("start").value== "" && !document.getElementById("end").value== "") {
        for (i=0; i<line.length; i++) 
            {                           
                line[i].setMap(null); //or line[i].setVisible(false);
            }                          
        xmlhttp.open("GET", "./JSON_search.php?action=info&start=" + document.getElementById("start").value + "&end=" + document.getElementById("end").value, true);
        xmlhttp.send();
    }
    event.preventDefault();
}
