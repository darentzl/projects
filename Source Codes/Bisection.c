/**
 * Daren Tan
 * This program illustrates how Bisection Method works by computing the root of a continuous function
 */

#include <stdio.h>
#include <math.h>

// Given function prototype must not be changed
double polynomial(double, int, int, int, int);

int main(void) {
	int    c3, c2, c1, c0; // coefficients of polynomial
	double a, b,           // endpoints
		   pA, pB;         // function values at endpoints
	double m, pM;          // midpoint and function value at midpoint
	double threshold;

	printf("Enter coefficients (c3,c2,c1,c0) of polynomial: ");
	scanf("%d %d %d %d", &c3, &c2, &c1, &c0);

	printf("Enter endpoints a and b: ");
	scanf("%lf %lf", &a, &b);

	//this loop is the bisection method
	do {

		//when midpoint is root, terminates loops
		if (pM == 0) {
			break;
		}

		m = (a+b)/2.0; 
		pA = polynomial(a,c3,c2,c1,c0);
		pB = polynomial(b,c3,c2,c1,c0);
		pM = polynomial(m,c3,c2,c1,c0);

		threshold = fabs(a-b); //difference between two end points which can be negative or postive or both.

		//this loop swaps m into either a or b whose function value is same sign as the function value of m
		if (pM/pB > 0) { 
			b = m;
		} else {
			a = m;
		}

	} while (threshold > 0.0001);

	printf("root = %f\n", m);
	printf("p(root) = %f\n", pM);

	return 0;
}

//this function computes and returns the polynomial value of using the user inputs
double polynomial(double n, int c3, int c2, int c1, int c0) {

	double value;

	value = (c3 * pow(n,3)) + (c2 * pow(n,2)) + (c1 * n) + c0;

	return value;
}
