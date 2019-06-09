#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	int v[10],i;

	for(i=0;i,i<10;i++)
	{
		scanf("%d", v+i);
	}

	for(i=9;i>=0;i--)
	{
		if(v[i]%2 == 0)
		{
			printf("%d\n", v[i]/2);
		}
		else
		{
			printf("%d\n", v[i]);
		}
	}

	return EXIT_SUCCESS;
}
