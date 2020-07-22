<?php
require 'function.php';
	if(isset($_GET['bid'])){
		$bid = $_GET['bid'];
		$connection = getConnection();
		$sql = "SELECT userName FROM user WHERE userName = '$bid'";
		if(!$connection->query($sql)){
			echo mysqli_error($connection);
		} else {
			if(mysqli_num_rows($connection->query($sql)) == 1){
					echo "Valid";
				}else {
				echo "Invalid";
			}
		}
	} else {
		echo "Empty";
	}
?>
