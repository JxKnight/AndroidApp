<?php
require 'function.php';
if(isset($_GET['receiptID'])){
$username = $_GET['username'];
$rnur = $_GET['r&ur'];
$connection = getConnection();
$sql="SELECT receiptID,senderID,sendDate,sendText,photo FROM receipt where receiverID= '$username'&& r&unr='$rnur'";
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
			$mO->receiptID = $row["receiptID"];
			$mO->senderID = $row["senderID"];
			$mO->sendDate = $row["sendDate"];
      $mO->sendText = $row["sendText"];
      $mO->photo = $row["photo"];
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
