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

int dfs(v *g, int s, int *color)
{
	int t;
	
	for(int i=0; i<((g+s)->ne); i++)
	{
		t=*((g+s)->e+i);
		
		if(color[t]==0)
		{
			color[t]=-color[s];
			
			if(!dfs(g, t, color))
			{
				return 0;
			}
		}
		else
		{
			if(color[s]==color[t])
			{
				return 0;
			}
		}
	}
	
	return 1;
}

int bipartito(v *g, int n)
{
	int *color = (int*) malloc(n*sizeof(int)),i;
	
	for(i=0; i<n; i++)
	{
		color[i]=0;
	}
	
	for(i=0; i<n; i++)
	{
		if(color[i]==0)
		{
			color[i]=1;
			
			if(!dfs(g, i, color))
			{
				free(color);
				return 0;
			}
		}
	}
	
	return 1;
}

int main(int argc, char **argv)
{
	int n;
	
	scanf("%d", &n);
	
	v *g = read(n);
	
	printf("%d\n", bipartito(g, n));
	
	for(int i=0; i<n; i++)
	{
		free((g+i)->e);
	}
	
	free(g);
	
	return EXIT_SUCCESS;
}
