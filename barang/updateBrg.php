<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */
		require_once('koneksi.php');
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable
		  $response = array();
		$id = $_POST['id'];
		$nama = $_POST['nama'];
		$stok = $_POST['stok'];
		$image = $_POST['image'];
		//import file koneksi database

		//Membuat SQL Query
		$sql = "UPDATE tbl_barang SET nama = '$nama', stok = '$stok', image = '$image' WHERE id = $id;";
		//Meng-update Database
		 if(mysqli_query($con,$sql)) {
   			$response["value"] = 1;
    		$response["message"] = "Berhasil diperbarui";
  			echo json_encode($response);
 		} else {
    		$response["value"] = 0;
    		$response["message"] = "oops! Gagal merubah!";
    		echo json_encode($response);
  		}
  		mysqli_close($con);
	}
?>
