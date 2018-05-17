<?php
require_once 'connect.php';
$db = new Db();
$id = $_GET["id"];
$table = $_GET["table"];
$query = "SELECT ST_AsText(route) as route FROM $table WHERE id= $id" ;
$result = $db -> select($query);

    $str = $result[0]['route'];
    $str = substr( $str, 11, (strlen( $str) - 1) - 11); 
    $objective = array();
    foreach(str_getcsv($str) as $k => $v) {
        list($x, $y) = explode( ' ', $v);
        $objective[$k]['lat'] = $x;
        $objective[$k]['lng'] = $y;
    }
    echo json_encode( $objective );

    
?>