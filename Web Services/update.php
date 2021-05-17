<?php

$response = array ();
    if(isset($_POST['noteId'])&&isset($_POST['noteTitle'])&&isset($_POST['noteContent'])&&isset($_POST['noteFontType'])
       &&isset($_POST['noteTextColor'])&&isset($_POST['noteBackColor'])&&isset($_POST['noteImgStat'])){
        $NoteId                = $_POST['noteId'];
        $NoteTitle             = $_POST['noteTitle'];
        $NoteContent           = $_POST['noteContent'];
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

   $sqlquery = "UPDATE notes SET NoteTitle = '{$NoteTitle}' , NoteContent = '{$NoteContent}', NoteFontType = '{$NoteFontType}', NoteTextColor = '{$NoteTextColor}', imgStat = '{$NoteImgStat}' WHERE NoteId = '{$NoteId}'";

    if(mysqli_query($connection,$sqlquery)){
        
        // Resim var mı yok mu kontrol ediliyor, eğer update isteğinde bize resimde geliyorsa (stat = 1) , yeni gelen resmi eklemeden evvel sunucu üzerinde ait bu notun sahip olduğu resim veya resimleri siliyoruz.
        
        if($NoteImgStat=="1" && isset($_POST['image'])){
           
           $encodedImage = $_POST['image'];
           $imageTitle = generateRandomString(50);
           $imageLocation = "images/$imageTitle.jpg";
           
           $imageDeleteQuery = "DELETE FROM Images WHERE Images.NoteId = '{$NoteId}'";
           
           if(mysqli_query($connection,$imageDeleteQuery)){
               
                // Resim ekleme işlemi başlangıç
               $imageUploadQuery = "INSERT INTO Images (NoteId,ImageTitle,ImageLocation) VALUES ('{$NoteId}','{$imageTitle}','{$imageLocation}')";
               
               if(mysqli_query($connection,$imageUploadQuery)){
                    file_put_contents($imageLocation,base64_decode($encodedImage));
               }
               
               }
               //Resim ekleme işlemi bitiş
           }
           
        
        // Resim Update İslemi bitiyor

        $response["success"] = 1;
        $response["message"] = "Succesfully";

            echo json_encode($response);
    }
    else{

        $response["success"] = 0;
        $response["message"] = "No product found";
            printf("Error message: %s\n", mysqli_error($connection));
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
