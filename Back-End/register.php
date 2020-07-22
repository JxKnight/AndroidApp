<?php
require 'function.php';
	if(isset($_GET['username']) && isset($_GET['firstname'])&& isset($_GET['lastname'])&& isset($_GET['password'])&& isset($_GET['email'])&& isset($_GET['contact'])){
		$username = $_GET['username'];
		$firstname = $_GET['firstname'];
		$lastname = $_GET['lastname'];
		$password = $_GET['password'];
		$email = $_GET['email'];
		$contact = $_GET['contact'];
		$connection = getConnection();
		$sql = "SELECT userName FROM user WHERE userName = '$username'";
		if(!$connection->query($sql)){
			echo mysqli_error($connection);
		} else {
			if(mysqli_num_rows($connection->query($sql)) == 0){
				$sql = "INSERT INTO user(userName,firstName,lastName,userPass,userEmail,userContact) VALUES ('$username','$firstname','$lastname','$password','$email','$contact')";
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
