<?php
require 'function.php';
if(isset($_GET['username'])){
$username = $_GET['username'];
$connection = getConnection();
$sql = "SELECT receiptID,receiverID,sendDate FROM receipt WHERE senderID='$username'";
if(!$connection->query($sql)){
  echo "error";
}else{
  if(mysqli_num_rows($connection->query($sql))==0){
    echo "NotValid";
  }else{
    $result = $connection->query($sql);
    $newArray=array();
    for($i=1;$i<($row = $result->fetch_assoc());$i++){
      $objArray =['receiptID'=>$row["receiptID"],'receiverID'=>$row["receiverID"],'Date'=>$row["sendDate"]];
      array_push($newArray,$objArray);
}
echo json_encode($newArray);
$connection_aborted;
}
}
}
 ?>
