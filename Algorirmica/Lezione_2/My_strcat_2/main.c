#include <stdlib.h>
#include <stdio.h>

char * my_strcat2(char l1, char *s1, char l2, char *s2);

int main(int argc, char **argv)
{
	int l1,l2;

	scanf("%d", &l1);
	char *s1 = (char *) malloc((l1+1)*sizeof(char));
	scanf("%s", s1);

	scanf("%d", &l2);
	char *s2 = (char *) malloc((l2+1)*sizeof(char));
	scanf("%s", s2);

	char *s=my_strcat2(l1, s1, l2, s2);

	printf("%s", s);

	free(s1);
	free(s2);
	free(s);

        return EXIT_SUCCESS;
}

char * my_strcat2(char l1, char *s1, char l2, char *s2)
{
	char *s=(char *)malloc(sizeof(char)*(l1+l2+1));

	int i;

	for(i=0;i<l1;i++)
	{
		s[i]=s1[i];
	}

	for(int c=0;c<l2;c++,i++)
        {
                s[i]=s2[c];
        }

	s[i]='\0';

	return s;
}
