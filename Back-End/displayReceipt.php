<?php
require 'function.php';
if(isset($_GET['rID'])){
$receiptid=$_GET['rID'];
$connection = getConnection();
$sql="SELECT * FROM receipt where receiptID='$receiptid'";
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
			$mO->receiverID = $row["receiverID"];
			$mO->sendDate = $row["sendDate"];
      $mO->sendText = $row["sendText"];
			$mO->photo =base64_encode($row["photo"]);
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
