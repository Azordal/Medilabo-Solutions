const database = db.getSiblingDB("medilabo_notes");

if (database.notes.countDocuments({}) === 0) {
    database.notes.insertMany([
        {
            patientId: NumberLong(1),
            content:
                "Le patient déclare qu'il 'se sent très bien'\n" +
                "Poids égal ou inférieur au poids recommandé",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(2),
            content:
                "Le patient déclare qu'il ressent beaucoup de stress au travail\n" +
                "Il se plaint également que son audition est anormale dernièrement",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(2),
            content:
                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois\n" +
                "Il remarque également que son audition continue d'être anormale",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(3),
            content: "Le patient déclare qu'il fume depuis peu",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(3),
            content:
                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière\n" +
                "Il se plaint également de crises d'apnée respiratoire anormales\n" +
                "Tests de laboratoire indiquant un taux de cholestérol LDL élevé",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(4),
            content:
                "Le patient déclare qu'il lui est devenu difficile de monter les escaliers\n" +
                "Il se plaint également d’être essoufflé\n" +
                "Tests de laboratoire indiquant que les anticorps sont élevés\n" +
                "Réaction aux médicaments",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(4),
            content:
                "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(4),
            content:
                "Le patient déclare avoir commencé à fumer depuis peu\n" +
                "Hémoglobine A1C supérieure au niveau recommandé",
            createdAt: new Date()
        },
        {
            patientId: NumberLong(4),
            content:
                "Taille, Poids, Cholestérol, Vertige et Réaction",
            createdAt: new Date()
        }
    ]);
}