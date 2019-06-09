#include <stdlib.h>
#include <stdio.h>

typedef struct s_p
{
	int x;
	int y;
	int c;
}p;

int compare(const void *x, const void *y)
{
	p *x1=(p*)x;
	p *y1=(p*)y;

	return x1->c - y1->c;
}

int query(int x1, int y1, int x2, int y2, p *v, int n)
{
	int s=0, cp=-1;

	for(int i=0; i<n; i++)
	{
		if(x1<=(v+i)->x && (v+i)->x<=x2 && y1<=(v+i)->y && (v+i)->y<=y2)
		{
			if((v+i)->c != cp)
			{
				cp=(v+i)->c;
				s++;
			}
		}
	}
	
	return s;
}

int main(int argc, char **argv)
{
	int n, m, x1, y1, x2, y2;

	scanf("%d %d", &n, &m);

	p *v=(p*) malloc(sizeof(p)*n);

    for(int i=0; i<n; i++)
    {
        scanf("%d %d %d", &(v+i)->x, &(v+i)->y, &(v+i)->c);
    }

	int *s = (int*) malloc(sizeof(int)*m);

	qsort(v, n, sizeof(p), compare);

	for(int i=0; i<m; i++)
	{
		scanf("%d %d %d %d", &x1, &y1, &x2, &y2);

		s[i]=query(x1, y1, x2, y2, v, n);
	}

	for(int i=0; i<m; i++)
	{
		printf("%d\n", s[i]);
	}

	free(v);
	free(s);
	
	return EXIT_SUCCESS;
}
