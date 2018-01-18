<?php
require "conn.php";
	$name = $_POST["name"];	
	$uname = $_POST["uname"];
	$pass = $_POST["pass"];
	$conct = $_POST["conct"];

  $query = "Select * from registry where username like '$uname';";
  $result = mysqli_query($conn,$query);
  
  $query2 = "Select * from registry where contact like '$conct';";
  $result2 = mysqli_query($conn,$query2);
   
    if(mysqli_num_rows($result)>0){
  	echo "Username '".$uname."' Already Exist";
      }
   else {
     	 	 if(mysqli_num_rows($result2)>0){
  			echo ("The Phone Number '".$conct."' is already registered with us");
        	}		
   		  
     		else{
     			$options = ['cost' => 12,];
			$pass = password_hash($pass, PASSWORD_BCRYPT, $options);
		  	$mysql_qry = "Insert into registry (Name,username,password,contact) values ('$name','$uname','$pass','$conct')";
			 if($conn->query($mysql_qry) === TRUE){
		  	       echo "Successfully Register!!";
			  }
			else{
				echo "Something went wrong. Please try again later!!";	
	     		  }
                    }
         } 
  $conn->close();
?>
  	