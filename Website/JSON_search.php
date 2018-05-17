<?php
    require_once 'connect.php';
    $db = new Db();
    $action = $_GET["action"];
    if($action == 'all') {
        $query = "SELECT id, ST_AsText(route) as route, level FROM map";
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
            $map[$l]['level'] = $result[$l]['level'];
        }
        
        $myJSON = json_encode($map);
        echo $myJSON;          
           
    } else {
        $start = $_GET["start"];;
        $start = str_replace(',', '' , $start);
        $end = $_GET["end"];
        $end = str_replace(',', '' , $end);
        
        $startPoint = 'Point'.$start;
        $endPoint = 'Point'.$end;
        
        $query = "SELECT id, ST_AsText(route) as route, level, ST_NumPoints(route) as num FROM map";
        $result = $db -> select($query);
        $map = array();
        $u = 0;
        for ($l = 0; $l < count($result); $l++) {
            $id = $result[$l]['id'];
            $numOfPoint = $result[$l]['num'];
            $str = $result[$l]['route'];
            $level = $result[$l]['level'];
            $st = 0;
            $et = 0;
                                       
            for ($n = 1; $n <= $numOfPoint; $n++) {
                $query_2 = "SELECT 
                (
                    GLength(
                        LineStringFromWKB(
                            LineString(
                                ST_PointN(route,$n),
                                GeomFromText('$startPoint')
                            )
                        )
                    )
                ) AS distance FROM map WHERE id=$id";
                $result_2 = $db -> select($query_2);
                $startDistance = $result_2[0]['distance'];
                
                if (floatval($startDistance) < 0.0003) {
                    $st = 1;
                }
                $query_3 = "SELECT 
                (
                    GLength(
                        LineStringFromWKB(
                            LineString(
                                ST_PointN(route,$n),
                                GeomFromText('$endPoint')
                            )
                        )
                    )
                ) AS distance FROM map WHERE id=$id";
                $result_3 = $db -> select($query_3);
                $endDistance = $result_3[0]['distance'];
                if (floatval($endDistance) < 0.0003) {
                    $et = 1;
                }                               
            }
            if ($et == 1 && $st == 1) {
                $str = substr( $str, 11, (strlen( $str) - 1) - 11); 
                $objective = array();
                foreach(str_getcsv($str) as $k => $v) {
                    list($x, $y) = explode( ' ', $v);
                    $objective[$k]['lat'] = $x;
                    $objective[$k]['lng'] = $y;
                }
                $map[$u]['id'] = $id;
                $map[$u]['route'] = $objective;
                $map[$u]['level'] = $level;
                $u++;
                $et = FALSE;
                $st = FALSE;
                break;
            }
            $et = 0;
            $st = 0;
        }
        $myJSON = json_encode($map);
        echo $myJSON;
    }
?>