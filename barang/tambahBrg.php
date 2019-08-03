<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */

	if($_SERVER['REQUEST_METHOD']=='POST'){

		//Mendapatkan Nilai Variable
		$name = $_POST['nama'];		
		$stok = $_POST['stok'];
		$image = $_POST['image'];

		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbl_barang (nama, stok, image) VALUES ('$name','$stok','$image')";

		//Import File Koneksi database
		require_once('koneksi.php');

		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
       $response["value"] = 1;
       $response["message"] = "Berhasil Menambahkan Barang";
       echo json_encode($response);
		}else{
       $response["value"] = 0;
       $response["message"] = "Oops! Gagal Menambahkan Barang";
       echo json_encode($response);
		}

		mysqli_close($con);
	}
?>
