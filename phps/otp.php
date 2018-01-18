/*
 $api_url = 'https://control.msg91.com/api/sendotp.php?authkey='.authkey.'&mobile=91'.$phone.'&message=Hello%2C%20'.$otp.'%20is%20your%20OTP.%20use%20this%20for%20confirmation&sender='.SENDERID.'&otp='.$otp;
*/

<?php 
    require "conn.php";
	$name = $_POST["name"];	
	$phone = $_POST["contact"];
  	$otp = rand(100000,999999);
   	define('authkey','163617A0JvCQiCX595a0e24');
   	define('SENDERID','LBuddy');
   	
 $api_url = 'https://control.msg91.com/api/sendotp.php?authkey='.authkey.'&mobile=91'.$phone.'&message=Hello%2C%20'.$name.'%20'.$otp.'%20is%20your%20OTP.%20use%20this%20for%20confirmation&sender='.SENDERID.'&otp='.$otp;

 $response = file_get_contents($api_url);
   echo $response;
   $conn->close();
?>