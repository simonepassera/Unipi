#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	int a[10],i,b=1;

	for(i=0;i<10;i++)
	{
		scanf("%d", a+i);
	}

	i=1;

	do
	{
		if(a[i] == a[i+1]-a[i-1])
		{
			b=0;
		}
		else
		{
			i++;
		}
	}while(i<9 && b);

	if(!b)
	{
		printf("%d", i);
	}
	else
	{
		printf("-1");
	}

	return EXIT_SUCCESS;
}

