#include <stdlib.h>
#include <stdio.h>

void swap(int *a, int *b);

int main(int argc, char **argv)
{

	int a,b;

	scanf("%d", &a);
	scanf("%d", &b);

	swap(&a,&b);

	printf("%d\n%d", a,b);

    return EXIT_SUCCESS;
}

void swap(int *a, int *b)
{
	int t=*a;

	*a=*b;
	*b=t;
}
