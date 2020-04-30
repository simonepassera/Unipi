.data
v: .word 1, 2, 3, 4, 5, 6, 7, 8

.text
.global main

main: 	mov r0, #8
	ldr r1, =v
	push {lr}
	bl somma
	pop {pc}

somma:	mov r3, #0 @ index
	mov r2, #0 @ risultato
	push {r4}

	loop:	cmp r3, r0
		beq fine
		ldr r4, [r1, r3, lsl #2] @ r4 = v[index]
		add r2, r2, r4 @ r2 = r2 + v[index]
		add r3, r3, #1 @ index++
		b loop

	fine:	pop {r4}
		mov r0, r2
		mov pc, lr @ return
