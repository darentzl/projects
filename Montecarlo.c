/**
 * Daren Tan
 * This program makes use of the the simulation of throwing darts to approximate the value of pi
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int throwDarts(int);

int main(void) {
	int darts_total,   // number of darts to be thrown
		darts_inside;  // number of darts inside quadrant
	float approxPi;

	printf("How many darts? ");
	scanf("%d", &darts_total);

	darts_inside  = throwDarts(darts_total); // darts that land inside the unit circle's quadrant
	approxPi = ((float)darts_inside / (float)darts_total) * 4; //formula for deriving approximation of Pi

	printf("Darts landed inside unit circle's quadrant = %d\n", darts_inside);
	printf("Approximated pi = %.4f\n", approxPi);

	return 0;
}

//this function takes in one value of total darts input and returns the number of darts that land inside the unit circle's quadrant
int throwDarts(int totalDarts) {

	int i, dartsHit = 0;
	float x, y;

	srand(time(NULL)); //random seed

	for (i=0; i<totalDarts; i++) { //this loop generates a random number x and y in the range from 0.0 to 1.0
		x = ((float)rand() ) / RAND_MAX;
		y = ((float)rand() ) / RAND_MAX;

		if (x*x + y*y <= 1.0) { //if darts landed, dartsHit +1. to compute total darts that have landed inside the unit circle's quadrant
			dartsHit += 1;
		}
	}
	return dartsHit;
}
