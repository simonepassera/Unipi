#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
        int h,m,s;

        scanf("%d", &s);

	h=s/3600;
	m=(s-(h*3600))/60;

        printf("%d h %d min %d s", h, m, s-(h*3600)-(m*60));

        return EXIT_SUCCESS;
}
