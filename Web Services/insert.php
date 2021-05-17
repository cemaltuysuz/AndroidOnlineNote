<?php

$response = array ();
if(isset($_POST['noteTitle'])&&isset($_POST['noteContent'])&&isset($_POST['noteDate'])&&isset($_POST['noteFontType'])&&
   isset($_POST['noteTextColor'])&&isset($_POST['noteBackColor'])&&isset($_POST['noteImgStat'])){
    $NoteTitle             = $_POST['noteTitle'];
    $NoteContent           = $_POST['noteContent'];
    $NoteDate              = $_POST['noteDate'];
    $NoteFontType          = $_POST['noteFontType'];
    $NoteTextColor         = $_POST['noteTextColor'];
    $NoteBackColor         = $_POST['noteBackColor'];
    $NoteImgStat           = $_POST['noteImgStat'];
    

    //Getting database info for connection
    require_once __DIR__ . '/db_config.php';

    //Conneciton
    $connection = mysqli_connect(DB_SERVER,DB_USER,DB_PASSWORD,DB_DATABASE);

    if(!$connection){

        die("Connection error  : " . mysqli_connect_eror());
    }

   $sqlquery = "INSERT INTO notes (NoteTitle,NoteContent,NoteDate,NoteFontType,NoteTextColor,NoteBackColor,imgStat) VALUES  ('{$NoteTitle}','{$NoteContent}','{$NoteDate}','{$NoteFontType}','{$NoteTextColor}','{$NoteBackColor}','{$NoteImgStat}')";
    

    if(mysqli_query($connection,$sqlquery)){
        
        $sqlquery2 = "select NoteId from notes ORDER BY NoteId DESC LIMIT 1";
        $result2 = mysqli_query($connection,$sqlquery2);

        if(mysqli_num_rows($result2)>0){
            $response["notlar"] = array();

            while ($row = mysqli_fetch_assoc($result2)){

                $Notlar["NoteId"] = $row["NoteId"];
                $noteID = $row["NoteId"];
                //Image Upload Start
                if($NoteImgStat=="1" && isset($_POST['image'])){
                   
                   $encodedImage = $_POST['image'];
                   $imageTitle = generateRandomString(50);
                   $imageLocation = "images/$imageTitle.jpg";
                   
                   $imageUploadQuery = "INSERT INTO Images (NoteId,ImageTitle,ImageLocation) VALUES ('{$noteID}','{$imageTitle}','{$imageLocation}')";
                   
                   if(mysqli_query($connection,$imageUploadQuery)){
                        file_put_contents($imageLocation,base64_decode($encodedImage));
                   }
                   
                   }
                //Image upload finish
                array_push($response["notlar"],$Notlar);
            }

            $response["success"] = 1;
            echo json_encode($response);
        }

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
                   function generateRandomString($length = 10) {
                       $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
                       $charactersLength = strlen($characters);
                       $randomString = '';
                       for ($i = 0; $i < $length; $i++) {
                           $randomString .= $characters[rand(0, $charactersLength - 1)];
                       }
                       return $randomString;
                   }
?>
                
            
