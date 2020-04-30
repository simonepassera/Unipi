.data
x:		.word 1, 2, 3, 4
y:    		.word 4, 3, 2, 1
l:		.word 4
fmt:		.asciz "[1,2,3,4] * [4,3,2,1] -> %d\n"

.text
.global main

main:		ldr r0, =x			@ Vettore x
      		ldr r1, =y			@ Vettore y
      		ldr r2, =l
		ldr r2, [r2]			@ Lunghezza
		push {r3-r6}
		bl prodotto
		pop {r3-r6}
		mov r1, r0
		ldr r0, =fmt
		bl printf
		mov r7, #1    			@ exit
      		svc 0

prodotto:	mov r3, #0			@ index
		mov r4, #0			@ risultato

		loop:	cmp r3, r2
			beq end
			ldr r5, [r0, r3, lsl #2]	@ x[index]
			ldr r6, [r1, r3, lsl #2]	@ y[index]
			mul r5, r5, r6			@ x[index] * y[index]
			add r4, r4, r5			@ aggiorno risultato
			add r3, r3, #1
			b loop

		end:	mov r0, r4
			mov pc, lr
