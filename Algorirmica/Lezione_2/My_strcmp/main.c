#include <stdlib.h>
#include <stdio.h>

int my_strcmp(char *s1, char *s2);

int main(int argc, char **argv)
{

	char s1[1001],s2[1001];
	int r;

	scanf("%s", s1);
	scanf("%s", s2);

	r=my_strcmp(s1, s2);

	if(r==1)
	{
		printf("+1");
	}
	else
	{
		printf("%d", r);
	}

        return EXIT_SUCCESS;
}

int my_strcmp(char *s1, char *s2)
{
	int i=0;

	while(s1[i]!='\0')
	{
       	if(s2[i]=='\0')
		{
			return 1;
		}
       	else
		{
			if(s1[i]<s2[i])
			{
				return -1;
			}
       		else
			{
				if(s1[i]>s2[i])
				{
					return 1;
				}
			}
		}

	        i++;
    	}

	return 0;
}
