<?php
require 'function.php';
if(isset($_GET['username'])&&isset($_GET['firstname'])&&isset($_GET['lastname'])&&isset($_GET['password'])&&isset($_GET['email'])&&isset($_GET['contact'])){
	$username = $_GET['username'];
	$firstname = $_GET['firstname'];
	$lastname = $_GET['lastname'];
	$password = $_GET['password'];
	$email = $_GET['email'];
	$contact = $_GET['contact'];
	$conn = getConnection();
	$sql = "SELECT userName FROM user where userName='$username'";
	if(!$conn->query($sql)){
		echo "error";
	}else{
		if(mysqli_num_rows($conn->query($sql))==0){
			echo "NotValid";
		}else{
				$sql = "UPDATE user SET firstName='$firstname',lastName='$lastname',userPass='$password',userEmail='$email',userContact='$contact' WHERE userName='$username'";
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
