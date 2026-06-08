import extensions.File;
import extensions.CSVFile;

class Jeu extends Program {
    final int COL_QUESTION = 1;
    final int COL_REPONSE_CORRECTE = 2;
    final int COL_OPTION_A = 3;
    final int COL_OPTION_B = 4;
    final int COL_OPTION_C = 5;
    final int COL_OPTION_D = 6;
    boolean qcm(String fichierCSV, String nomTheme){

        CSVFile donnees= loadCSV(fichierCSV,';');

        // choisir une question aléatoire (on saute l'en-tête)
        int ligne = 1 + (int)(random() * (rowCount(donnees) - 1));

        println("\n Question de " + nomTheme + " :");
        println(getCell(donnees, ligne, COL_QUESTION));


        String res="";
        println("A:"+getCell(donnees,ligne,COL_OPTION_A));
        println("B:"+getCell(donnees,ligne,COL_OPTION_B));
        println("C:"+getCell(donnees,ligne,COL_OPTION_C));
        println("D:"+getCell(donnees,ligne,COL_OPTION_D));

        print("Votre réponse (a/b/c/d) : ");
        res = toLowerCase(readString());
        if(!equals(res,"a") && !equals(res,"b") && !equals(res,"c") && !equals(res,"d")){
            println(" Réponse invalide");
            print("Veillez entrez un nouveau index : ");
            res=toLowerCase(readString());
        }
        if (equals(res, getCell(donnees, ligne, COL_REPONSE_CORRECTE))) {
            return true;
        } else {
            println(" Mauvaise réponse");
            return false;
        }
        }

    String toString(Carte c){ //to Sting de carte
        return c.couleur + " " + c.symbole;
    }
    void testToStringCarte(){
        Carte c =new Carte();
        c.couleur=Couleur.ROUGE;
        c.symbole=Symbole.UN;
     assertEquals("ROUGE UN",toString(c));
    }

    Joueur newJoueur (String nom){
        Joueur j = new Joueur();
        j.nom = nom;
        j.deck = new Carte[50];
        j.carte_restante = 0;
        j.JoueActuellement = false;
        return j;
    }
    void testNewJoueur(){
        Carte [] c= new Carte[50];
        Joueur J=newJoueur("Test");
        assertEquals(J.nom,"Test");
        assertEquals(50,length(J.deck));
        assertEquals(0,J.carte_restante);
        assertFalse(J.JoueActuellement);
        
    }

    Carte[] newPaquet() {
        Couleur[] couleurs = new Couleur[]{
        Couleur.ROUGE, Couleur.BLEU,
        Couleur.VERT, Couleur.JAUNE
        };
        Symbole[] symboles = new Symbole[]{
            Symbole.ZERO, Symbole.UN, Symbole.DEUX,
            Symbole.TROIS, Symbole.QUATRE,
            Symbole.CINQ, Symbole.SIX,
            Symbole.SEPT, Symbole.HUIT,
            Symbole.NEUF
        };

        // taille maximale du paquet
        Carte[] paquet = new Carte[40]; 
        int idx = 0;
        for (int i = 0; i < length(couleurs); i++){
            for (int j = 0; j < length(symboles); j++){
                Carte c = new Carte();
                c.couleur = couleurs[i];
                c.symbole = symboles[j];
                paquet[idx] = c;
                idx++;
            }
        }
        return paquet;
    }
    void testNewPaquet(){
       Carte[] c = newPaquet();
       assertEquals(40,length(c));

    }

    String toStringPaquet(Carte[] paquet) {
        String res = "";

        for (int i = 0; i < length(paquet); i++) {
            if (paquet[i] != null) {
                res = res + "[" + paquet[i].couleur + " " + paquet[i].symbole + "] ";
            }
        }

        return res;
    }
    void testToStringPaquet(){
        Carte c= new Carte();
        c.symbole=Symbole.UN;
        c.couleur=Couleur.ROUGE;
        Carte[]cp=new Carte[]{c};
        assertEquals("[ROUGE UN] ",toStringPaquet(cp));
    }

