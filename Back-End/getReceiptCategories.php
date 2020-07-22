<?php
require 'function.php';
if(isset($_GET['rid'])){
	$rid = $_GET['rid'];
	$conn = getConnection();
	$sql = "SELECT categories FROM receipt where receiptID='$rid'";
	if(!$conn->query($sql)){
		echo "error";
	}else{
      $result = $conn->query($sql);
      while($row = $result->fetch_assoc()){
        $categories = $row["categories"];
  		}
      echo $categories;
    }
	}else{
	echo "empty";
}
?>
