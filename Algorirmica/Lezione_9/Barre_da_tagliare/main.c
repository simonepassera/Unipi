#include <stdio.h>
#include <stdlib.h>

int cut_rod(int *p, int n, int *s)
{
	int r[n+1], q=-1;

	r[0]=0;

	for(int j=1;j<=n;j++)
	{
		for(int i=1;i<=j;i++)
		{
			if(q<(p[i]+r[j-i]))
			{
				q=p[i]+r[j-i];
				s[j]=i;
			}
		}

		r[j]=q;
	}

	return r[n];
}

void print_cut_rod(int n, int *s)
{
	while(n>0)
	{
		printf("%d ", s[n]);
		n-=s[n];
	}
}

int main(int argc, char **argv)
{
	int n, x;

	scanf("%d", &n);

	int p[n+1], s[n+1];
	p[0]=0;
	s[0]=0;

	for(int i=1; i<=n; i++)
	{
		scanf("%d", &x);
		p[i]=x;
	}

	printf("%d\n", cut_rod(p, n, s));

	print_cut_rod(n, s);

	return EXIT_SUCCESS;
}
