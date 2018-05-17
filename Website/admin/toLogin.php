<?php
    session_start();
    require_once 'connect.php';
    $db = new Db();
    $username = $db->quote($_POST["username"]);
    $password = $db->quote($_POST["password"]);

        $sql = "SELECT * FROM users WHERE username=$username and password=$password";
    
    $result = $db -> select($sql);
    
        if(empty($result)) {
            echo '<script> alert("Wrong username or password, please try again") </script>';
            echo '<script language="JavaScript"> window.location.href ="login.php" </script>';
        } else {
            $type = $result[0]['type'];
    if($type == 1) {
            echo '<script> alert("Wrong username or password, please try again") </script>';
            echo '<script language="JavaScript"> window.location.href ="login.php" </script>';
        
        exit();
    }
                $_SESSION['login'] = "TRUE";
                $_SESSION['ID'] = $result[0]['id'];
                $_SESSION['username'] = $result[0]['username'];
                $_SESSION['type'] = $result[0]['type'];               
            
            echo '<script> alert("Welcome back : '.$_SESSION['username'].'") </script>';
            echo '<script language="JavaScript"> window.location.href ="index.php" </script>';
        }
    
?>
