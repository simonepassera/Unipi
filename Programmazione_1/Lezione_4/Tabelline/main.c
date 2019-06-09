#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	int x,i;

	scanf("%d", &x);

	for(i=1;i<=10;i++)
	{
		printf("%d\n", x*i);
	}

	return EXIT_SUCCESS;
}
