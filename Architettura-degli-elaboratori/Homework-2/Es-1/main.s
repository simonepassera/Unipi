.data
x:	.word 1, 2, 3, 4			@ indirizzo di cui va calcolato il prefix
n:    	.word 4        				@ lunghezza del vettore
fmt:	.asciz "[1,2,3,4] -> [%d,%d,%d,%d]\n"

.text
.global main

main:	ldr r0, =n
      	ldr r0, [r0]				@ caricamento di n in r0
      	ldr r1, =x    				@ indirizzo del vettore, modifica in place

	mov r2, #0				@ index
	mov r3, #0				@ prefisso x[0] ... x[index-1]

	loop:	cmp r2, r0
		beq end
		ldr r4, [r1, r2, lsl #2]	@ r4 = x[index]
		add r3, r3, r4			@ aggiorno il prefisso
		str r3, [r1, r2, lsl #2]	@ x[index] = prefisso
		add r2, r2, #1			@ index++
		b loop

	end:    ldr r0, =fmt
		mov r5, r1
		ldr r1, [r5]
		ldr r2, [r5, #4]
		ldr r3, [r5, #8]
		ldr r4, [r5, #12]
		push {r4}
		bl printf
		mov r7, #1    			@ exit
      		svc 0
