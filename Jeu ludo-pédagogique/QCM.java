import extensions.CSVFile;

class QCM extends Program{
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
        if(res!="a" && res!="b" && res!="c" && res!="d"){
            println(" Réponse invalide");
            res=toLowerCase(readString());
            if (equals(res, getCell(donnees, ligne, COL_REPONSE_CORRECTE))) {
            return true;
        } else {
            println(" Mauvaise réponse");
            return false;
        }
        }
            return false;}

    void algorithm(){
    }
}