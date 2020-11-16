open List

type texp =
| TInt of int
| TDen of int (* Run-time index *)
| TLet of texp * texp
| TTimes of texp * texp
| TPlus of texp * texp
;;

let emptyrenv = [];;

let rec teval (e : texp) (renv : int list) : int =
	match e with
	| TInt i 		  -> i
	| TDen n 		  -> List.nth renv n
	| TLet (e, ebody) -> let xval = teval e renv in
	 					   let renv1 = xval::renv in
	 						 teval ebody renv1
	| TTimes (e1, e2) -> (teval e1 renv) * (teval e2 renv)
	| TPlus (e1, e2)  -> (teval e1 renv) + (teval e2 renv) 
;;

(* REPL *)

let te = TLet (TInt 5, TLet (TInt 6, TTimes (TDen 1, TDen 0)));;
teval te emptyrenv;;	(* int = 30 *)
