#include <stdlib.h>
#include <stdio.h>

typedef struct s_v
{
	int ne;
	int *e;
	int d;
	int c;
}v;

typedef struct s_el
{
	int x;
	struct s_el *next;
}el;

typedef struct s_queue
{
	int n;
	el *e;
	el *t;
}queue;

queue * create_queue()
{
	queue *q= (queue*) malloc(sizeof(queue));
	
	q->n=0;
	q->e=NULL;
	q->t=NULL;
	
	return q;
}

void enqueue(queue **q, int s)
{
	el *n= (el*) malloc(sizeof(el));
	n->x=s;
	n->next=NULL;
	
	if((*q)->e==NULL)
	{
		(*q)->e=n;
	}
	else
	{
		((*q)->t)->next=n;
	}
	
	(*q)->t=n;
	((*q)->n)++;
}

int dequeue(queue **q)
{
	int r=-1;
	
	if((*q)->n!=0)
	{
		el *t=(*q)->e;
		r=t->x;
		(*q)->e=t->next;
		free(t);
		((*q)->n)--;
		
		if((*q)->n==0)
		{
			(*q)->t=NULL;
		}
	}
	
	return r;
}

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

void bfs(v *g, int s)
{
	(g+s)->c=1;
	(g+s)->d=0;
	
	queue *q=create_queue();
	enqueue(&q, s);

	int u, i, t;
	
	while((q->n)!=0)
	{
		u=dequeue(&q);
		
		for(i=0; i<(g+u)->ne; i++)
		{
			t=*((g+u)->e+i);
			
			if(!(g+t)->c)
			{
				(g+t)->c=1;
				(g+t)->d=(g+u)->d+1;
				enqueue(&q, t);
			}
		}
		
	}

	free(q);
}

int p(v *g, int n, int i, int j)
{
	for(int k=0; k<n; k++)
	{
		(g+k)->c=0;
		(g+k)->d=-1;
	}
	
	bfs(g, i);
	
	return (g+j)->d;
}

int main(int argc, char **argv)
{
	int n, m, i, j, k;
	
	scanf("%d", &n);
	
	v *g = read(n);
	
	scanf("%d", &m);
	
	int *r= (int*) malloc(m*sizeof(int));

	for(k=0; k<m; k++)
	{
		scanf("%d %d", &i, &j);
		r[k]=p(g, n, i, j);
	}
	
	for(k=0; k<m; k++)
	{
		printf("%d\n", r[k]);
	}
	
	for(k=0; k<n; k++)
	{
		free((g+k)->e);
	}
	
	free(g);
	free(r);
	
	return EXIT_SUCCESS;
}