package com.rovianda.utility.portserial;

import com.fazecast.jSerialComm.*;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import javax.xml.bind.*;

public class PortSerial {

    public static String readString(int port, SerialPort [] ports){
        SerialPort comPort = ports[port];
        String message = "";
        String lastTaked="";
        int intents=0;
        try {
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            while (true) {

                while (comPort.bytesAvailable() <= 0)
                    Thread.sleep(20);
                if (comPort.bytesAvailable() > 0) {

                    byte[] readBuffer = new byte[comPort.bytesAvailable()];
                    int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                    InputStream in = comPort.getInputStream();
                    for (int i = 0; i <= numRead; i++) {
                        message += (char) in.read();
                    }
                    if (message.indexOf("KG") != -1)
                    {
                        int indexSpace = message.lastIndexOf(" ");
                        if(indexSpace!=-1){
                        int lengthMessage = message.length();
                        String weigth = message.substring(indexSpace,lengthMessage);
                        weigth = weigth.trim();

                        if(weigth.indexOf("KG")!=-1) {
                            if(!lastTaked.equals(weigth)){
                                lastTaked=weigth;
                                System.out.println("Peso: " + weigth);
                                intents=0;
                            }else{
                                intents++;
                                if(intents==10) {
                                    comPort.closePort();
                                    return lastTaked;
                                }else{
                                    System.out.println("Peso calculado: " + lastTaked);
                                }
                            }
                        }
                        }
                    }
                    message = "";
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            comPort.closePort();
            return "error";
        }

    }

    public static void readStringOfHexadecimal(int port, SerialPort [] ports){
        SerialPort comPort = ports[port];
        String message = "";
        try {
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            while (true){

                while (comPort.bytesAvailable() <= 0)
                    Thread.sleep(5);
                if(comPort.bytesAvailable()> 0){

                    byte [] readBuffer = new byte[comPort.bytesAvailable()];
                    int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                    InputStream in = comPort.getInputStream();
                    for (int i=0; i< numRead ;i++){
                        System.out.println(in.read());
                        message +=(char) in.read();
                    }
                    System.out.println(message);
                    message = "";
                    System.out.println("read: "+numRead);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        comPort.closePort();
    }

    public static void main(String[] args) throws IOException {
        int port=0;
        //int option;
        //Scanner sc = new Scanner(System.in);
        SerialPort [] ports = SerialPort.getCommPorts();
        /*String [] result = new String [ports.length];*/
        for (int i =0; i < ports.length;i++){
            /*System.out.println("Puerto "+i+": "+ports[i].getDescriptivePortName());
            result[i] = ports[i].getSystemPortName();*/
            if(ports[i].getDescriptivePortName().indexOf("Prolific USB-to-Serial Comm Port")!=-1){
                port=i;
            }
        }
        //System.out.println("Seleccionar puerto (un numero)");
        //port =sc.nextInt();

        /*System.out.println("Seleccionar un metodo de lectura");
        System.out.println("(1) lectura de string");
        System.out.println("(2) lectura decimal");
<<<<<<< HEAD
        option = sc.nextInt();
        switch (option){
=======
        System.out.println("(2) lectura hexadecimal");*/
        //option = 1;
        String peso = PortSerial.readString(port,ports);
        System.out.println("El peso completo es: "+peso);
        /*switch (option){
>>>>>>> 19dba798fc7f0aa33cb713318194a010c509f33a
            case 1:
                PortSerial.readString(port,ports);
                break;
            case 2:
                PortSerial.readStringOfHexadecimal(port,ports);
                break;

        }*/

    }
}