    Carte tirerCarteAleatoire(Carte[] paquet) {//Non testable car random 
        int idx;

       
        do {
            idx = (int)(random() * length(paquet));
        } while (paquet[idx] == null);

        Carte c = paquet[idx];
        return c;
    }

    void donnerCarte(Joueur j, Carte c){
        j.deck[j.carte_restante] = c;
        j.carte_restante++;
    }

    void distribuer(Joueur j, Carte[] paquet){
        for (int i = 0; i < 7; i++) {
            Carte c = tirerCarteAleatoire(paquet);
            donnerCarte(j, c);
    }

    }

     String affichageCouleur(Couleur c) {
        if (c == Couleur.ROUGE) return "ROUGE";
        if (c == Couleur.BLEU) return "BLEU";
        if (c == Couleur.VERT) return "VERT";
        return "JAUNE";
    }
    void testAffichageCouleur(){
        assertEquals("ROUGE",affichageCouleur(Couleur.ROUGE));
        assertEquals("VERT",affichageCouleur(Couleur.VERT));
        assertEquals("BLEU",affichageCouleur(Couleur.BLEU));
        assertEquals("JAUNE",affichageCouleur(Couleur.JAUNE));
    }
    String affichageSymbole(Symbole s) {
        if (s == Symbole.ZERO) return "0";
        if (s == Symbole.UN) return "1";
        if (s == Symbole.DEUX) return "2";
        if (s == Symbole.TROIS) return "3";
        if (s == Symbole.QUATRE) return "4";
        if (s == Symbole.CINQ) return "5";
        if (s == Symbole.SIX) return "6";
        if (s == Symbole.SEPT) return "7";
        if (s == Symbole.HUIT) return "8";
        return "9";
    }
    void testAffichageSymbole(){
        assertEquals("0",affichageSymbole(Symbole.ZERO));
        assertEquals("1",affichageSymbole(Symbole.UN));
        assertEquals("2",affichageSymbole(Symbole.DEUX));
        assertEquals("3",affichageSymbole(Symbole.TROIS));
        assertEquals("4",affichageSymbole(Symbole.QUATRE));
        assertEquals("5",affichageSymbole(Symbole.CINQ));
        assertEquals("6",affichageSymbole(Symbole.SIX));
        assertEquals("7",affichageSymbole(Symbole.SEPT));
        assertEquals("8",affichageSymbole(Symbole.HUIT));
        assertEquals("9",affichageSymbole(Symbole.NEUF));


    }

    String mettreCentre(String txt, int largeur) {
        if (length(txt) >= largeur) return txt;

        int espaces = largeur - length(txt);
        int gauche = espaces / 2;
        int droite = espaces - gauche;

        String res = "";
        for (int i = 0; i < gauche; i++) res += " ";
        res += txt;
        for (int i = 0; i < droite; i++) res += " ";
        return res;
    }
    void testMettreCentre(){
        assertEquals(" test ",mettreCentre("test",6));
    }

    String ligneHautCarte() {
        return "┌─────┐";
    }
    void testLigneHaut(){
        assertEquals(ligneHautCarte(),"┌─────┐");
    }

    String ligneBasCarte() {
        return "└─────┘";
    }
    void testLigneBas(){
        assertEquals(ligneBasCarte(),"└─────┘");
    }

    String ligneCouleurCarte(Carte c) {
        return "│" + mettreCentre(affichageCouleur(c.couleur), 5) + "│";
    }
    void testLigneCouleurCarte(){
        Carte c= new Carte();
        c.couleur=Couleur.ROUGE;
        assertEquals("│ROUGE│","│"+mettreCentre(affichageCouleur(c.couleur),5)+"│");
    }

    String ligneSymboleCarte(Carte c) {
        return "│" + mettreCentre(affichageSymbole(c.symbole), 5) + "│";
    }
       void testLigneSymboleCarte(){
        Carte c= new Carte();
        c.symbole=Symbole.UN;
        assertEquals("│  1  │","│"+mettreCentre(affichageSymbole(c.symbole),5)+"│");
    }


    void afficherCarteUNO(Carte c) {
        println("┌─────┐");
        println("│" + mettreCentre(affichageCouleur(c.couleur), 5) + "│");
        println("│" + mettreCentre(affichageSymbole(c.symbole), 5) + "│");
        println("└─────┘");
    }

