#include <stdlib.h>
#include <stdio.h>

typedef struct s_v
{
	int ne;
	int *e;
	int c;
}v;

v * read(int n)
{
	int i,ne,j;

	v *g = (v*) malloc(n*sizeof(v));

	for(i=0; i<n; i++)
	{
        scanf("%d", &ne);
        
    	(g+i)->ne=ne;
        (g+i)->e= (int*) malloc(ne*sizeof(int));
        
        for(j=0; j<ne; j++)
        {
        	scanf("%d", (g+i)->e+j);
        }
	}
	
	return g;
}

void dfs(v *g, int s)
{
	int t, i;
	
	(g+s)->c=1;
	
	for(i=0; i<((g+s)->ne); i++)
	{
		t=*((g+s)->e+i);
		
		if((g+t)->c==0)
		{
			dfs(g, t);
		}
	}
}

int connesso(v *g, int n)
{
	int b=1, k, i;
	
	for(k=0; k<n; k++)
	{
		(g+k)->c=0;
	}
	
	dfs(g, 0);
	
	for(i=0; i<n && b; i++)
	{
		if((g+i)->c==0)
		{
			b=0;
		}
	}
	
	return b;
}

int albero(v *g, int n)
{
	int i, ne=0, a=0;
	
	for(i=0; i<n; i++)
	{
		ne+=(g+i)->ne;
	}
	
	ne/=2;
	
	if(ne==(n-1))
	{
		if(connesso(g, n))
		{
			a=1;
		}
	}
	
	return a;
}

int main(int argc, char **argv)
{
	int n, r=0, i;
	
	scanf("%d", &n);
	
	v *g=read(n);

	if(albero(g, n))
	{
		r=1;
	}
	
	printf("%d\n", r);
	
	for(i=0; i<n; i++)
	{
		free((g+i)->e);
	}
	
	free(g);
	
	return EXIT_SUCCESS;
}