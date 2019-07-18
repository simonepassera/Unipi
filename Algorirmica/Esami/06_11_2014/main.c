#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct s_e
{
	char *nome;
	int v;
	struct s_e *next;
}e;

int hash(char *s, int n);
e * search(e **t, char *s, int n);
int compare(const void *x, const void *y);
void free_list(e *l);

int main(int argc, char **argv)
{
	int n, v, x, j=0;
	char *s=(char*) malloc(sizeof(char)*101);
	
	scanf("%d", &n);
	
	if(n!=0)
	{
	
		e **t=(e**) malloc(sizeof(e*)*2*n), *l;
		
		for(int i=0; i<(2*n); i++)
		{
			t[i]=NULL;
		}
		
		for(int i=0; i<n; i++)
		{
			scanf("%s", s);
			scanf("%d", &v);
			
			l=search(t, s, n);

			if(l==NULL)
			{
				e *z=(e*) malloc(sizeof(e));
				z->v=v;
				z->nome=(char*) malloc(sizeof(char)*101);
				strcpy(z->nome, s);
				
				x=hash(s, n);
				
				z->next=t[x];
				t[x]=z;
				
				j++;
			}
			else
			{
				if(v>l->v)
				{
					l->v=v;
				}
			}
		}
		
		free(s);
		
		e **a=(e**) malloc(sizeof(e*)*j);
		
		int y=0;
		
		for(int i=0; i<(2*n); i++)
		{
			e *l=t[i];
			
			while(l!=NULL)
			{
				a[y]=l;
				l=l->next;
				y++;
			}
		}
		
		qsort(a, j, sizeof(e*), compare);
		
		if(j>15)
		{
			j=15;
		}
		
		for(int i=0; i<j; i++)
		{
			printf("%s\n", (*(a+i))->nome);
		}
		
		for(int i=0; i<(2*n); i++)
		{
			free_list(t[i]);
		}
		
		free(t);
		free(a);
	}
}

int hash(char *s, int n)
{
	int sum=0, l=strlen(s);
	
	for(int i=0; i<l; i++)
	{
		sum+=(unsigned int) s[i];
	}
	
	return sum%(2*n);
}

e * search(e **t, char *s, int n)
{
	int x=hash(s, n);
	
	e *l=t[x];
	
	while((l!=NULL) && (strcmp(s, l->nome)!=0))
	{
		l=l->next;
	}
	
	return l;
}

int compare(const void *x1, const void *y2)
{
	e **x=(e**)x1;
	e **y=(e**)y2;
	
	if((*x)->v==(*y)->v)
	{
		return strcmp((*x)->nome, (*y)->nome);
	}
	else
	{
		return ((*y)->v - (*x)->v);
	}
}

void free_list(e *l)
{
	if(l!=NULL)
	{
		free_list(l->next);
		free(l);
	}
}