    void afficherMainAvecIndex(Joueur joueur) {
        // Ligne des index
        for (int i = 0; i < joueur.carte_restante; i++) {
            print(" " + mettreCentre("" + i, 5) + "  ");
        }
        println();

        // Haut des cartes
        for (int i = 0; i < joueur.carte_restante; i++) {
            print(ligneHautCarte() + " ");
        }
        println();

        // Couleurs
        for (int i = 0; i < joueur.carte_restante; i++) {
            print(ligneCouleurCarte(joueur.deck[i]) + " ");
        }
        println();

        // Symboles
        for (int i = 0; i < joueur.carte_restante; i++) {
            print(ligneSymboleCarte(joueur.deck[i]) + " ");
        }
        println();

        // Bas des cartes
        for (int i = 0; i < joueur.carte_restante; i++) {
            print(ligneBasCarte() + " ");
        }
        println();
    }


    String toStringMain(Joueur j) {
        String res = "";

        for (int i = 0; i < j.carte_restante; i++) {
            res = res + "[" + toString(j.deck[i]) + "] ";
        }

        return res;
    }
    void testToStringMain(){
        Joueur j = new Joueur();
         Carte c = new Carte();
        c.couleur = Couleur.ROUGE;
        c.symbole= Symbole.UN;
        j.deck= new Carte[]{c};
        j.carte_restante=1;
        assertEquals("["+toString(j.deck[0])+"] ",toStringMain(j));
    }

    String toStringJoueur(Joueur j) {
        String res = "";

        res = res + "Nom : " + j.nom + "\n";
        res = res + "Cartes : \n";
        res = res + toStringMain(j);

        res = res + "\nNombre de cartes : " + j.carte_restante;
        res = res + "\nJoue actuellement : " + j.JoueActuellement;

        return res;
    }
    void testToStringJoueur(){
        Joueur j = new Joueur();
        j.nom="test";
        Carte c = new Carte();
        c.couleur = Couleur.ROUGE;
        c.symbole= Symbole.UN;
        j.deck= new Carte[]{c};
        j.carte_restante=1;
        j.JoueActuellement=false;
        assertEquals("Nom : test"+"\n"+"Cartes : "+"\n"+"["+toString(j.deck[0])+"] "+"\nNombre de cartes : 1"+"\nJoue actuellement : false",toStringJoueur(j));
    }


    boolean estValide(Carte carte, Carte carteCentre) { //ajouter test 
        return carte.couleur == carteCentre.couleur|| carte.symbole == carteCentre.symbole;
    }
    void testEstValide(){ //test pour estValide
        Carte c = new Carte();
        c.couleur = Couleur.ROUGE;
        c.symbole= Symbole.UN;
        Carte ct= new Carte();
        ct.couleur= Couleur.ROUGE;
        ct.symbole= Symbole.UN;
        Carte fausse= new Carte();
        fausse.couleur= Couleur.BLEU;
        fausse.symbole=Symbole.ZERO;

        assertTrue(estValide(c,ct));
        assertFalse(estValide(fausse,ct));
    }
    

    boolean jouerCarte(Joueur j, int idxCarte, Carte[] carteCentre) {//test impossible car intéraction
        boolean test=false; 

        if (idxCarte < 0 || idxCarte >= j.carte_restante) {
            return false; 
        }

        Carte c = j.deck[idxCarte];


        if (!estValide(c, carteCentre[0])) {
            return false; 
        }
        if (c.couleur == Couleur.BLEU) {
            test=qcm("q.csv", "Géographie") ;
        }
        if (c.couleur == Couleur.JAUNE) {
            test=qcm("question_histoire.csv", "Histoire");
        }
        if (c.couleur == Couleur.ROUGE) {
            test=qcm("question_math.csv", "Maths");
        }
        if (c.couleur == Couleur.VERT) {
            test=qcm("question_art.csv", "Arts");
        }
        carteCentre[0] = c;
        j.deck[idxCarte] = j.deck[j.carte_restante - 1];
        j.deck[j.carte_restante - 1] = null;
        j.carte_restante--;

        return test;
    }



