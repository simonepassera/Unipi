#include <stdlib.h>
#include <stdio.h>

int pot2(int n);

int  main(int argc, char *argv[])
{
	int n;

	do
	{
		scanf("%d", &n);
	}while(n<0);

	printf("%d", pot2(n));

	return EXIT_SUCCESS;
}


int pot2(int n)
{
	if(n<2)
	{
		return 2;
	}
	else
	{
		return 2*pot2(n-1);
	}
}
