<?php
    session_start();
    require_once 'connect.php';
    $db = new Db();
    $action = $_GET["action"];
    $userid = $_SESSION['ID'];
    
    $query = "SELECT * FROM locationms WHERE userid = $userid" ;
    $result = $db -> select($query);
    
    $myJSON = json_encode($result[0]);
    echo $myJSON;          
           
    
?>