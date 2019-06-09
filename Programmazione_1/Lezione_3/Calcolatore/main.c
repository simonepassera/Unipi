#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
        float a,b,r;
	char op;

        scanf("%f", &a);
	scanf("%f", &b);
	getchar();
	scanf("%c", &op);

	switch(op)
	{
		case '+':
				r=a+b;
				break;
		case '-':
				r= a-b;
				break;
		case '/':
				r=a/b;
				break;
		case '%':
				r=((int)a % (int)b) * 1.0;
	}

	printf("%.1f", r);

        return EXIT_SUCCESS;
}
