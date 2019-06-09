#include <stdlib.h>
#include <stdio.h>

int varifica(int *a, int *b);

int main(int argc, char *argv[])
{
        int a[3],b[3],i;

	for(i=0;i<3;i++)
	{
		scanf("%d", a+i);
	}

	for(i=0;i<3;i++)
        {
                scanf("%d", b+i);
        }

	if(verifica(a,b))
	{
		printf("TRUE");
	}
	else
	{
		printf("FALSE");
	}

        return EXIT_SUCCESS;
}

int verifica(int *a, int *b)
{
	int i=0,j,s=0,c=0;

	while(i<3 && !s)
	{
		for(j=0;j<3;j++)
		{
			if(a[i]<b[j])
			{
				c++;
			}
		}

		if(c==3)
		{
			s=1;
		}
		else
		{
			c=0;
			i++;
		}
	}

	return s;
}
