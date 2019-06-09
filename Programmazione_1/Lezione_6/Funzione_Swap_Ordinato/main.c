#include <stdlib.h>
#include <stdio.h>

void ordered_swap(int *n1, int *n2, int *n3);

int main(int argc, char *argv[])
{
        int n1,n2,n3;

        scanf("%d", &n1);
	scanf("%d", &n2);
	scanf("%d", &n3);

	ordered_swap(&n1,&n2,&n3);

	printf("%d\n%d\n%d", n1, n2, n3);

        return EXIT_SUCCESS;
}

void ordered_swap(int *n1, int *n2, int *n3)
{
	int t;

	if(*n1>=*n2 && *n1>=*n3)
	{
		t=*n1;

		*n1=*n3;
		*n3=t;

		if(*n1>=*n2)
		{
			t=*n1;
			*n1=*n2;
			*n2=t;
		}
	}
	else
	{
		if(*n2>=*n1 && *n2>=*n3)
        	{
                	t=*n2;

               	 	*n2=*n3;
                	*n3=t;

                	if(*n1>=*n2)
                	{
                        	t=*n1;
                        	*n1=*n2;
                        	*n2=t;
                	}
        	}
		else
		{
			if(*n1>=*n2)
                	{
                        	t=*n1;
                        	*n1=*n2;
                        	*n2=t;
                	}
		}
	}
}
