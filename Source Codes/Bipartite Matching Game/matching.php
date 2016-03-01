<?php
header('Content-Type: text/xml');
echo '<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>';

echo '<response>';
	$n_value = $_GET[n_value];
	$m_value = $_GET[m_value];


	if(!($n_value >= 1 && $m_value <= 10)) {
		//echo '(1 ≤ N, M ≤ 10) not satisfied.';
		//echo "n:" . $n_value;
		//echo "m:" . $m_value;
		echo "Invalid N/M values";
	}


	$graph = array();
	for ($i=1; $i <= $n_value; $i++) {
		for ($j=1; $j <= $m_value; $j++) {
			$isLine = rand(0,3); //0,1,2,3 - if value is 2, do line (25% chance)
			if($isLine == 2) {
				$weight = rand(1,100);
				$edge = array($i, $j, $weight);
				array_push($graph, $edge);
			}
		}
	}

	$int_n = (int)$n_value +1;
	$int_m = (int)$m_value +1;

	echo json_encode(array('N' => $int_n, 'M' => $int_m, 'E' => $graph));


echo '</response>';
?>