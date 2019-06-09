#include <stdlib.h>
#include <stdio.h>

double approx_pi(int n);

int main(int argc, char *argv[])
{
	int n;

	do
	{
		scanf("%d", &n);
	}while(n<0);

	printf("%.6f", approx_pi(n));

	return EXIT_SUCCESS;
}

double approx_pi(int n)
{
	int i,d=1;
	double p=0;

	for(i=0;i<n;i++,d+=2)
	{
		if(i%2 == 0)
		{
			p+=(double)4/d;
		}
		else
		{
			p-=(double)4/d;
		}
	}

	return p;
}
