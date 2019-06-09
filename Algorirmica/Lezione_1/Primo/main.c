#include <stdlib.h>
#include <stdio.h>

int main(int argc, char **argv)
{

	int n,r,c=2,p=1;

	scanf("%d",&n);

	while(c<n)
	{
		r=n%c;

		if(r==0)
		{
			p=0;
        }

		c++;
    }

    if(p)
	{
		printf("1");
	}
	else
	{
		printf("0");
    }

    return EXIT_SUCCESS;
}
