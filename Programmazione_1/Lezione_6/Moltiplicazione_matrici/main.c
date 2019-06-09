#include <stdlib.h>
#include <stdio.h>

void moltiplicazione_matrici(int a[][2], int b[][3], int m[][3]);

int main(int argc, char *argv[])
{
        int a[4][2],b[2][3],c[4][3],i,j;

	for(i=0;i<4;i++)
	{
		for(j=0;j<2;j++)
		{
			scanf("%d", &a[i][j]);
		}
	}

	for(i=0;i<2;i++)
        {
                for(j=0;j<3;j++)
                {
                        scanf("%d", &b[i][j]);
                }
        }

	moltiplicazione_matrici(a,b,c);

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

void moltiplicazione_matrici(int a[][2], int b[][3], int m[][3])
{
	int i,j,c;

        for(i=0;i<4;i++)
        {
                for(j=0;j<3;j++)
                {
			m[i][j]=0;

			for(c=0;c<2;c++)
			{
				m[i][j]+=a[i][c]*b[c][j];
			}
		}
	}
}
