#include <stdlib.h>
#include <stdio.h>

int count_larger(int x, int v[], int d);

int main(int argc, char *argv[])
{
	int x,v[5],i;

	scanf("%d", &x);

	for(i=0;i<5;i++)
	{
		scanf("%d",v+i);
	}

	printf("%d", count_larger(x,v,5));

	return EXIT_SUCCESS;
}

int count_larger(int x, int v[], int d)
{
	int i,e=0;

	for(i=0;i<d;i++)
	{
		if(v[i]>x)
		{
			e++;
		}
	}

	return e;
}
