package com.example;

import com.example.paquete.Paquete;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloController implements Runnable{
    public Paquete paquete;//objeto de la clase paquete
    public int llave=0;

    @FXML
    private VBox VbACR;

    public void initialize(){
        Thread hilo1 = new Thread(this);
        hilo1.start();
    }

    public void run(){
        try{
            ServerSocket servidor = new ServerSocket(5001); // puerto en donde escucha mi asociación
            while(true){
                Socket misocket = servidor.accept(); //aceptamos todas las conecciones del exterior, abrimos mi socket
                ObjectInputStream paqueteEntreda = new ObjectInputStream(misocket.getInputStream());
                Paquete paqueteRecibido = (Paquete) paqueteEntreda.readObject();


                System.out.println(paqueteRecibido.getMensaje());
                Platform.runLater(() -> {
                    //txt_recibido.setText(mensaje);
                    VbACR.getChildren().add(new Label("Se recibio solicitud para el certificado: " +paqueteRecibido.getNumeroDeCertificado()+ "\n"));
                });


                if (paqueteRecibido.getCodigoDeOperación() == 'C') {
                    System.out.println("Recibo por parte del cliente");
                    String NumCertificado = paqueteRecibido.getNumeroDeCertificado() + "";
                    String rutaCertificadoEmisor = "D:\\IJ\\proyectos\\certificadosAR1\\cerificado" + NumCertificado + ".cer";
                    File file = new File(rutaCertificadoEmisor);
                    FileReader fr = null;
                    try {
                        fr = new FileReader(file);
                        buscarCertificado(fr);
                        //mando mensaje

                        //genero paquete de salida
                        Socket Socketcliente = new Socket("127.0.0.1", 6001);
                        ObjectOutputStream paqueteSalida =  new ObjectOutputStream(Socketcliente.getOutputStream());
                        paqueteRecibido.setLlavePuclica(llave);
                        paqueteSalida.writeObject(paqueteRecibido);
                        System.out.println("Se mando la llave "+ paqueteRecibido.getLlavePuclica());
                        paqueteSalida.close();
                        Socketcliente.close();
                    } catch (FileNotFoundException e) {
                        //no se encuentra el certificado
                        Platform.runLater(() -> {
                            //txt_recibido.setText(mensaje);
                            VbACR.getChildren().add(new Label("No se encontro el certificado : " + paqueteRecibido.getNumeroDeCertificado() + " en la base de datos de AR1" + "\n"
                                    + "Se mando solicitud de busqueda del certifidado a AR2 para el certificado " + paqueteRecibido.getNumeroDeCertificado()));
                        });
                        if (paqueteRecibido.getCodigoDeOperación() == 'C') {//mando solicitud a mi otra AR 2
                            paqueteRecibido.setCodigoDeOperación('A');
                            //genero paquete de salida
                            //Paquete mandoLLave = new Paquete(paqueteRecibido.getMensaje(), paqueteRecibido.getPuertoE(), paqueteRecibido.getpPuertoR(), paqueteRecibido.getNumeroDeCertificadoR());
                            Socket Socketcliente = new Socket("127.0.0.1", 5002);
                            ObjectOutputStream paqueteSalida = new ObjectOutputStream(Socketcliente.getOutputStream());
                            paqueteSalida.writeObject(paqueteRecibido);
                            paqueteSalida.close();
                            Socketcliente.close();
                        }

                    }
                }

                else if (paqueteRecibido.getCodigoDeOperación()=='A'){

                    String NumCertificado = paqueteRecibido.getNumeroDeCertificado() + "";
                    String rutaCertificadoEmisor = "D:\\IJ\\proyectos\\certificadosAR1\\cerificado" + NumCertificado + ".cer";
                    File file = new File(rutaCertificadoEmisor);
                    FileReader fr = null;
                    try {
                        fr = new FileReader(file);
                        buscarCertificado(fr);
                        //mando mensaje

                        //mando mensaje
                        //genero paquete de salida
                        Socket Socketcliente = new Socket("127.0.0.1", 5002);
                        ObjectOutputStream paqueteSalida =  new ObjectOutputStream(Socketcliente.getOutputStream());
                        System.out.println("estoy mandando la llave");
                        paqueteRecibido.setLlavePuclica(llave);
                        paqueteRecibido.setCodigoDeOperación('O');
                        paqueteSalida.writeObject(paqueteRecibido);
                        System.out.println("Se mando la llave "+ paqueteRecibido.getLlavePuclica());
                        paqueteSalida.close();
                    } catch (FileNotFoundException e) {
                    }
                }
               else if (paqueteRecibido.getCodigoDeOperación()=='O'){
                    Socket Socketcliente = new Socket("127.0.0.1", 6001);
                    ObjectOutputStream paqueteSalida =  new ObjectOutputStream(Socketcliente.getOutputStream());
                    paqueteRecibido.setLlavePuclica(paqueteRecibido.getLlavePuclica());
                    paqueteSalida.writeObject(paqueteRecibido);
                    System.out.println("Se mando la llave "+ paqueteRecibido.getLlavePuclica());
                    paqueteSalida.close();
                    Socketcliente.close();
                }

            }

        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void buscarCertificado(FileReader fr){
        BufferedReader br = new BufferedReader(fr);   // creates a buffering character input stream
        String line;
        int lineCounter = 0;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (lineCounter == 1) {
                llave = Integer.parseInt(line);
                //paqueteRecibido.setLlavePuclica(Integer.parseInt(line)); //guardo la llave pública oara verificar la firma
                System.out.println("llave del certificado: "+llave);
            }
            lineCounter++;
        }
        try {
            fr.close(); // closes the stream and release the resources
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}