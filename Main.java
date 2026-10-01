package Juego_21_palitos_completo;

import java.util.*;

public class Main {

    static int n_palos = 21;
    static Scanner teclado = new Scanner(System.in);
    static String ficha = "|";
    // 1 para 2 jugadores , 2 para vs ia
    static int opcion_modo_juego = 1; 
    static int ultima_quitan = 1;

    public static void pintar_palos() {
        for (int i = 1; i <= n_palos; i++) {
            System.out.print(" " + ficha + " ");
        }
        System.out.println();
    }

    public static void ver_ayuda() {
        System.out.println("\nAYUDA");
        System.out.println("---------------------------------------");
        System.out.println("empezamos con 21 palos");
        System.out.println("cada uno quita entre 1 y 4 por turno");
        System.out.println("quien se queda el ultimo palo pierde");
        System.out.println("controles en la partida:");
        System.out.println("  r -> reiniciar");
        System.out.println("  m -> menu principal");
        System.out.println("-----------------------------------\n");
    }

    public static void cambiar_palo() {
        System.out.print("\npon el nuevo caracter para los palos: ");
        String elegida = teclado.next();

        // si la letra no esta vacia cogemos solo la primera letra por si es mas largo
        if (elegida.length() > 0) {
            // recortamos la palabra desde la posicion 0 a 1
            ficha = elegida.substring(0, 1);
            System.out.println("palo cambiado a " + ficha);
        }
    }

    public static void pedir_palos() {
        n_palos = 21;
        int turno_actual = 1;
        boolean seguimos = true;

        System.out.println("\nempieza el juego: ");
        pintar_palos();

        while (n_palos > 0 && seguimos == true) {

            if (opcion_modo_juego == 1) {
                System.out.println("\nturno jugador " + turno_actual);
            } else {
                if (turno_actual == 1) {
                    System.out.println("\nturno jugador");
                } else {
                    System.out.println("\nturno ia");
                }
            }

            // turno de la ia
            if (opcion_modo_juego == 2 && turno_actual == 2) {

                // truco del numero magico 5
                int tirada_maquina = 5 - ultima_quitan;

                // si quedan pocos palos ajustamos la tirada para no restar de mas
                if (n_palos - tirada_maquina < 1) {
                    tirada_maquina = n_palos - 1;
                    if (tirada_maquina <= 0) {
                        tirada_maquina = 1;
                    }
                }

                System.out.println("la ia ha tachado " + tirada_maquina + " palos");
                n_palos = n_palos - tirada_maquina;
                pintar_palos();

                // comprobamos si gana o pierde
                if (n_palos == 0) {
                    System.out.println("\nla ia ha quitado el ultimo palo, has ganado!!!");
                    seguimos = false;

                } else if (n_palos == 1) {
                    decidir_ganador();
                    seguimos = false;

                } else {
                    // le devolvemos el turno al jugador
                    turno_actual = 1;
                }

            } else {
                // turno persona
                System.out.print("cuantos palos quieres tachar (1-4), r reiniciar o m menu: ");
                String valor_teclado = teclado.next();

                // si es m sale al menu
                if (valor_teclado.equalsIgnoreCase("m")) {
                    System.out.println("volviendo al menu...");
                    seguimos = false;

                // si es r se reinicia
                } else if (valor_teclado.equalsIgnoreCase("r")) {
                    System.out.println("reiniciando...");
                    n_palos = 21;
                    turno_actual = 1;
                    pintar_palos();

                } else {
                    // comprobamos si mete 1, 2, 3 o 4
                    if (valor_teclado.equals("1") || valor_teclado.equals("2") || valor_teclado.equals("3") || valor_teclado.equals("4")) {
                        
                        int tirada_jugador = Integer.parseInt(valor_teclado);

                        // no se pueden quitar mas palos de los que quedan
                        if (tirada_jugador > n_palos) {
                            System.out.println("no puedes quitar mas de los que quedan (" + n_palos + ")");

                        } else {
                            ultima_quitan = tirada_jugador;
                            n_palos = n_palos - tirada_jugador;
                            System.out.println("has tachado " + tirada_jugador + " palos");
                            pintar_palos();

                            // si quedan 0 palos pierdes 
                            if (n_palos == 0) {
                                System.out.println("\nhas quitado el ultimo palo, has perdido");
                                seguimos = false;

                            // si queda 1 palo ganas, porque al rival le queda 1
                            } else if (n_palos == 1) {
                                if (opcion_modo_juego == 1) {
                                    int gana = 1;

                                    if (turno_actual == 1) {
                                        gana = 2;
                                    } else {
                                        gana = 1;
                                    }
                                    System.out.println("\nqueda 1 palo, gana el jugador " + gana);

                                } else {
                                    System.out.println("\nqueda 1 palo y le toca a la ia, has ganado");
                                }

                                seguimos = false;

                            } else {
                                // cambiamos el turno entre el jugador 1 y 2
                                if (turno_actual == 1) {
                                    turno_actual = 2;
                                } else {
                                    turno_actual = 1;
                                }
                            }
                        }

                    } else {
                        System.out.println("solo puedes entre 1 y 4 palos, o poner r / m");
                    }
                }
            }
        }

        if (seguimos == false && n_palos <= 1 && n_palos > 0) {
            System.out.print("\nquieres jugar de nuevo? (r para reiniciar, m para menu): ");
            String fin = teclado.next();
            if (fin.equalsIgnoreCase("r")) {
                pedir_palos();
            }
        }
    }


    public static void sub_menu() {
        int opcion = 0;
        do {
            System.out.println("\nSELECCIONAR MODO: ");
            System.out.println("1. jugador vs jugador");
            System.out.println("2. jugador vs ia");
            System.out.println("3. cambiar ficha (actual: " + ficha + ")");
            System.out.println("4. atras");
            System.out.print("elige: ");

            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    opcion_modo_juego = 1;
                    pedir_palos();
                    break;
                case 2:
                    opcion_modo_juego = 2;
                    pedir_palos();
                    break;
                case 3:
                    cambiar_palo();
                    break;
                case 4:
                    System.out.println("atras...");
                    break;
                default:
                    System.out.println("opcion no valida");
            }

        } while (opcion != 4);

    }

    public static void decidir_ganador() {
        System.out.println("\nhas perdido, queda 1 palo y te toca");
    }


    public static void main(String[] args) {

        int opcion = 0;

        do {
            System.out.println("\nJUEGO DE LOS 21 PALITOS");
            System.out.println("-----------------------------");
            System.out.println("1. jugar");
            System.out.println("2. ayuda y controles");
            System.out.println("3. salir");
            System.out.print("opcion: ");

            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    sub_menu();
                    break;
                case 2:
                    ver_ayuda();
                    break;
                case 3:
                    System.out.println("saliendo del jeugo...");
                    break;
                default:
                    System.out.println("opcion no valida");
            }

        } while (opcion != 3);

    }
}