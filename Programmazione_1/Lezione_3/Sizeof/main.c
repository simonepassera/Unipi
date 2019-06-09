#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	int x;

	scanf("%d", &x);

	printf("%lu", x*sizeof(x));

	return EXIT_SUCCESS;
}
