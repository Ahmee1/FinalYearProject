<?php
    session_start();
    require_once 'connect.php';
    $db = new Db();
    $login = $_POST["login"];
    $loginType = $_POST["type"];    
    $username = $db->quote($_POST["username"]);
    $password = $db->quote($_POST["password"]);
    if ($loginType == "family") {
        $sql = "SELECT * FROM users WHERE familyaccname=$username and familyaccpassword=$password";    
    } else {
        $sql = "SELECT * FROM users WHERE username=$username and password=$password";
    }
    $result = $db -> select($sql);
    $type = $result[0]['type'];
    if($type == 0) {
        if($login == 'web') {
            echo '<script> alert("Wrong username or password, please try again") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';
        } else {
            echo '';
        }
        exit();
    }
    if($login == 'web') {
        if(empty($result)) {
            echo '<script> alert("Wrong username or password, please try again") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';
        } else {
            if ($loginType == "family") {           
                $_SESSION['login'] = "TRUE";
                $_SESSION['ID'] = $result[0]['id'];
                $_SESSION['username'] = $result[0]['familyaccname'];
                $_SESSION['type'] = $result[0]['type'];
            } else {
                $_SESSION['login'] = "TRUE";
                $_SESSION['ID'] = $result[0]['id'];
                $_SESSION['username'] = $result[0]['username'];
                $_SESSION['type'] = $result[0]['type'];               
            }
            echo '<script> alert("Welcome back : '.$_SESSION['username'].'") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';
        }
    } else {
        $myJSON = json_encode($result[0]);
        echo $myJSON;
    } 
?>
