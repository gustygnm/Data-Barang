<?php
require_once('koneksi.php');
if($_SERVER['REQUEST_METHOD']=='POST') {
  $response = array();
  //mendapatkan data
  $npm = $_POST['id'];
  $sql = "DELETE FROM tbl_barang WHERE id = '$npm'";
  if(mysqli_query($con,$sql)) {
    $response["value"] = 1;
    $response["message"] = "Berhasil dihapus";
    echo json_encode($response);
  } else {
    $response["value"] = 0;
    $response["message"] = "oops! Gagal dihapus!";
    echo json_encode($response);
  }
  mysqli_close($con);
}