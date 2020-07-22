<?php
require 'function.php';
if(isset($_GET['username']) ){
$username = $_GET['username'];
$connection = getConnection();
$sql="SELECT userName,firstName,lastName,userPass,userEmail,userContact FROM user where userName= '$username'";
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
			$mO->username = $row["userName"];
			$mO->firstname = $row["firstName"];
			$mO->lastname = $row["lastName"];
			$mO->password = $row["userPass"];
			$mO->email = $row["userEmail"];
			$mO->contact = $row["userContact"];
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
