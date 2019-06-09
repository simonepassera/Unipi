#include <stdlib.h>
#include <stdio.h>

void fibonacci(int n);
void fibonacci_r(int p, int u, int n);

int main(int argc, char *argv[])
{
	int n;

	do
	{
		scanf("%d", &n);
	}while(n<0);

	fibonacci(n);

	return EXIT_SUCCESS;
}

void fibonacci_r(int p, int u, int n)
{
	int e=p+u;

	if(e<=n)
	{
		printf("%d\n", e);

		fibonacci_r(u, e, n);
	}
}

void fibonacci(int n)
{
	if(n==0)
	{
		printf("0");
	}
	else
	{
			printf("0\n1\n1\n");
			fibonacci_r(1,1,n);
	}
}
