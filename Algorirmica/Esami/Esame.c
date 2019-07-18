**** Grafi ****

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

typedef struct s_v
{
	int ne;
	int *e;
	int d;
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

void bfs(v *g, int s, int *color)
{
	color[s]=1;
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
			
			if(!color[t])
			{
				color[t]=1;
				(g+t)->d=(g+u)->d+1;
				enqueue(&q, t);
			}
		}
		
	}

	free(q);
}

**** ABR ****

typedef struct nodo
{
	int k;
	struct nodo *dx;
	struct nodo *sx;
}a;

a * insert(a *t, int x)
{
	if(t==NULL)
	{
		a *n=(a*) malloc(sizeof(a));
		n->k=x;
		n->sx=NULL;
		n->dx=NULL;

		return n;
	}

	if(t->k<x)
	{
		t->dx=insert(t->dx, x);
	}
	else
	{
		t->sx=insert(t->sx, x);
	}

	return t;
}

int search(a *t, int x)
{
	if(t==NULL)
	{
		return -1;
	}

	if(x==t->k)
	{
		return 0;
	}

	int f=-1;

	if(x>t->k)
	{
		f=search(t->dx, x);
	}
	else
	{
		f=search(t->sx, x);
	}

	if(f>=0)
	{
		return 1+f;
	}
	else
	{
		return -1;
	}
}

void free_tree(a *t)
{
	if(t!=NULL)
	{
		free_tree(t->sx);
		free_tree(t->dx);

		free(t);
	}
}

int main(int argc, char **argv)
{
	int n, x;
	a *t=NULL;

	scanf("%d", &n);

	for(int i=0; i<n; i++)
	{
		scanf("%d", &x);
		t=insert(t, x);
	}

	int c=0, m=10, *s=(int*) malloc(sizeof(int)*10);

	do
	{
		scanf("%d", &x);

		if(x>=0)
		{

			if(c==m)
			{
				m+=10;
				s=(int*) realloc(s, sizeof(int)*m);
			}

			s[c]=search(t, x);
			c++;
		}
	}while(x>=0);

	for(int i=0; i<c; i++)
	{
		if(s[i]!=-1)
		{
			printf("%d\n", s[i]);
		}
		else
		{
			printf("NO\n");
		}
	}

	free_tree(t);
	free(s);

	return EXIT_SUCCESS;
}

**** Tabelle Hash ****

typedef struct s_e
{
	int x;
	struct s_e *next;
}e;

int hash(int x, int a, int b, int n)
{
	return ((a*x+b)%999149)%(2*n);
}

int insert(e **t, int x, int a, int b, int l)
{
	int i=hash(x, a, b, l), c=0;

	e *n=(e*) malloc(sizeof(e));

	n->x=x;

	if(t[i]==NULL)
	{
		n->next=NULL;

		t[i]=n;
	}
	else
	{
		c=1;
		n->next=t[i];
		t[i]=n;
	}
	
	return c;
}

int max(e **t, int n)
{
	int m=0, l=0;
	e *p=NULL;

	for(int i=0;i<(2*n);i++)
	{
		p=t[i];
		l=0;

		while(p!=NULL)
		{
			l++;
			p=p->next;
		}

		if(l>m)
		{
			m=l;
		}
	}

	return m;
}

int main(int argc, char **argv)
{
	int n, a, b, x, c=0;

	scanf("%d\n%d\n%d", &n, &a, &b);

	e **t=(e**) malloc(sizeof(e*)*2*n);

	for(int i=0; i<n; i++)
	{
		scanf("%d", &x);
		c+=insert(t, x, a, b, n);
	}

	printf("%d\n%d\n", max(t, n), c);

	for(int i=0;i<(2*n);i++)
	{
		free(t[i]);
	}

	free(t);

	return EXIT_SUCCESS;
}

**** Qsort ****

int main(int argc, char **argv)
{
	int n;

	scanf("%d", &n);

	char **v = (char**) malloc(sizeof(char*)*n);

    for(int i=0; i<n; i++)
    {
		v[i] = (char*) malloc(sizeof(char)*101);
        scanf("%s", v[i]);
	}

  	qsort(v, n, sizeof(char*), compare);

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

int compare(const void *x, const void *y)
{
	char **x1=(char**)x;
	char **y1=(char**)y;

	return strcmp(*x1, *y1);
}

qsort(v, n sizeof(int), compare);

int compare(const void *x, const void *y)
{
	int *a=(int*)x, *b=(int*)y;
	
	return *a-*b;
}