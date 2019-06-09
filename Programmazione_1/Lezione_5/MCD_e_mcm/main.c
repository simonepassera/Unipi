#include <stdlib.h>
#include <stdio.h>

int mcm (int n, int m);
int mcd(int n, int m);

int main(int argc, char *argv[])
{
	int n,m;

	scanf("%d", &n);
	scanf("%d", &m);

	printf("%d\n", mcd(n,m));
	printf("%d", mcm(n,m));

	return EXIT_SUCCESS;
}

int mcm (int n, int m)
{
	return (n*m)/mcd(n,m);
}

int mcd(int n, int m)
{
	while(n!=m)
	{
		if(n>m)
		{
			n-=m;
		}
		else
		{
			m-=n;
		}
	}

	return n;
}
