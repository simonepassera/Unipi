#include <stdlib.h>
#include <stdio.h>

int somma(int *v, int n);

int main(int argc, char **argv)
{
    int n;
    scanf("%d", &n);

    int *a=(int*) malloc(sizeof(int)*n);

    for(int i=0;i<n;i++)
    {
        scanf("%d", a+i);
    }

    printf("%d", somma(a, n));

    free(a);

    return EXIT_SUCCESS;
}

int somma(int *v, int n)
{
    int m=0,s=0;

    for(int i=0; i<n; i++)
    {
        if(s>0)
        {
            s+=v[i];
        }
        else
        {
            s=v[i];
        }

        if(s>m)
        {
            m=s;
        }
    }

    if(m<0)
    {
        return 0;
    }

    return m;
}