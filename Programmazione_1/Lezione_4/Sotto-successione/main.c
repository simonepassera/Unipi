#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	int v[10],i;

	for(i=0;i<10;i++)
	{
		scanf("%d", v+i);
	}

	for(i=0;i<10;i++)
        {
		if(v[i]>=0)
		{
			if(v[i]%2 == 0)
			{
				printf("%d\n", v[i]);
			}
		}
		else
		{
			if(i!=9 && v[i+1]>=0)
			{
				printf("%d\n", v[i]);
			}
		}
        }

	return EXIT_SUCCESS;
}
