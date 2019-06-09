#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int anagramma(unsigned char *s1, unsigned char *s2);
int FindVal(unsigned char *s, int v);

int main(int argc, char **argv)
{

	unsigned char s1[256],s2[256];

	scanf("%s", s1);
	scanf("%s", s2);

	printf("%d", anagramma(s1, s2));

    return EXIT_SUCCESS;
}

int FindVal(unsigned char *s, int v)
{
        int i=0;

        while(s[i]!='\0')
        {
                if(s[i]==v)
                {
                        return 1;
                }

				i++;
        }

        return 0;
}

int anagramma(unsigned char *s1, unsigned char *s2)
{
	int b=1,i=0;

	if(strlen(s1)!=strlen(s2))
	{
		return 0;
	}

	while(s1[i]!='\0' && b)
	{
		if(!FindVal(s2, s1[i]))
		{
			b=0;
		}

		i++;
	}

	return b;
}
