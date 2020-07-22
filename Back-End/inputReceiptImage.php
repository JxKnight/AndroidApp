<?php
require 'function.php';
if(isset($_GET['receiptID'])&& isset($_GET['img'])) {
$receiptid = $_GET['receiptID'];
$img = $_GET['img'];

$connection = getConnection();
$sql = "SELECT * FROM receipt WHERE receiptID = '$receiptid'";
if(!$connection->query($sql)){
  echo mysqli_error($connection);
} else {
  if(mysqli_num_rows($connection->query($sql)) == 0){
	$sql = "INSERT INTO receipt(photo) VALUES ('$img') WHERE receiptID='$receiptid'";
  if(!$connection->query($sql)){
    echo mysqli_error($connection);
  } else{
    echo "Valid";
  }
  } else {
  echo "Invalid";
}
}
} else {
echo "Empty";
}
 ?>
