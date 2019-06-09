#include <stdlib.h>
#include <stdio.h>

void rettangolo(int h, int l);

int main(int argc, char *argv[])
{
	int h,l;

	scanf("%d", &h);
	scanf("%d", &l);

	rettangolo(h,l);

	return EXIT_SUCCESS;
}

void rettangolo(int h, int l)
{
	int i,j;

	for(i=0;i<h;i++)
	{
		if(i==0 || i==h-1)
		{
			for(j=0;j<l;j++)
			{
				printf("*");
			}

			printf("\n");
		}
		else
		{
			printf("*");

			for(j=0;j<l-2;j++)
                        {
                                printf(" ");
                        }

                        printf("*\n");
		}
	}
}
