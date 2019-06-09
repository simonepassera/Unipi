#include <stdlib.h>
#include <stdio.h>

int main(int argc, char **argv)
{
	int n;

	do
	{
		scanf("%d",&n);
	}while(n>=10000);

	int a[n], i;

	for(i=(n-1);i>=0;i--)
	{
		scanf("%d", a+i);
	}

	for(i=0;i<n;i++)
    {
        printf("%d\n", a[i]);
    }

    return EXIT_SUCCESS;
}
