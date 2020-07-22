<?php
require 'function.php';
if(isset($_GET['username']) && isset($_GET['password'])){
$username = $_GET['username'];
$password = $_GET['password'];
$connection = getConnection();
$sql="SELECT userName FROM user WHERE userName= '$username' && userPass='$password'";
if(!$connection->query($sql)){
	echo "error";
}else{
	if(mysqli_num_rows($connection->query($sql))==0){
		echo "NotValid";
	}
	else{
		echo "Valid";
	}
	}
	}else{
	echo "empty";
}
$connection_aborted;
?>
