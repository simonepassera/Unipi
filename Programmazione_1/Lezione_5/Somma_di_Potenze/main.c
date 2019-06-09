#include <stdlib.h>
#include <stdio.h>
#include <math.h>

double sum_pow(int n, double x);

int main(int argc, char *argv[])
{
	int n;
	double x;

	scanf("%d", &n);

	do
	{
		scanf("%lf", &x);
	}while(x<=0);

	printf("%.2f", sum_pow(n,x));

	return EXIT_SUCCESS;
}

double sum_pow(int n, double x)
{
	int i;
	double s=0;

	for(i=0;i<=n;i++)
	{
		s+=pow(x,(double)i);
	}

	return s;
}
