# Rapport SMPT - DAI

##### Laetitia Guidetti et Cédric Centeno

## Description du projet

Ce projet à pour but de réaliser un client SMTP (le protocole de communication utilisé pour transférer des emails vers les serveurs de messagerie électronique, 
détails : https://www.rfc-editor.org/rfc/rfc5321.html). Ce client utilise l'API socket pour communiquer avec un serveur SMTP.
L'application permet d'envoyer des blagues par email à une liste de victimes. L'application forme un ou plusieurs groupes de victimes, 
chaque groupe aura une victime qui sera désignée comme l'envoyeur et les autres comme des destinataires. Un email sera ensuite 
envoyé à chaque groupe, l'email fera croire que l'envoyeur est la victime désignée.
Les informations permettant la communication avec le serveur, les faux emails et les victimes sont configurables.

## MockMock 
L'application utilise actuellement un Mock SMTP Server : MockMock (https://github.com/HEIGVD-Course-DAI/MockMock).
Ce serveur permet de tester le fonctionnement de notre client. Les emails qui atteignent le serveur ne sont pas envoyés 
aux clients. Il permet de voir à quoi les emails fournis au serveur via le client ressemble via une interface Web.
Par conséquent, il s'agit uniquement d'un serveur de test et non d'envoi. 

## Instructions pour lancer le faux serveur SMTP

## Configuration et lancement du client

Plusieurs options sont paramétrables, tous les fichiers de configuration se situe dans le dossier SMTP/src/main/resources.

Le fichier config.properties contient les paramètres globaux et ceux du serveur. Chaque paramètre y est stocké comme une paire composée 
d'une clé (le nom du paramètre) et de sa valeur. Par conséquent, seul la partie située après le = est à modifier.\
La ligne host permet d'indiquer l'adresse IP du serveur.\
La ligne port permet d'indiquer le port sur lequel écoute le serveur.\
La ligne nbGroupe permet d'indiquer le nombre de groupes de victimes que l'on souhaite créer.\
Exemple de contenu conforme :

> host=localhost\
> port=25\
> nbGroupe=5

Le fichier address.txt permet d'indiquer toutes les victimes. Il doit n'y avoir qu'une adresse par ligne. Si une adresse 
est invalide, cela provoquera une erreur lors de l'exécution du programme. Il doit au minimum avoir 3 fois plus d'adresses 
que le nombre de groupes souhaité (minimum 1 envoyeur et 2 destinataires). Les groupes sont formés de manière aléatoire.\
Exemple de contenu conforme :

> mario.amos@heig-vd.ch\
> tim.ernst@heig-vd.ch\
> cedric.centeno@heig-vd.ch\
> michael.gogniat@heig-vd.ch

Le fichier fakeEmail.txt permet de rentrer les faux emails. Chaque email doit être séparé par le charactère "#" qui doit 
être seul sur une ligne. Chaque début d'email doit commencer par "Subject:" puis l'objet de l'email, laissez ensuite une ligne 
vide, puis écriviez le contenu du mail. Le contenu du mail est encodé en UTF-8. Il peut y avoir plus ou moins de faux mails que de 
nombre de groupes (si plus : certains textes ne sont pas envoyés, si moins : certains textes sont envoyés plusieurs fois).
Le faux mail envoyé à un groupe est aléatoire.\
Exemple de contenu conforme :

> Subject: Mon Dieu
>
> Bonjour cher camarade,
>
> J'ai découvert\
> Suite du mail ...\
> #\
> Subject: LA VERITE
> 
> Contenu du mail ...

### A COMPLETER, lancement du client commande

## Implémentation

Le code est réparti en 3 paquetages : data, configuration et smpt.\
Le premier data est chargé de stocker via diverses classes toutes les informations obtenues via le paquetage configuration.
- La classe **ServerInfo** qui permet de stocker les informations permettant de communiquer avec le serveur SMTP
- La classe **EmailGroup** qui permet de stocker les groupes d'adresses. Elle y a toujours un envoyeur et au minimum 2 destinataires
- La classe **Email** qui permet de stocker toutes les informations d'un email. Elle contient un objet EmailGroup et le contenu de l'email à envoyer
Le deuxième configuration permet de lire les ressources et de vérifier la conformité de toutes les informations.
- La classe **DataReader** permet de lire les fichiers txt selon le format voulu (address.txt et fakeEmail.txt)
- La classe **Config** récupère les informations de config.properties et permet de créer un objet ServerInfo. Elle utilise  
DataReader pour récupérer les adresse emails qu'elle place dans des objets EmailGroupe. Elle vérifie que chaque adresse soit valide et
que le nombre total d'adresses soit suffisant pour le nombre de groupes spécifié. Elle récupère via cette classe également les contenus des emails 
ce qui lui permet de créer les objets Email.
Le dernière smtp utilise les objets créés plutôt pour établir une communication avec le serveur SMTP et de lui transmettre les faux mails.
- La classe **SMTPClient** est une implémentation d'un client SMPT. Elle permet d'établir une connexion avec le serveur 
grâce au contenu d'un objet ServerInfo et d'envoyer un email grâce à un objet Email.