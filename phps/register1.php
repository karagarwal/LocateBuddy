<?php
require "conn.php";
	$name = $_POST["name"];	
	$uname = $_POST["uname"];
	$pass = $_POST["pass"];
	$conct = $_POST["conct"];
	$otp = rand(100000,999999);
   	define('authkey','163617A0JvCQiCX595a0e24');
   	define('SENDERID','LBuddy');
   	 $api_url = 'https://control.msg91.com/api/sendotp.php?authkey='.authkey.'&mobile=91'.$conct.'&message=Hello%2C%20'.$name.'%20'.$otp.'%20is%20your%20OTP.%20use%20this%20for%20confirmation&sender='.SENDERID.'&otp='.$otp;


  $query = "Select * from registry where username like '$uname' and verified = '1';";
  $result = mysqli_query($conn,$query);
  
  $query2 = "Select * from registry where contact like '$conct' and verified = '1';";
  $result2 = mysqli_query($conn,$query2);
   
    if(mysqli_num_rows($result)>0){
  	echo "Username '".$uname."' Already Exist";
      }
   else {
     	 	 if(mysqli_num_rows($result2)>0){
  			echo ("The Phone Number '".$conct."' is already registered with us");
        	}		
   		  
     		else{ 
			 $response = file_get_contents($api_url); 
			 $options = ['cost' => 12,];
			 $pass = password_hash($pass, PASSWORD_BCRYPT, $options);
		  	 $mysql_qry = "Insert into registry (Name,username,password,contact,otp) values ('$name','$uname','$pass','$conct','$otp')";
			   if($conn->query($mysql_qry) === TRUE){
		  	        echo "Successfully Register!!";
   			   }
			}
         } 
  $conn->close();
?>
  	