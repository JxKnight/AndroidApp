<?php
require 'function.php';
if(isset($_GET['username']) ){
  $username = $_GET['username'];
$connection = getConnection();
$sql = "SELECT userName FROM user WHERE userName='$username'";
if(!$connection->query($sql)){
  echo "error";
}else{
  if(mysqli_num_rows($connection->query($sql))==0){
    echo "NotValid";
  }else{
    $result = $connection->query($sql);
    $newArray=array();
    for($i=1;$i<($row = $result->fetch_assoc());$i++){
      $objArray =['name'=>$row["userName"],'pass'=>$row["userPass"]];
      array_push($newArray,$objArray);
}
echo json_encode($newArray);
$connection_aborted;
}
}
}
 ?>
