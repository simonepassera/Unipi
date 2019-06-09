#include <stdlib.h>
#include <stdio.h>

void tswap(int *x, int *y, int *z);

int main(int argc, char **argv)
{

	int x,y,z;

	scanf("%d", &x);
	scanf("%d", &y);
	scanf("%d", &z);

	tswap(&x,&y,&z);

	printf("%d\n%d\n%d", x,y,z);

    return EXIT_SUCCESS;
}

void tswap(int *x, int *y, int *z)
{
	int t=*x;

	*x=*z;
	*z=*y;
	*y=t;
}
