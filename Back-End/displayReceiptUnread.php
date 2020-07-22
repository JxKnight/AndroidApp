<?php
require 'function.php';
if(isset($_GET['username'])&& isset($_GET['rnur']) ){
$receiverID = $_GET['username'];
$rnur = $_GET['rnur'];
$connection = getConnection();
$sql = "SELECT receiptID,senderID,sendDate FROM receipt WHERE receiverID='$receiverID' && rnunr='$rnur'";
if(!$connection->query($sql)){
  echo "error";
}else{
  if(mysqli_num_rows($connection->query($sql))==0){
    echo "NotValid";
  }else{
    $result = $connection->query($sql);
    $newArray=array();
    for($i=1;$i<($row = $result->fetch_assoc());$i++){
      $objArray =['receiptID'=>$row["receiptID"],'senderID'=>$row["senderID"],'Date'=>$row["sendDate"]];
      array_push($newArray,$objArray);
}
echo json_encode($newArray);
$connection_aborted;
}
}
}
 ?>
