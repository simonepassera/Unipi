.data
fmt: .asciz "%d/%d = q:%d r:%d\n"
e: .asciz "Impossibile!\n"

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

	cmp r3, #0				@ divisore == 0
	bne c
	ldr r0, =e
        bl printf
        mov r7, #1
        svc 0

	c:	cmp r2, r3
		moveq r4, #1			@ quoziente
		moveq r5, #0			@ resto
		beq end
		movlt r4, #0			@ quoziente
		movlt r5, r2			@ resto
		blt end

		mov r0, r3			@ index
		mov r4, #0			@ quoziente

	loop:	cmp r0, r2
		bgt resto
		add r0, r0, r3
		add r4, r4, #1
		b loop

	resto:	sub r1, r0, r3
		sub r5, r2, r1			@ resto = dividendo - (index - divisore)

	end:	ldr r0, =fmt
		mov r1, r2
		mov r2, r3
		mov r3, r4
		push {r5}
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
