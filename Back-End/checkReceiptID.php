<?php
require 'function.php';
$x = 0;
$R = "R";
$connection = getConnection();
$sql = "SELECT receiptID FROM receipt";
if(!$connection->query($sql)){
  	echo mysqli_error($connection);
}else{
  $result = $connection->query($sql);
  while($row = $result->fetch_assoc()){
  $x++;
  }
  echo $R.($x+1);
}
 ?>
