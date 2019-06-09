#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct s_w
{
	char *s;
	int f;
}w;

int compare(const void *x, const void *y)
{
	char **x1=(char**)x;
	char **y1=(char**)y;

	return strcmp(*x1, *y1);
}

int compare2(const void *x, const void *y)
{
	w *x1=(w*)x;
	w *y1=(w*)y;

	return y1->f - x1->f;
}

int compare3(const void *x, const void *y)
{
        w *x1=(w*)x;
        w *y1=(w*)y;

        return strcmp(x1->s, y1->s);
}

int main(int argc, char **argv)
{
	int n, k;

	scanf("%d", &n);
	scanf("%d", &k);

	char **v = (char**) malloc(sizeof(char*)*n);

    for(int i=0; i<n; i++)
    {
		v[i] = (char*) malloc(sizeof(char)*101);
        scanf("%s", v[i]);
	}

  	qsort(v, n, sizeof(char*), compare);
  	
  	if(k==n)
	{
		for(int i=0; i<n; i++)
		{
			printf("%s\n", v[i]);
		}
		
		for(int i=0; i<n; i++)
		{
			free(v[i]);
		}
		
		free(v);

		return EXIT_SUCCESS;
	}

	w *c = (w*) malloc(sizeof(w)*n);

    for(int i=0; i<n; i++)
    {
		(c+i)->s=NULL;
		(c+i)->f=0;
    }

	int p=0;
	
	for(int i=0; i<n; i++)
	{
		if((c+p)->s == NULL)
		{
			(c+p)->s=v[i];
			(c+p)->f++;
		}
		else
		{
			if(strcmp(v[i], (c+p)->s) == 0)
			{
				(c+p)->f++;
			}
			else
			{
				p++;
				
				(c+p)->s=v[i];
				(c+p)->f++;
			}
		}
	}

	qsort(c, p+1, sizeof(w), compare2);
	qsort(c, k, sizeof(w), compare3);

	for(int i=0; i<k; i++)
	{
		printf("%s\n", (c+i)->s);
	}
	
	for(int i=0; i<n; i++)
	{
		free(v[i]);
	}

	free(c);
	free(v);

	return EXIT_SUCCESS;
}
