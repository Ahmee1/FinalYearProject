<?php
    require_once 'connect.php';
    $db = new Db();
    $userid = $_POST["userid"];
    $lat = $_POST["lat"];
    $lng = $_POST["lng"];
    $sql = "INSERT INTO locationms (userid, lat, lng) VALUES($userid, $lat, $lng) ON DUPLICATE KEY UPDATE lat=$lat, lng=$lng";
    $result = $db -> query($sql);
    if ($result == TRUE) {
        echo 'Y';
    } else {
        echo 'N';
    }
?>

