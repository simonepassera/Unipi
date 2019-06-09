#include <stdlib.h>
#include <stdio.h>

int matrice(int m[][3]);

int main(int argc, char *argv[])
{
        int m[4][3],i,j;

	for(i=0;i<4;i++)
	{
		for(j=0;j<3;j++)
		{
			scanf("%d", &m[i][j]);
		}
	}

	printf("%d", matrice(m));

        return EXIT_SUCCESS;
}

int matrice(int m[][3])
{
	int r=-1,i,j,c=0;

        for(i=0;i<3;i++)
        {
                for(j=0;j<4;j++)
                {
                        if(m[j][i]%3 == 0)
			{
				c++;
			}
                }

		if(c==4)
		{
			r=i;
		}

		c=0;
        }

	return r;
}
