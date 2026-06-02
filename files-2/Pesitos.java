import java.util.ArrayList;
import java.util.Scanner;

/**
 * Pesitos — educación financiera para jóvenes colombianos.
 *
 * Main class. Builds the lessons and runs the menu loop.
 * ALL progress now lives inside the Student object — Pesitos just
 * tells the Student what happened and asks it what to display.
 */
public class Pesitos {

    private static Scanner input = new Scanner(System.in);

    private static ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    // The current user. Owns progress, best scores, and achievements.
    private static Student student;

    public static void main(String[] args) {
        // STEP 1: Build all the lessons.
        buildLessons();

        // STEP 2: Welcome + ask for the student's name.
        printWelcome();
        System.out.print("¿Cómo te llamas? ");
        String name = input.nextLine().trim();
        if (name.equals("")) {
            name = "Estudiante";
        }

        // STEP 3: Create the Student. We pass how many questions each lesson
        // has so the Student can size its 2D progress array correctly.
        int[] counts = new int[lessons.size()];
        for (int i = 0; i < lessons.size(); i++) {
            counts[i] = lessons.get(i).getNumberOfQuestions();
        }
        student = new Student(name, counts);
        System.out.println("\n¡Bienvenido(a), " + name + "! Empecemos.\n");

        // STEP 4: Main menu loop. Runs until the user chooses "salir" (exit).
        boolean running = true;
        while (running) {
            printMenu();
            String choice = input.nextLine().trim();

            if (choice.equalsIgnoreCase("salir") || choice.equals("0")) {
                running = false;
            } else {
                int option = parseChoice(choice);

                if (option >= 1 && option <= lessons.size()) {
                    runLesson(option - 1);
                } else if (option == lessons.size() + 1) {
                    printProgress();
                } else if (option == lessons.size() + 2) {
                    printProfile();
                } else {
                    System.out.println("\nOpción no válida. Intenta de nuevo.\n");
                }
            }
        }

        // STEP 5: Goodbye message.
        System.out.println("\n¡Gracias por usar Pesitos, " + student.getName()
            + "! Recuerda: de pesito en pesito.\n");
    }

