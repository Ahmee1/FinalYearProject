<?php
    require_once 'connect.php';
    $db = new Db();
    $userid = $_POST["userid"];
    $familyaccname = $db->quote($_POST["familyaccname"]);
    $familyaccpassword = $db->quote($_POST["familyaccpassword"]);
    $sql = "UPDATE users set familyaccname=$familyaccname, familyaccpassword = $familyaccpassword WHERE id = $userid";
    $result = $db -> query($sql);
    if ($result == TRUE) {
        echo 'Y';
    } else {
        echo 'N';
    }
?>

