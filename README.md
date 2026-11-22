# 📌 Robin Hood RPG

Robin Hood RPG è un gioco di ruolo a turni sviluppato in Java con interfaccia grafica JavaFX.

Il giocatore sceglie tra Robin Hood e Little John e affronta cinque incontri contro nemici con 
caratteristiche differenti, fino allo scontro con il re. Ogni eroe possiede un’abilità e una difesa 
speciale utilizzabili una volta per incontro.

Dopo le vittorie, il giocatore dona il bottino al villaggio e riceve ricompense da conservare nell’inventario. 
È possibile salvare i progressi tra gli incontri e riprendere successivamente la partita.

## 🚀 Come eseguire il progetto

### Prerequisiti
- Java 25 (LTS)
- Gradle
### Istruzioni
```bash
git clone <https://github.com/alessandroip/progetto_rpg_mdp.git>
cd <progetto_rpg_mdp>
```
### Build del progetto
```bash
./gradlew build
```
### Esecuzione
```bash
./gradlew run
```

## 🤖 Utilizzo di strumenti di AI

Durante la realizzazione del progetto ho utilizzato **Gemini** per il ripasso iniziale dei concetti teorici e **ChatGPT** per il confronto sulle soluzioni, lo sviluppo e la documentazione.

L’AI è stata utilizzata per:

- chiarire concetti relativi a Java, JavaFX, programmazione a oggetti e principi SOLID;
- discutere l’organizzazione delle classi, le responsabilità e le possibili alternative progettuali;
- proporre alternative e correzioni a porzioni di codice scritte dall'autore, spiegandone il funzionamento;
- comprendere Jackson e supporto all'organizzazione della struttura di persistenza JSON;
- sostegno nel collegare FXML e controller e aiuto tecnico sull’utilizzo di Scene Builder per comprendere gli elementi grafici da utilizzare;
- interpretare errori di compilazione ed esecuzione;
- aiuto nella stesura e revisione dei JavaDoc e dei testi della documentazione.

Il codice è stato scritto, compreso, analizzato e testato personalmente. L'AI è stata utilizzata come supporto e
sostegno allo studio e allo sviluppo e non come sostituto dell'attività progettuale e implementativa.
## Risorse grafiche e crediti

Le immagini dei personaggi, presenti nella cartella `src/main/resources/images/heroes/`, provengono da [Robin Hood di Kevin’s Mom’s House](https://kevins-moms-house.itch.io/robinhood), distribuito con licenza **CC0**.

Le immagini non sono state generate tramite strumenti di intelligenza artificiale.

## Documentazione

La [Wiki del progetto](https://github.com/alessandroip/progetto_rpg_mdp/wiki) descrive le funzionalità, l’organizzazione delle classi, il combattimento, la persistenza e i meccanismi di estensione.