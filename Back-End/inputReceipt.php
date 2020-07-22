<?php
require 'function.php';
if(isset($_GET['receiptID']) && isset($_GET['senderID'])&& isset($_GET['receiverID'])&& isset($_GET['sendDate'])&& isset($_GET['sendText'])&& isset($_GET['rnur'])&& isset($_GET['categories'])) {
$receiptid = $_GET['receiptID'];
$senderid = $_GET['senderID'];
$receiverid = $_GET['receiverID'];
$senddate = $_GET['sendDate'];
$text = $_GET['sendText'];
$rnur = $_GET['rnur'];
$categories = $_GET['categories'];

$connection = getConnection();
$sql = "SELECT receiptID FROM receipt WHERE senderID = '$senderID'";
if(!$connection->query($sql)){
  echo mysqli_error($connection);
} else {
  if(mysqli_num_rows($connection->query($sql)) == 0){
	$sql = "INSERT INTO receipt(receiptID,senderID,receiverID,sendDate,sendText,rnunr,categories) VALUES ('$receiptid','$senderid','$receiverid','$senddate','$text','$rnur','$categories')";
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