    boolean peutJouer(Joueur j, Carte carteCentre) { //ajouter test
        for (int i = 0; i < j.carte_restante; i++) {
            if (estValide(j.deck[i], carteCentre)) {
                return true;
            }
        }
        return false;
    }
    void testPeutJouer(){
        Joueur J= new Joueur();
        Carte c1= new Carte();
        c1.couleur= Couleur.ROUGE;
        c1.symbole= Symbole.UN;
        J.deck= new Carte[]{c1};
        J.carte_restante=1;
        Carte c= new Carte();
        c.couleur= Couleur.ROUGE;
        c.symbole= Symbole.UN;
        assertTrue(peutJouer(J,c));
    }

    void afficherRegles() {
        File fichier = newFile("règles.csv");
        while (ready(fichier)) {
            println(readLine(fichier));
        }
    }

    void afficherLogo() {
        File fichier = newFile("logo.txt");
        while (ready(fichier)) {
            println(readLine(fichier));
        }
    }

    void clear(){
        print("\u001B[H\u001B[2J");
    }
    void delay(){
        println("\nAppuyez sur Entrée pour continuer...");
        readString();
    }
    void jouerRobot(Joueur robot, Carte[] carteCentre, Carte[] paquet) {

        println("\n--- Tour du robot ---");
        
        for (int i = 0; i < robot.carte_restante; i++) {
            Carte c = robot.deck[i];
            if (estValide(c, carteCentre[0])) {
                carteCentre[0] = c;
                robot.deck[i] = robot.deck[robot.carte_restante - 1];
                robot.deck[robot.carte_restante - 1] = null;
                robot.carte_restante--;

                println("Le robot joue : ");
                afficherCarteUNO(c);
                return;
            }
        }

        // Si aucune carte jouable pioche
        Carte pioche = tirerCarteAleatoire(paquet);
        donnerCarte(robot, pioche);
        println("Le robot ne peut pas jouer et pioche une carte.");
    }

    void affichageDépart(){
        //Règles
        afficherRegles();
        delay();
        // Logo
        afficherLogo();


    }

    void lancerJeu() {
        affichageDépart();


        // Joueur
        print("Entrez votre prénom : ");
        String nom = readString();
        Joueur joueur = newJoueur(nom);

        // Joueur robot
        Joueur robot=newJoueur("Robot");

        // Paquet
        Carte[] paquet = newPaquet();

        // Distribution
        distribuer(joueur, paquet);
        distribuer(robot, paquet);

        // Carte au centre
        Carte[] carteCentre = new Carte[1];
        carteCentre[0] = tirerCarteAleatoire(paquet);

        // Affichage de départ 

        println("\n========================");
        println("Carte de départ : ");
        afficherCarteUNO(carteCentre[0]);
        println("========================");



    // Boucle de jeu
        while (joueur.carte_restante > 0 && robot.carte_restante > 0) {

        
            println("------------------------");
            println("Carte au centre :");
            afficherCarteUNO(carteCentre[0]);
            println("------------------------");

            println("Votre main :");
            afficherMainAvecIndex(joueur);

            if (peutJouer(joueur, carteCentre[0])) {

                print("Choisissez l'index de la carte à jouer : ");
                int idx = readInt();

                if (jouerCarte(joueur, idx, carteCentre)) {
                    clear();
                    println("\nCARTE JOUEE !");
                } else {
                    clear();
                    println("\nCARTE INVALIDE,TOUR PERDU.");
                }

            } else {
                Carte c = tirerCarteAleatoire(paquet);
                donnerCarte(joueur, c);
                println("\nAUCUNE CARTE JOUABLE, VOUS PIOCHEZ.");
            }

            
            if (joueur.carte_restante == 0) {
                break;
            }

            
            jouerRobot(robot, carteCentre, paquet);
            //delay();
            println("Carte restante de l'adversaire : "+robot.carte_restante);
            
        }

        if (joueur.carte_restante == 0) {
        println("\n BRAVO " + joueur.nom + ", vous avez gagné ! 🎉");
        } else {
            println("\n Le robot a gagné 🤖");
        }
    }


    void algorithm() {
        lancerJeu();
    }
}