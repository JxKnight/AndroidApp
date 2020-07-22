<?php
require 'function.php';
if(isset($_GET['username'])){
	$username=$_GET['username'];
	$conn = getConnection();
	$sql = "SELECT * FROM user where userName='$username'";
	if(!$conn->query($sql)){
		echo "error";
	}else{
		if(mysqli_num_rows($conn->query($sql))==0){
			echo "NotValid";
		}else{
				$sql = "DELETE FROM user where userName='$username'";
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
