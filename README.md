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
git clone https://github.com/alessandroip/progetto_rpg_mdp.git
cd progetto_rpg_mdp
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

Durante la realizzazione del progetto ho utilizzato tre strumenti di AI, in momenti diversi:

* **Gemini**, all'inizio, per il ripasso dei concetti teorici e degli strumenti;
* **ChatGPT**, durante lo sviluppo, per chiarire concetti (Java, JavaFX, programmazione a oggetti,
  SOLID, Jackson, FXML e Scene Builder), discutere l'organizzazione delle classi e le alternative
  progettuali, proporre alternative e correzioni a porzioni di codice scritte da me,
  interpretare errori di compilazione ed esecuzione e supportare la stesura delle JavaDoc;
* **Claude**, nella fase finale, per la revisione del codice e della Wiki: ha segnalato difetti
  e proposto miglioramenti e correzioni.

L'ambientazione, le regole, la scelta dei protagonisti e la struttura del gioco sono state
decise da me. Ho valutato ogni proposta, scartandone alcune, e ho inserito, adattato e provato
manualmente le modifiche (compilazione, avvio, interfaccia, salvataggio e caricamento).

L'AI è stata un supporto allo studio e allo sviluppo, non un sostituto
dell'attività progettuale e implementativa.

## Risorse grafiche e crediti

Le immagini dei personaggi, presenti nella cartella `src/main/resources/images/heroes/`, provengono da [Robin Hood di Kevin’s Mom’s House](https://kevins-moms-house.itch.io/robinhood), distribuito con licenza **CC0**.

Le immagini non sono state generate tramite strumenti di intelligenza artificiale.

## Documentazione

La [Wiki del progetto](https://github.com/alessandroip/progetto_rpg_mdp/wiki) descrive le funzionalità, l’organizzazione delle classi, il combattimento, la persistenza e i meccanismi di estensione.