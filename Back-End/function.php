<?php
function getConnection(){
	$servername = "localhost";
	$username = "root";
	$password = "root";
	$tablename = "mobileappassignment";
	$conn = new mysqli($servername,$username,$password,$tablename);
	if($conn->connect_error){
		return "error";
	} else {
		return $conn;
	}
}
?>
