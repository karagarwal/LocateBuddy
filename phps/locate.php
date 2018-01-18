<?php
require "conn.php";
	
	$uname = $_POST["username"];
	$lati = $_POST["latitude"];
	$longi = $_POST["longitude"];
	
   // $mysql_qry = "Update locate SET latitude=".$lati.",longitude=".$longi."where id=".$uname;
    
   $mysql_qry = "Update locate SET latitude=".$lati.",longitude=".$longi."where id='$uname'";

	 if($conn->query($mysql_qry) === TRUE){
	       echo "Locate success!!";
	 }
	else{
		echo "Something went wrong. Please try again later!!";	
	}
	
  $conn->close();
?>
  	