<?php
    session_start();
    require_once 'connect.php';
    $db = new Db();
    $action = $_GET["action"];
    $userid = $_SESSION['ID'];

    if($action == 'all') {
        $query = "SELECT id, ST_AsText(route) as route, date, name FROM records WHERE userid=$userid";
        $result = $db -> select($query);
        $map = array();
        for ($l = 0; $l < count($result); $l++) {
            $str = $result[$l]['route'];
            $str = substr( $str, 11, (strlen( $str) - 1) - 11); 
            $objective = array();
            foreach(str_getcsv($str) as $k => $v) {
                list($x, $y) = explode( ' ', $v);
                $objective[$k]['lat'] = $x;
                $objective[$k]['lng'] = $y;
            }
            $map[$l]['id'] = $result[$l]['id'];
            $map[$l]['route'] = $objective;
            $map[$l]['date'] = $result[$l]['date'];
            $map[$l]['name'] = $result[$l]['name'];
        }
        
        $myJSON = json_encode($map);
        echo $myJSON;          
           
    } else {

    }
?>