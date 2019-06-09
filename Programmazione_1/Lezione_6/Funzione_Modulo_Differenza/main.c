#include <stdlib.h>
#include <stdio.h>

void diff_abs(float a , float b);

int main(int argc, char *argv[])
{
        float a,b;

        scanf("%f", &a);
	scanf("%f", &b);

	diff_abs(a,b);

        return EXIT_SUCCESS;
}

void diff_abs(float a, float b)
{
	float t=a;

        printf("%.2f\n",a-b);
	printf("%.2f\n",b-t);
}

