<?php
require "conn.php";
	$user_name = $_POST["user_name"];		
	$user_pass = $_POST["password"];
	$mysql_qry = "Select password from registry where username like '$user_name';";
	$result = mysqli_query($conn,$mysql_qry);
		
		if(!$row = mysqli_fetch_assoc($result)){
			echo "Username '".$user_name."' doesn't exist";
		}
		else{
			if(password_verify($user_pass, $row['password'])){
			 
			 	$mysql_qry = "Select Name,contact from registry where username like '$user_name';";
				$result = mysqli_query($conn,$mysql_qry);
			  	 $row = mysqli_fetch_assoc($result); 
				  
				  $name  = $row['Name'];
				  $phone = $row['contact'];   
				   
				$mysql_qry = "Insert into locate (id,name,contact) values ('$user_name','$name','$phone')";
			 if($conn->query($mysql_qry) === TRUE){
		  	      echo "Login success !! welcome";
			  }
			 else{
			   echo "Login success !! welcome";	
			 }    

			}
			else 
			   echo 
			     "Invalid Password for user '".$user_name."' ";
		}
$conn->close();

?>



