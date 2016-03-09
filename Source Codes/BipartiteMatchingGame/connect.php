<?php // connect.php basically contains these commands
//This connection will not work because definitions for the database have been omitted for security purposes.

global $db;
$db = new mysqli(db_host, db_uid, db_pwd, db_name);
if ($db->connect_errno) 
  exit("Failed to connect to MySQL, exiting this script");
?>