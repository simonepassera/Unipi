#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	float t;

	scanf("%f", &t);

	printf("%.2f", t*1.8+32);

	return EXIT_SUCCESS;
}
