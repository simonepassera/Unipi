#include <stdlib.h>
#include <stdio.h>

float media();

int main(int argc, char *argv[])
{
	printf("%.2f", media());

	return EXIT_SUCCESS;
}

float media()
{
	int v[10],i,e=0,b=0;
	float m=0;

	for(i=0;i<10;i++)
	{
		if(i==9)
		{
			do
			{
				scanf("%d", v+i);
			}while(v[i]==0);
		}
		else
		{
			scanf("%d", v+i);
		}
	}

	if(v[9]>0)
	{
		b=1;
	}

	for(i=0;i<10;i++)
        {
                if(v[i] != 0)
		{
			if(b)
			{
				if(v[i]>0)
				{
					e++;
					m+=v[i];
				}
			}
			else
			{
				if(v[i]<0)
				{
					e++;
					m+=v[i];
				}
			}
		}
        }

	return m/e;
}
