#include <stdlib.h>
#include <stdio.h>

int main(int argc, char **argv)
{

	int n,s=0;

	do
	{
		scanf("%d",&n);
		s+=n;
	}while(n!=0);

	printf("%d", s);

    return EXIT_SUCCESS;
}
