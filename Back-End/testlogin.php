<?php
require 'function.php';
if(isset($_GET['username']) && isset($_GET['password']) && isset($_GET['SorB'])){
$username = $_GET['username'];
$password = $_GET['password'];
$SorB = $_GET['SorB'];
$connection = getConnection();
$sql="SELECT userName,userPass FROM user where userName= '$username' && userPass='$password'";
if(!$connection->query($sql)){
	echo "error";
}else{
	if(mysqli_num_rows($connection->query($sql))==0){
		echo "NotValid";
	}
	else{
		$result = $connection->query($sql);
		while($row = $result->fetch_assoc()){
			$mO = new stdClass();
			$mO->name = $row["userName"];
			$mO->password = $row["userPass"];
		}
		$myJSON = json_encode($mO);
		echo $myJSON;
	}		
	}
	}else{
	echo "empty";
}
$connection_aborted;
?>