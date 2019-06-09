#include <stdlib.h>
#include <stdio.h>

void multipli(int n, int m);

int main(int argc, char *argv[])
{
	int n,m;

	scanf("%d", &n);
	scanf("%d", &m);

	multipli(n,m);

	return EXIT_SUCCESS;
}

void multipli(int n, int m)
{
	int i;

	for(i=1;i<=n;i++)
	{
		if(i%m == 0)
		{
			printf("%d\n", i);
		}
	}
}
