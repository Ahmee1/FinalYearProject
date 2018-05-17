<?php
    require_once 'connect.php';
    $db = new Db();
    $register = $_POST["register"];
    $username = $db->quote($_POST["username"]);
    $password = $db->quote($_POST["password"]);
    $firstname = $db->quote($_POST["firstname"]);
    $lastname = $db->quote($_POST["lastname"]);
    $phone = $_POST["phone"];
    $email = $db->quote($_POST["email"]);
    $password2 = $db->quote($_POST["password2"]);
    if ($password !== $password2) {
        if ($register == 'web') {
            echo '<script> alert("Registation Unsuccessful - Password Do not match") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';
            exit();
        }
    }
    $sql = "SELECT * FROM users WHERE username=$username";
    $result = $db -> select($sql); 
    if(!empty($result)){ 
        if ($register == 'web') {
            echo '<script> alert("Registation Unsuccessful - Username exist") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';           
        } else {
            echo 'Registation Unsuccessfully - Username exist';           
        }
        exit();
    }
    $sql = "SELECT * FROM users WHERE email=$email";
    $result = $db -> select($sql); 
    if(!empty($result)){ 
        if ($register == 'web') {
            echo '<script> alert("Registation Unsuccessful - Email exist") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';           
        } else {
            echo 'Registation Unsuccessfully - Email exist';           
        }
        exit();
    }
    $sql = "SELECT * FROM users WHERE phone=$phone";
    $result = $db -> select($sql); 
    if(!empty($result)){ 
        if ($register == 'web') {
            echo '<script> alert("Registation Unsuccessful - Phone Number exist") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';           
        } else {
            echo 'Registation Unsuccessfully - Phone Number exist';           
        }
        exit();
    }
    $sql = "Insert into users (username, password, firstname, lastname, phone, email, active, type) values ($username, $password, $firstname, $lastname, $phone, $email, 1, 1)";
    $result = $db -> query($sql);
    if($result == TRUE){ 
        if ($register == 'web') {
            echo '<script> alert("Registation Successful.") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';     
        } elseif ($register == 'android') {
            echo 'Registation Successfully';
        } 
    } else {
        if ($register == 'web') {
            echo '<script> alert("Registation Unsuccessful, please try again") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';
        } else {
            echo 'Registation Unsuccessfully';
        }
    }
?>

