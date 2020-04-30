.data
fmt: .asciz "%d*%d = %d\n"

.text
.global main

main:	ldr r0, [r1, #4]
	push {r1-r3}
	bl atoi
	pop {r1-r3}
	mov r2, r0				@ r2 = argv[1]
	ldr r0, [r1, #8]
	push {r1-r3}
	bl atoi
	pop {r1-r3}
	mov r3, r0				@ r3 = argv[2]

	cmp r2, #0
	beq zero
	cmp r3, #0
	beq zero

	cmp r2, r3
	movle r4, r2			@ index
	movle r5, r3
	movgt r4, r3			@ index
	movgt r5, r2
	mov r6, #0

	loop:	cmp r4, #0
		beq r
		add r6, r6, r5
		sub r4, r4, #1
		b loop

	zero:	mov r1, r2
		mov r2, r3
		mov r3, #0
		b end

	r:	mov r1, r2
		mov r2, r3
		mov r3, r6
		b end

	end:	ldr r0, =fmt
		bl printf
		mov r7, #1
		svc 0

atoi:	mov r1, #0 				@ risultato
	mov r2, #10

	loop2: 	ldrb r3, [r0], #1
		cmp r3, #0
		moveq r0, r1
		moveq pc, lr
		mul r1, r1, r2
		sub r3, r3, #48	 		@ codice ascii '0'
		add r1, r1, r3
		b loop2
