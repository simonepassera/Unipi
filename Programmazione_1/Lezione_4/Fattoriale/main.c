#include <stdlib.h>
#include <stdio.h>

int fattoriale(int x);

int main(int argc, char *argv[])
{
	int x;

	scanf("%d", &x);

	printf("%d", fattoriale(x));

	return EXIT_SUCCESS;
}

int fattoriale(int x)
{
	if(x!=1)
	{
		return x*fattoriale(x-1);
	}
	else
	{
		return 1;
	}
}
