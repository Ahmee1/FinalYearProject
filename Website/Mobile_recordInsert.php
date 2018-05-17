<?php
    require_once 'connect.php';
    $db = new Db();
    $userid = $_POST["userid"];
    $route = $db -> quote($_POST["route"]);
    $name = $db -> quote($_POST["name"]);
    $sql = "INSERT INTO records (userid, route, name) VALUES($userid, ST_GeomFromText($route), $name)";
    $result = $db -> query($sql);
    if ($result == TRUE) {
        echo 'Y';
    } else {
        echo 'N';
    }
?>

