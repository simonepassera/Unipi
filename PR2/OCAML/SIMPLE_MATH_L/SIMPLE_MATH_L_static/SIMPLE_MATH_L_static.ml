type ide = string;;

type exp =
| Int of int
| Den of ide
| Times of exp * exp
| Plus of exp * exp
| Let of ide * exp * exp
;;

let emptyst = [];;

let rec lookup st x =
    match st with
    | []		-> false
    | y::t 		-> if x = y
    			   then true
                   else lookup t x
;;

let extend st x = x::st;;

let rec check (e:exp) st =
	match e with
	| Int (n)			-> true
	| Den (id)			-> lookup st id
	| Times (e1, e2) 	-> (check e1 st) && (check e2 st)
	| Plus (e1, e2) 	-> (check e1 st) && (check e2 st)
	| Let (id, e1, e2)	-> (check e1 st) && (check e2 (extend st id))
;;

(* REPL *)

let e = Let ("x", Int 5, Let ("y", Int 6, Times (Den "x", Den "y")));;
check e emptyst;;	(* true *)

let e2 = Let ("y", Int 6, Times (Den "x", Den "y"));;
check e2 emptyst;;	(* false *)
