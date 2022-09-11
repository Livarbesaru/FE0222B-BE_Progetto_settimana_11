# FE0222B-BE_Progetto_settimana_11

FE0222B-BE_Progetto_settimana_11
Il Progetto e' stato fatto utilizzando spring

Per effettuare l'aggiunta di un utente bisogna passare -username -password -email -ed i ruoli come un set di stringhe, sceglibile tra user ed admin

Al momento swagger risulta essere inaccessibile per via di un problema con le autorizzazioni

l'aggiunta di category ha bisogno di -name

l'aggiunta di author ha bisogno di -name -surname

l'aggiunta di book ha bisogno di -name, -un set di autori(se i valori passati risultano non esistenti nel database vengono aggiunti evitando duplicati), -un set di categorie(se i valori passati risultano non esistenti nel database vengono aggiunti evitando duplicati),-un prezzo, la data e' opzionale e deve essere definita con il formato yyyy-mm-gg

la modifica di book,author e category ha bisogno di -id
