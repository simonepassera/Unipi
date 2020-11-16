type ide = string;;

type exp =
| Int of int
| Den of ide
| Times of exp * exp
| Plus of exp * exp
| Let of ide * exp * exp
;;

let emptyenv = [];;

let rec lookup env x =
	match env with
	| []		-> failwith	("not found")
	| (y, v)::t	-> if x=y
				    then v
				   	else lookup t x
;;

let bind x v env = (x, v)::env;;

let rec eval (e: exp) (env: (string*int) list) : int =
	match e with
	| Int n 		    -> n
	| Den x 		    -> lookup env x
	| Let (x, e, ebody) -> let xval = eval e env in
						     let env1 = (x, xval)::env in
						       eval ebody env1
	| Times (e1, e2)    -> (eval e1 env) * (eval e2 env)
	| Plus (e1, e2) 	-> (eval e1 env) + (eval e2 env)
;;

(* REPL *)

let e = Let ("x", Int 5, Let ("y", Int 6, Times (Den "x", Den "y")));;
eval e emptyenv;;	(* int = 30 *)
