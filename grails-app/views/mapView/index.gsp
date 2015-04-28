<%--
  Created by IntelliJ IDEA.
  User: sbortman
  Date: 4/27/15
  Time: 5:26 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="mapView.css"/>
</head>

<body>
<div class="nav">
    <ul>
        <li><g:link uri="/" class="home">Home</g:link></li>
    </ul>
</div>

<div class="content">
    <h1>Cities</h1>

    <div id="map"></div>
</div>
<asset:javascript src="mapView.js"/>
<g:javascript>
    $( document ).ready( function ()
    {
        MapView.initialize();
    } );
</g:javascript>
</body>
</html>