<?php
$response = array();

//Getting database info for connection
    require_once __DIR__ . '/db_config.php';

    //Conneciton
    $connection = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);

    if(!$connection){

        die("Connection error  : " . mysqli_connect_eror());
    }

    $sqlquery = "SELECT * FROM notes";
    $result = mysqli_query($connection,$sqlquery);

    if(mysqli_num_rows($result)>0){
        $response["notlar"] = array();

        while ($row = mysqli_fetch_assoc($result)){
           
            // For Image
            $noteID  = $row["NoteId"];
            $imgStat = $row["imgStat"];
           
            $Notlar["NoteId"]            = $row["NoteId"];
            $Notlar["NoteTitle"]         = $row["NoteTitle"];
            $Notlar["NoteContent"]       = $row["NoteContent"];
            $Notlar["NoteDate"]          = $row["NoteDate"];
            $Notlar["NoteFontType"]      = $row["NoteFontType"];
            $Notlar["NoteTextColor"]     = $row["NoteTextColor"];
            $Notlar["NoteBackColor"]     = $row["NoteBackColor"];
            $Notlar["imgStat"]           = $row["imgStat"];
            
            if($imgStat=="1"){
                $imageQuery = "SELECT * FROM images WHERE images.NoteId = '{$noteID}'";
                $imageResult = mysqli_query($connection,$imageQuery);

                if(mysqli_num_rows($imageResult)>0){
                    
                    while ($row = mysqli_fetch_assoc($imageResult)){

                        $Notlar["ImageUrl"]          = $row["ImageLocation"];
                    }
                }
            }

            array_push($response["notlar"],$Notlar);
        }

        $response["success"] = 1;
        echo json_encode($response);
    }
    else{

    $response["success"] = 0;
    $response["message"] = "No data found.";
    echo json_encode($response);}
    

    //Connection close
    mysqli_close($connection);



