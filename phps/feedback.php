<?php
require "conn.php";
	$user_subj = $_POST["subj"];		
	$user_fbck = $_POST["feedback"];
	
		
		$to1='support_lbuddy@gmail.com';
		
		$subject='New Feedback for LBuddy : '.$user_subj.' ';
		$mgs=="Name: \n"."Wrote the following: "."\n\n".$user_fbck." ";
		$header="From: agarwal.karag@gmail.com";

			if(mail($to,$subject,$mgs,$header)){
				echo "success";
			}
			 else{
			 echo "Something went wrong!";
			 }
			 
$conn->close();

?>



