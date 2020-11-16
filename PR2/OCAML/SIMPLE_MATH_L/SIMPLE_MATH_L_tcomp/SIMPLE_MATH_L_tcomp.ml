type ide = string;;

type exp =
| Int of int
| Den of ide
| Times of exp * exp
| Plus of exp * exp
| Let of ide * exp * exp
;;

let emptyst = [];;

type texp =
| TInt of int
| TDen of int (* Run-time index *)
| TLet of texp * texp
| TTimes of texp * texp
| TPlus of texp * texp
;;

let rec getindex st x =
	match st with
	| [] -> failwith ("variable not found")
	| y::yr -> if x=y
				then 0
				else 1 + getindex yr x
;;

let rec tcomp (e: exp) (st: string list) : texp =
	match e with
	| Int i 			-> TInt i
	| Den x 			-> TDen (getindex st x)
	| Let (x, e, ebody) -> let st1 = x::st in
							 TLet (tcomp e st, tcomp ebody st1)
	| Times (e1, e2) 	-> TTimes (tcomp e1 st, tcomp e2 st)
	| Plus (e1, e2) 	-> TPlus (tcomp e1 st, tcomp e2 st)
;;

(* REPL *)

let e = Let ("x", Int 5, Let ("y", Int 6, Times (Den "x", Den "y")));;
tcomp e emptyst;; (* texp = TLet (TInt 5, TLet (TInt 6, TTimes (TDen 1, TDen 0))) *)
