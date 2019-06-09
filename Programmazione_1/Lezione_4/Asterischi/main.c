#include <stdlib.h>
#include <stdio.h>

void asterischi(int n);

int main(int argc, char *argv[])
{
	int n;

	scanf("%d", &n);

	do
	{
		asterischi(n);
		n-=2;
	}while(n>0);

	return EXIT_SUCCESS;
}

void asterischi(int n)
{
	int i;

	for(i=0;i<n;i++)
	{
		printf("*");
	}

	printf("\n");
}
