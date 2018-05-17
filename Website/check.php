<?php
    session_start();
    if (isset($_SESSION['login'])) {
        if ($_SESSION['login'] == "TRUE") {
            $username = $_SESSION['username'];
            $type = $_SESSION['type'];
        } else {
            if($_GET['path'] == service) {
                echo '<script> alert("Please login to use the service") </script>';
                echo '<script language="JavaScript"> window.location.href ="./index" </script>';
            }
        }
    }   else {
        if($_GET['path'] == service) {
            echo '<script> alert("Please login to use the service") </script>';
            echo '<script language="JavaScript"> window.location.href ="./index" </script>';
        }
    }
?>
