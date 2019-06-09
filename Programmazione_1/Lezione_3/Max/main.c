#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
        int n1,n2,n3,*max=&n3;

        scanf("%d", &n1);
        scanf("%d", &n2);
        scanf("%d", &n3);

	if(n1>n2 && n1>n3)
	{
		max=&n1;
	}
	else
	{
		if(n2>n1 && n2>n3)
		{
			max=&n2;
		}
	}

        printf("%d", *max);

        return EXIT_SUCCESS;
}
