#include <stdlib.h>
#include <stdio.h>

typedef struct s_coordinate
{
	int x;
	int y;
}coordinate;

int compare(const void *p1, const void *p2);

int main(int argc, char **argv)
{
	int n, i;
	scanf("%d", &n);

	coordinate *v = (coordinate*) malloc(sizeof(coordinate)*n);

    for(i=0; i<n; i++)
    {
        scanf("%d %d", &(v+i)->x, &(v+i)->y);
    }

	qsort(v, n, sizeof(coordinate), compare);

  	for(i=0; i<n; i++)
	{
		printf("%d %d\n", (v+i)->x, (v+i)->y);
	}

	free(v);

	return EXIT_SUCCESS;
}

int compare(const void *p1, const void *p2)
{
	coordinate *a=(coordinate*)p1;
	coordinate *b=(coordinate*)p2;

	if(a->x == b->x)
	{
		return b->y - a->y;
	}
	else
	{
		return a->x - b->x;
	}
}