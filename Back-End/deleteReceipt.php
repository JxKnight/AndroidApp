<?php
require 'function.php';
if(isset($_GET['rid'])){
	$receiptid=$_GET['rid'];
	$conn = getConnection();
	$sql = "SELECT receiptID FROM receipt where receiptID='$receiptid'";
	if(!$conn->query($sql)){
		echo "error";
	}else{
		if(mysqli_num_rows($conn->query($sql))==0){
			echo "NotValid";
		}else{
				$sql = "DELETE FROM receipt where receiptID='$receiptid'";
				if(!$conn->query($sql)){
					echo "error";
				}else{
					echo "Valid";
				}
			}
		}
	}else{
	echo "empty";
}
?>
