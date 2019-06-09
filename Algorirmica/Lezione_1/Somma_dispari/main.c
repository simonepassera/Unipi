#include <stdlib.h>
#include <stdio.h>

int f(int n);

int main(int argc, char **argv)
{

	int x;

	scanf("%d", &x);

	printf("%d", f(x));

    return EXIT_SUCCESS;
}

int f(int n)
{
	if(n<=0)
	{
		return 0;
	}
	else
	{
		return (n*2-1) + f(n-1);
	}
}
