<?php
require 'function.php';
if(isset($_GET['rid'])){
	$rid = $_GET['rid'];
	$rnur ="read";
	$conn = getConnection();
	$sql = "SELECT * FROM receipt where receiptID='$rid'";
	if(!$conn->query($sql)){
		echo "error";
	}else{
		if(mysqli_num_rows($conn->query($sql))==0){
			echo "NotValid";
		}else{
				$sql = "UPDATE receipt SET rnunr='$rnur' WHERE receiptID='$rid'";
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