    // ----------------------------------------------------------
    // parseChoice() — turns the menu input into an int.
    // Returns -1 if the input is not a whole number (avoids crashes).
    // ----------------------------------------------------------
    public static int parseChoice(String s) {
        if (s.length() == 0) {
            return -1;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return -1;
            }
        }
        return Integer.parseInt(s);
    }

    // ----------------------------------------------------------
    // buildLessons() — creates all lessons and their questions.
    // ----------------------------------------------------------
    public static void buildLessons() {

        // ----- LESSON 1: Impuestos (taxes) -----
        Lesson impuestos = new Lesson(
            "Impuestos",
            "Los impuestos son dinero que le pagas al gobierno. Vamos a aprender los conceptos básicos."
        );
        impuestos.addQuestion(new Question(
            "¿Qué es la declaración de renta en Colombia?",
            new String[] {
                "Un impuesto que pagan solo los ricos",
                "Un reporte anual que algunos colombianos deben hacer a la DIAN",
                "Un descuento que te hace el banco",
                "Un tipo de cuenta de ahorros"
            },
            1,
            "Correcto: es un reporte anual a la DIAN. No todos los colombianos están obligados — depende de cuánto ganas, cuánto tienes, y otros factores."
        ));
        impuestos.addQuestion(new Question(
            "¿A partir de qué edad puedes estar obligado a declarar renta en Colombia?",
            new String[] {
                "Desde los 15 años",
                "Desde los 18 años, si cumples ciertos topes de ingresos o patrimonio",
                "Solo cuando eres pensionado",
                "Nunca, si eres estudiante"
            },
            1,
            "Desde los 18 años puedes estar obligado, pero solo si tus ingresos, patrimonio, o consumos superan los topes que publica la DIAN cada año."
        ));
        impuestos.addQuestion(new Question(
            "¿Qué pasa si no declaras renta estando obligado a hacerlo?",
            new String[] {
                "Nada, no hay consecuencias",
                "Te pueden cobrar multas e intereses de mora",
                "Te quitan tu cédula",
                "Te suben los impuestos del próximo año a propósito"
            },
            1,
            "La DIAN puede imponerte multas e intereses. Entre más tarde lo hagas, más caro te sale. Por eso es importante saber si estás obligado."
        ));
        lessons.add(impuestos);

        // ----- LESSON 2: Facturas (bills) -----
        Lesson facturas = new Lesson(
            "Facturas",
            "Pagar facturas bien (y a tiempo) es una de las habilidades más importantes para un adulto joven."
        );
        facturas.addQuestion(new Question(
            "¿Qué información NO debe faltar en una factura?",
            new String[] {
                "La fecha de vencimiento",
                "El nombre del primo del dueño",
                "El color favorito del cliente",
                "La temperatura del día"
            },
            0,
            "La fecha de vencimiento es clave. Si no la revisas, puedes terminar pagando tarde e incurriendo en intereses."
        ));
        facturas.addQuestion(new Question(
            "Si pagas una factura después de la fecha de vencimiento, ¿qué es lo más común que pase?",
            new String[] {
                "Nada, es igual a pagar a tiempo",
                "Te cobran intereses de mora",
                "Te regalan un descuento",
                "Te cambian de proveedor automáticamente"
            },
            1,
            "Casi siempre te cobran intereses de mora. En servicios como luz o agua, pagar tarde varias veces puede costar más de lo que crees."
        ));
        facturas.addQuestion(new Question(
            "¿Cuál es una buena costumbre para no olvidar pagar facturas?",
            new String[] {
                "Pagar solo cuando te llamen a cobrar",
                "Programar pagos automáticos o recordatorios en el celular",
                "Ignorarlas hasta que te corten el servicio",
                "Esperar a fin de año y pagar todo junto"
            },
            1,
            "Los pagos automáticos o recordatorios en el celular son la manera más fácil de no olvidar. La mayoría de bancos colombianos lo permiten gratis."
        ));
        lessons.add(facturas);

        // ----- LESSON 3: Cuenta bancaria (bank account) -----
        Lesson cuenta = new Lesson(
            "Cuenta Bancaria",
            "Abrir y manejar una cuenta bancaria es el primer paso para tener control de tu plata."
        );
        cuenta.addQuestion(new Question(
            "¿Cuál es la diferencia principal entre una cuenta de ahorros y una corriente?",
            new String[] {
                "No hay ninguna diferencia",
                "La de ahorros genera intereses; la corriente generalmente no",
                "La corriente es solo para adultos mayores",
                "La de ahorros es ilegal antes de los 30"
            },
            1,
            "La cuenta de ahorros te paga un pequeño interés por tener tu plata ahí. La corriente se usa más para transacciones frecuentes."
        ));
        cuenta.addQuestion(new Question(
            "En Colombia, apps como Nequi y Daviplata son:",
            new String[] {
                "Juegos de celular",
                "Billeteras digitales donde puedes guardar y enviar plata",
                "Servicios de televisión",
                "Redes sociales"
            },
            1,
            "Son billeteras digitales. Te dan un número de cuenta, puedes recibir plata, enviar, y pagar en muchas tiendas sin tener una cuenta bancaria tradicional."
        ));
        cuenta.addQuestion(new Question(
            "¿Qué es una tarjeta débito?",
            new String[] {
                "Una tarjeta que usa plata que SÍ tienes en tu cuenta",
                "Una tarjeta que te presta plata del banco",
                "Una tarjeta solo para viajes",
                "Una tarjeta sin ningún uso real"
            },
            0,
            "La tarjeta débito usa tu propia plata. Es diferente a la tarjeta de crédito, que te presta plata que luego debes devolver."
        ));
        lessons.add(cuenta);

        // ----- LESSON 4: Presupuesto (budget) -----
        Lesson presupuesto = new Lesson(
            "Presupuesto",
            "Un presupuesto es un plan para tu plata. Te ayuda a decidir ANTES de gastar, no después."
        );
        presupuesto.addQuestion(new Question(
            "Si ganas $1.000.000 al mes, ¿cuánto deberías intentar ahorrar como mínimo según la regla 50/30/20?",
            new String[] {
                "$0",
                "$50.000",
                "$200.000 (el 20%)",
                "$900.000"
            },
            2,
            "La regla 50/30/20 dice: 50% necesidades, 30% gustos, 20% ahorro. Sobre $1.000.000, eso es $200.000 de ahorro."
        ));
        presupuesto.addQuestion(new Question(
            "¿Cuál de estos es un GASTO FIJO?",
            new String[] {
                "Un helado ocasional",
                "El arriendo mensual",
                "Un regalo sorpresa",
                "Una multa"
            },
            1,
            "El arriendo es fijo: se repite cada mes, por el mismo valor. Los gastos fijos son los primeros que debes identificar en un presupuesto."
        ));
        presupuesto.addQuestion(new Question(
            "¿Qué es más importante al empezar a presupuestar?",
            new String[] {
                "Escribir todo lo que gastas durante al menos una semana",
                "Esperar a ganar más plata",
                "Ponerte una meta imposible",
                "Comparar tu sueldo con el de tus amigos"
            },
            0,
            "Antes de planear, hay que observar. Si no sabes a dónde se va tu plata, no puedes manejarla. Una semana de registro ya te enseña muchísimo."
        ));
        lessons.add(presupuesto);

        // ----- LESSON 5: Intereses (interest) -----
        Lesson intereses = new Lesson(
            "Intereses",
            "El interés es el costo (o ganancia) por usar plata. Entenderlo te puede salvar de deudas trampa."
        );
        intereses.addQuestion(new Question(
            "Si sacas un préstamo al 30% anual y no pagas nada, ¿aproximadamente en cuántos años se duplica lo que debes?",
            new String[] {
                "En 30 años",
                "En 10 años",
                "Aproximadamente en 2.5 años",
                "Nunca"
            },
            2,
            "Regla del 72: divide 72 entre la tasa de interés. 72 / 30 ≈ 2.4 años. Por eso las deudas al 30% son tan peligrosas."
        ));
        intereses.addQuestion(new Question(
            "El interés COMPUESTO es mejor para ti cuando:",
            new String[] {
                "Estás endeudado",
                "Estás ahorrando o invirtiendo",
                "Pagas impuestos",
                "Compras ropa"
            },
            1,
            "Cuando ahorras, el interés compuesto es tu amigo: generas interés sobre el interés. Cuando debes, es tu enemigo: la deuda crece sobre la deuda."
        ));
        intereses.addQuestion(new Question(
            "Una compra a \"cero interés\" en 12 cuotas, ¿siempre es realmente gratis?",
            new String[] {
                "Sí, siempre",
                "No necesariamente — a veces hay cuotas de manejo u otros cargos",
                "Solo si pagas en efectivo",
                "Solo si compras en diciembre"
            },
            1,
            "Muchas veces el \"sin interés\" viene con cuotas de manejo, seguros obligatorios, o precios inflados. Siempre pregunta cuál es el COSTO TOTAL al final."
        ));
        lessons.add(intereses);

        // ----- LESSON 6: Crédito (credit) -----
        Lesson credito = new Lesson(
            "Crédito",
            "El crédito te deja usar plata que aún no tienes. Bien usado es una herramienta; mal usado, una trampa."
        );
        credito.addQuestion(new Question(
            "¿Qué es una tarjeta de crédito?",
            new String[] {
                "Una tarjeta donde el banco te presta plata que luego debes devolver",
                "Una tarjeta que usa la plata que ya tienes ahorrada",
                "Una tarjeta solo para comprar en el exterior",
                "Una tarjeta que regala plata gratis cada mes"
            },
            0,
            "Con una tarjeta de crédito el banco te presta plata. Si no pagas a tiempo, te cobra intereses, que en Colombia suelen ser de los más altos del mercado."
        ));
        credito.addQuestion(new Question(
            "Si cada mes solo pagas el \"pago mínimo\" de tu tarjeta de crédito, ¿qué pasa?",
            new String[] {
                "La deuda desaparece más rápido",
                "Sigues pagando intereses sobre el saldo y la deuda puede tardar años en bajar",
                "El banco te baja la tasa de interés",
                "No pasa nada, es lo mismo que pagar todo"
            },
            1,
            "El pago mínimo cubre casi solo los intereses. Pagando solo el mínimo, una deuda pequeña puede tardar años en desaparecer y costar mucho más."
        ));
        credito.addQuestion(new Question(
            "En Colombia, ¿qué es el historial crediticio (por ejemplo en Datacrédito)?",
            new String[] {
                "Una lista negra de la que nunca puedes salir",
                "Un registro de cómo pagas tus deudas, que consultan los bancos",
                "El saldo de tu cuenta de ahorros",
                "Un impuesto sobre los préstamos"
            },
            1,
            "Tu historial guarda cómo has pagado. Un buen historial te abre puertas (créditos, arriendos); pagar tarde lo daña y puede costarte oportunidades."
        ));
        lessons.add(credito);

        // ----- LESSON 7: Ahorro e Inversión (saving & investing) -----
        Lesson inversion = new Lesson(
            "Ahorro e Inversión",
            "Ahorrar es guardar; invertir es poner esa plata a trabajar. Aquí ves la diferencia y los riesgos."
        );
        inversion.addQuestion(new Question(
            "¿Para qué sirve un \"fondo de emergencia\"?",
            new String[] {
                "Para comprar lujos cuando se te antojen",
                "Para cubrir gastos inesperados sin tener que endeudarte",
                "Para prestarle a tus amigos",
                "Para pagar impuestos del próximo año"
            },
            1,
            "Un fondo de emergencia (idealmente de 3 a 6 meses de gastos) te protege de imprevistos como una enfermedad o quedarte sin trabajo, sin caer en deudas."
        ));
        inversion.addQuestion(new Question(
            "En general, ¿qué relación hay entre el riesgo y la rentabilidad de una inversión?",
            new String[] {
                "No tienen ninguna relación",
                "A mayor rentabilidad prometida, normalmente mayor riesgo",
                "A menor riesgo, siempre mayor ganancia",
                "El riesgo no existe si invierte mucha gente"
            },
            1,
            "Si algo promete ganancias enormes \"sin riesgo\", desconfía. Casi siempre, más rentabilidad significa más riesgo de perder."
        ));
        inversion.addQuestion(new Question(
            "En Colombia, ¿qué es un CDT (Certificado de Depósito a Término)?",
            new String[] {
                "Un producto donde dejas tu plata un tiempo fijo a cambio de un interés",
                "Una tarjeta de crédito sin cupo",
                "Un impuesto sobre el ahorro",
                "Un préstamo que te hace el gobierno"
            },
            0,
            "En un CDT le prestas tu plata al banco por un plazo fijo (por ejemplo 90 días) y él te paga intereses. Es de bajo riesgo, pero no puedes retirarla antes sin penalidad."
        ));
        lessons.add(inversion);

        // ----- LESSON 8: Estafas Financieras (financial scams) -----
        Lesson estafas = new Lesson(
            "Estafas Financieras",
            "Aprender a detectar fraudes puede salvarte de perder los ahorros de meses en un solo día."
        );
        estafas.addQuestion(new Question(
            "Te llaman diciendo que ganaste un premio, pero primero debes pagar para reclamarlo. ¿Qué es esto probablemente?",
            new String[] {
                "Una promoción legítima de un banco",
                "Una estafa: si tienes que pagar para reclamar un premio, casi nunca es real",
                "Una oferta del gobierno",
                "Un descuento por ser buen cliente"
            },
            1,
            "Regla simple: un premio real no te pide pagar por adelantado. Pedir plata para \"liberar\" un premio es una de las estafas más comunes."
        ));
        estafas.addQuestion(new Question(
            "¿Qué es un esquema piramidal (una \"pirámide\")?",
            new String[] {
                "Una inversión segura respaldada por el gobierno",
                "Un fraude donde pagan a los antiguos con la plata de los nuevos, hasta que colapsa",
                "Un tipo de cuenta de ahorros",
                "Una forma legal de invertir en la bolsa"
            },
            1,
            "Las pirámides prometen ganancias rápidas, pero solo funcionan mientras entre gente nueva. Cuando deja de entrar plata, colapsan y la mayoría pierde todo."
        ));
        estafas.addQuestion(new Question(
            "Recibes un mensaje urgente que pide tu clave o datos bancarios para \"no bloquear tu cuenta\". ¿Qué haces?",
            new String[] {
                "Mandas los datos rápido para no perder la cuenta",
                "Desconfías y NO compartes nada: los bancos no piden claves por mensaje o llamada",
                "Respondes con tu número de cédula primero",
                "Reenvías el mensaje a tus contactos"
            },
            1,
            "Eso es phishing. Ningún banco serio te pide la clave por SMS, WhatsApp o llamada. Ante la duda, cuelga y llama tú al número oficial del banco."
        ));
        lessons.add(estafas);
    }

    // ----------------------------------------------------------
    // printWelcome() — shows the splash screen.
    // ----------------------------------------------------------
    public static void printWelcome() {
        System.out.println();
        System.out.println("===========================================");
        System.out.println("            P E S I T O S                  ");
        System.out.println("   Educación financiera, un pesito         ");
        System.out.println("   a la vez.                                ");
        System.out.println("===========================================");
        System.out.println();
    }

    // ----------------------------------------------------------
    // printMenu() — shows the main menu (lessons + progress + profile).
    // ----------------------------------------------------------
    public static void printMenu() {
        System.out.println("---- MENÚ PRINCIPAL ----");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).getTitle());
        }
        System.out.println((lessons.size() + 1) + ". Ver mi progreso");
        System.out.println((lessons.size() + 2) + ". Ver mi perfil");
        System.out.println("0. Salir");
        System.out.print("\nEscoge una opción: ");
    }

    // ----------------------------------------------------------
    // runLesson(int) — runs a single lesson quiz from start to finish.
    // It now reports each answer to the Student object.
    // ----------------------------------------------------------
    public static void runLesson(int lessonIndex) {
        if (lessonIndex < 0 || lessonIndex >= lessons.size()) {
            System.out.println("Lección no encontrada.");
            return;
        }

        Lesson lesson = lessons.get(lessonIndex);

        // Clear the previous attempt so "progreso" reflects this run.
        student.resetLessonProgress(lessonIndex);

        System.out.println();
        System.out.println("====== " + lesson.getTitle().toUpperCase() + " ======");
        System.out.println(lesson.getIntro());
        System.out.println();

        int correctCount = 0;
        int totalQuestions = lesson.getNumberOfQuestions();

        for (int q = 0; q < totalQuestions; q++) {
            Question question = lesson.getQuestion(q);

            System.out.println("Pregunta " + (q + 1) + " de " + totalQuestions + ":");
            System.out.println(question.getQuestionText());

            String[] opts = question.getOptions();
            String[] letters = {"A", "B", "C", "D"};
            for (int i = 0; i < opts.length; i++) {
                System.out.println("  " + letters[i] + ") " + opts[i]);
            }

            int userAnswer = askForAnswer();
            boolean correct = question.isCorrect(userAnswer);

            // Tell the Student how this question went.
            student.recordAnswer(lessonIndex, q, correct);

            if (correct) {
                correctCount++;
                System.out.println("\n¡Correcto! \u2713");
            } else {
                System.out.println("\nIncorrecto. \u2717");
                System.out.println("La respuesta correcta era: "
                    + letters[question.getCorrectIndex()] + ") "
                    + opts[question.getCorrectIndex()]);
            }

            System.out.println("\nExplicación: " + question.getExplanation());
            System.out.println();
        }

        // Save the overall result (keeps best score, unlocks badges).
        student.recordResult(lessonIndex, correctCount, totalQuestions);

        // End-of-lesson summary.
        System.out.println("------ RESULTADO ------");
        System.out.println("Acertaste " + correctCount + " de " + totalQuestions + " preguntas.");

        double percentage = (double) correctCount / totalQuestions * 100.0;
        System.out.println("Porcentaje: " + Math.round(percentage) + "%");

        if (percentage >= 80) {
            System.out.println("¡Excelente! Dominas este tema.");
        } else if (percentage >= 50) {
            System.out.println("Vas bien. Repasa la lección y vuelve a intentarlo.");
        } else {
            System.out.println("No te preocupes. Aprender toma tiempo. Revisa las explicaciones.");
        }
        System.out.println();
    }

    // ----------------------------------------------------------
    // askForAnswer() — reads A/B/C/D (or 1-4) and returns 0..3.
    // ----------------------------------------------------------
    public static int askForAnswer() {
        while (true) {
            System.out.print("Tu respuesta (A/B/C/D): ");
            String answer = input.nextLine().trim().toLowerCase();

            if (answer.equals("a") || answer.equals("1")) return 0;
            if (answer.equals("b") || answer.equals("2")) return 1;
            if (answer.equals("c") || answer.equals("3")) return 2;
            if (answer.equals("d") || answer.equals("4")) return 3;

            System.out.println("Respuesta no válida. Escribe A, B, C, o D.");
        }
    }

    // ----------------------------------------------------------
    // printProgress() — most recent attempt per question.
    // Reads everything from the Student object now.
    // ----------------------------------------------------------
    public static void printProgress() {
        System.out.println();
        System.out.println("====== TU PROGRESO (último intento) ======");

        int totalAnswered = 0;
        int totalCorrect = 0;

        for (int i = 0; i < lessons.size(); i++) {
            String title = lessons.get(i).getTitle();

            int correctInLesson = student.getCorrectInLesson(i);
            int answeredInLesson = student.getAnsweredInLesson(i);
            int questionCount = student.getLessonQuestionCount(i);

            totalCorrect += correctInLesson;
            totalAnswered += answeredInLesson;

            if (answeredInLesson == 0) {
                System.out.println(title + ": (sin empezar)");
            } else {
                System.out.println(title + ": "
                    + correctInLesson + "/" + questionCount
                    + " correctas  [" + buildProgressBar(correctInLesson, questionCount) + "]");
            }
        }

        System.out.println("-----------------------------");
        if (totalAnswered == 0) {
            System.out.println("Aún no has contestado ninguna pregunta. ¡Anímate a empezar!");
        } else {
            double overall = (double) totalCorrect / totalAnswered * 100.0;
            System.out.println("Total: " + totalCorrect + " de " + totalAnswered
                + " (" + Math.round(overall) + "%)");
        }
        System.out.println();
    }

    // ----------------------------------------------------------
    // printProfile() — the student's overall profile and achievements.
    // ----------------------------------------------------------
    public static void printProfile() {
        System.out.println();
        System.out.println("====== PERFIL DE " + student.getName().toUpperCase() + " ======");
        System.out.println("Lecciones completadas: "
            + student.getLessonsCompleted() + " de " + student.getTotalLessons());
        System.out.println("Mejor desempeño general: " + student.getOverallPercentage() + "%");
        System.out.println();

        System.out.println("Mejores puntajes por lección:");
        for (int i = 0; i < lessons.size(); i++) {
            String title = lessons.get(i).getTitle();
            if (student.hasCompleted(i)) {
                System.out.println("  " + title + ": "
                    + student.getBestCorrect(i) + "/" + student.getLessonTotal(i));
            } else {
                System.out.println("  " + title + ": (sin empezar)");
            }
        }
        System.out.println();

        ArrayList<String> badges = student.getAchievements();
        System.out.println("Logros desbloqueados (" + badges.size() + "):");
        if (badges.size() == 0) {
            System.out.println("  Todavía ninguno. ¡Completa una lección para empezar!");
        } else {
            for (int i = 0; i < badges.size(); i++) {
                System.out.println("  " + badges.get(i));
            }
        }
        System.out.println();
    }

    // ----------------------------------------------------------
    // buildProgressBar(correct, total) — text bar like "[###..]".
    // ----------------------------------------------------------
    public static String buildProgressBar(int correct, int total) {
        String bar = "";
        for (int i = 0; i < total; i++) {
            if (i < correct) {
                bar += "#";
            } else {
                bar += ".";
            }
        }
        return bar;
    }
}
