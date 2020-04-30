.data
fmt:		.asciz "Numero occorrenze -> %d\n"

.text
.global main

main:		ldr r0, [r1, #4]			@ r0 = argv[1] = "Stringa"
              	ldr r1, [r1, #8]
		ldrb r1, [r1]				@ r1 = argv[2] = "Carattere"
		push {r2, r3}
		bl search
		pop {r2, r3}
		mov r1, r0
		ldr r0, =fmt
		bl printf
		mov r7, #1    				@ exit
      		svc 0

search:		mov r2, #0				@ risultato

		loop:	ldrb r3, [r0], #1		@ carico un carattere della stringa
	                cmp r3, #0			@ carattere == NULL
      	 	        moveq r0, r2			@ r0 = risultato
  	              	moveq pc, lr			@ return
        	        cmp r3, r1			@ carattere == carattere da cercare
             		addeq r2, r2, #1		@ occorrenze++
              	  	b loop
