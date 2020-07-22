<?php
require 'function.php';
if(isset($_GET['receiptID'])&&isset($_GET['categories'])){
$receiptid = $_GET['receiptID'];
$categories = $_GET['categories'];

$connection = getConnection();
$sql = "SELECT receiptID FROM receipt WHERE receiptID = '$receiptid'";
if(!$connection->query($sql)){
  echo mysqli_error($connection);
} else {
  if(mysqli_num_rows($connection->query($sql)) == 1){
	$sql = "UPDATE receipt SET categories='$categories' WHERE receiptID='$receiptid'";
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
