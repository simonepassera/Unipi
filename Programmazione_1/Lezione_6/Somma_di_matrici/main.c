#include <stdlib.h>
#include <stdio.h>

void somma_matrici(int a[][3], int b[][3], int m[][3]);

int main(int argc, char *argv[])
{
        int a[4][3],b[4][3],c[4][3],i,j;

	for(i=0;i<4;i++)
	{
		for(j=0;j<3;j++)
		{
			scanf("%d", &a[i][j]);
		}
	}

	for(i=0;i<4;i++)
        {
                for(j=0;j<3;j++)
                {
                        scanf("%d", &b[i][j]);
                }
        }

	somma_matrici(a,b,c);

	for(i=0;i<4;i++)
        {
                for(j=0;j<3;j++)
                {
                        printf("%d ", c[i][j]);
                }

		printf("\n");
        }

        return EXIT_SUCCESS;
}

void somma_matrici(int a[][3], int b[][3], int m[][3])
{
	int i,j;

        for(i=0;i<4;i++)
        {
                for(j=0;j<3;j++)
                {
			m[i][j]=a[i][j]+b[i][j];
		}
	}
}
