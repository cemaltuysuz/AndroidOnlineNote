<?php

$response = array();
if(isset($_POST['NoteId'])){
    $noteID = $_POST['NoteId'];
    //Getting database info for connection
    require_once __DIR__ . '/db_config.php';

    //Conneciton
    $connection = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);

    if(!$connection){

        die("Connection error  : " . mysqli_connect_eror());
    }

    $sqlquery = "DELETE FROM notes WHERE NoteId = '{$noteID}'";

    if(mysqli_query($connection,$sqlquery)){

        $response["success"] = 1;
        $response["message"] = "Succesfully";

            echo json_encode($response);
    }else{

        $response["success"] = 0;
        $response["message"] = "No product found";

            echo json_encode($response);
    }

    //Connection close
    mysqli_close($connection);

}else{

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
