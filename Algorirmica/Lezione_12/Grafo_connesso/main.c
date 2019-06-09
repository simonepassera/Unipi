#include <stdlib.h>
#include <stdio.h>

typedef struct s_v
{
	int ne;
	int *e;
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

void dfs(v *g, int s, int *color)
{
	int t;
	
	color[s]=1;
	
	for(int i=0; i<((g+s)->ne); i++)
	{
		t=*((g+s)->e+i);
		
		if(color[t]==0)
		{
			dfs(g, t, color);
		}
	}
}

int connesso(v *g, int n)
{
	int *color = (int*) calloc(n, sizeof(int)), b=1;
	
	dfs(g, 0, color);
	
	for(int i=0; i<n && b; i++)
	{
		if(color[i]==0)
		{
			b=0;
		}
	}
	
	free(color);
	
	return b;
}

int main(int argc, char **argv)
{
	int n;
	
	scanf("%d", &n);
	
	v *g = read(n);
	
	printf("%d\n", connesso(g, n));
	
	for(int i=0; i<n; i++)
	{
		free((g+i)->e);
	}
	
	free(g);
	
	return EXIT_SUCCESS;
}