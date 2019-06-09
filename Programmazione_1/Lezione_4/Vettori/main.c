#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	int v1[5],v2[5],i;

	for(i=0;i<5;i++)
	{
		scanf("%d", v1+i);
	}

	for(i=0;i<5;i++)
        {
                scanf("%d", v2+i);
        }

	for(i=0;i<5;i++)
        {
		if(i==0)
		{
			printf("[%d,", v1[i]);
		}
		else
		{
			if(i==4)
			{
				printf("%d]\n", v1[i]);
			}
			else
			{
				printf("%d,", v1[i]);
			}
        	}
	}

	for(i=0;i<5;i++)
        {
                if(i==0)
                {
                        printf("[%d,", v2[i]);
                }
                else
                {
                        if(i==4)
                        {
                                printf("%d]\n", v2[i]);
                        }
                        else
                        {
                                printf("%d,", v2[i]);
                        }
                }
        }

	for(i=0;i<5;i++)
        {
                if(i==0)
                {
                        printf("[%d,", v1[i]+v2[i]);
                }
                else
                {
                        if(i==4)
                        {
                                printf("%d]\n", v1[i]+v2[i]);
                        }
                        else
                        {
                                printf("%d,", v1[i]+v2[i]);
                        }
                }
        }

	return EXIT_SUCCESS;
}
