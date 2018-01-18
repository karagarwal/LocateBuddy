<?php
require "conn.php";
	
	$uname = $_POST["username"];
	    

	$mysql_qry = "DELETE from locate where id='$uname'";
	 if($conn->query($mysql_qry) === TRUE){
	       echo "logout success!!";
	 }
	else{
		echo "Something went wrong. Please try again later!!";	
	}
	
  $conn->close();
?>
  	