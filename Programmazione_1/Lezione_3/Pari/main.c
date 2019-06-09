#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
        int n;

        scanf("%d", &n);

        if(n%2 == 0)
        {
                printf("1");
        }
	else
	{
		printf("0");
	}

        return EXIT_SUCCESS;
}
