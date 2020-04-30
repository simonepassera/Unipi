.data
fmt: .asciz "%d ^ %d = %d\n"

.text
.global main

main:	ldr r0, [r1, #4]
	push {r1-r3}
	bl atoi
	pop {r1-r3}
	mov r2, r0				@ r2 = argv[1] = x
	ldr r0, [r1, #8]
	push {r1-r3}
	bl atoi
	pop {r1-r3}
	mov r3, r0				@ r3 = argv[2] = n

	mov r0, r2
	mov r1, r3
	bl xallan
	mov r1, r2
	mov r2, r3
	mov r3, r0
	ldr r0, =fmt
	bl printf
	mov r7, #1
	svc 0

xallan:		cmp r1 , #0			@ n == 0
		moveq r0, #1
		moveq pc, lr
		sub r1, r1, #1			@ n = n - 1
		push {r0, lr}
		bl xallan
		pop {r1, lr}
		mul r0, r0, r1			@ risultato = x * xallan(x, n-1)
		mov pc, lr

atoi:	mov r1, #0 				@ risultato
	mov r2, #10

	loop: 	ldrb r3, [r0], #1
		cmp r3, #0
		moveq r0, r1
		moveq pc, lr
		mul r1, r1, r2
		sub r3, r3, #48	 		@ codice ascii '0'
		add r1, r1, r3
		b loop
