Dama
====

Il gioco della Dama in Java.

*Penso che sia utile usare questa sezione per commenti corposi.

Riporto le regole di wikipedia sulla mangiata della dama italiana sperando siano utili a far comprendere le priorità della mangiata:


   - La presa è obbligatoria: infatti, quando una pedina incontra una pedina di colore diverso, con una casella libera dietro,
	sulla stessa diagonale, è obbligata a prenderla (si dice anche catturarla o mangiarla). La pedina, dopo la prima presa, 
	qualora si trovi nelle condizioni di poter nuovamente prendere, deve continuare a catturare pezzi (fino a un massimo di tre),
	potendo anche prendere la dama.

	(Direi di ignorare pure la seguente)
   - Il soffio è la facoltà di eliminare una pedina che avrebbe dovuto eseguire una presa, ma che invece effettuava una mossa diversa, 
	intenzionalmente o per distrazione. Il nome «soffio» deriva dal gesto che veniva effettuato dal giocatore che prendeva la pedina 
	causa della presa mancata e vi soffiava sopra. In alternativa, per fini tattici, anziché «soffiare» si poteva obbligare l'avversario 
	al rispetto della regola di presa. Esso resiste nella dama familiare, ma non è più applicato sin dal 1936 nelle partite ufficiali.
   
   - La pedina può prendere solo in avanti, lungo le due diagonali.

   - La dama può prendere (catturare, mangiare) su tutte e quattro le diagonali.

   - La dama, dopo la prima presa, qualora si trovi nelle condizioni di poter nuovamente prendere, deve continuare a catturare pezzi.

   - Sia per la dama sia per la pedina è obbligatorio prendere dalla parte ove c'è il maggior numero di pezzi in presa.

   - Se una dama e una pedina possono prendere un egual numero di pezzi, o una dama può scegliere tra la presa di una dama 
	e di una pedina, è obbligatorio prendere con il pezzo di maggior qualità, cioè la dama. Se una dama ha la possibilità di 
	scegliere tra prese di un egual numero di pezzi di qualità diversa, deve prendere dalla parte ove c'è una maggior qualità 
	complessiva. Se una dama può scegliere tra prese di eguale qualità complessiva, deve prendere dalla parte ove incontra per 
	primo un pezzo di maggior qualità. Nel caso le possibilità siano "pari", anche dopo l'applicazione dei criteri di cui sopra, 
	il giocatore sceglierà secondo le proprie esigenze tattiche.

NB: noterete che è presente un novo pacchetto, l'ho inserito per separare le classi usate nel calcolo delle priorità, non è una cosa definitiva
	Non ho ancora terminato questo aspetto, quindi al momento non sono usati per intero, è comunque funzionante 
	l'esempio tra i simboli di commento nel costruttore della classe Scacchiera se volete testarlo
