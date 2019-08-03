<?php

 /*

 penulis: Muhammad yusuf
 website: http://www.kodingindonesia.com/

 */

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT * FROM tbl_barang";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Membuat Array Kosong
	$result = array();

	while($row = mysqli_fetch_array($r)){

		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat
		array_push($result,array(
			"id"=>$row['id'],
			"nama"=>$row['nama'],
			"stok"=>$row['stok'],
			"image"=>$row['image']
		));
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
?>
