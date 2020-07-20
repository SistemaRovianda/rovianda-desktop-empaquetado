package com.rovianda.utility.portserial;

import com.fazecast.jSerialComm.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.xml.bind.*;

public class PortSerial {

    public static void readString(int port, SerialPort [] ports){
        SerialPort comPort = ports[port];
        String message = "";
        try {
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            while (true){

                while (comPort.bytesAvailable() <= 0)
                    Thread.sleep(20);
                if(comPort.bytesAvailable()> 0){

                    byte [] readBuffer = new byte[comPort.bytesAvailable()];
                    int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                    InputStream in = comPort.getInputStream();
                    for (int i=0; i<= numRead ;i++){
                        message += (char)in.read();
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
        int port;
        int option;
        Scanner sc = new Scanner(System.in);
        SerialPort [] ports = SerialPort.getCommPorts();
        String [] result = new String [ports.length];
        for (int i =0; i < ports.length;i++){
            System.out.println("Puerto "+i+": "+ports[i].getDescriptivePortName());
            result[i] = ports[i].getSystemPortName();
        }
        System.out.println("Seleccionar puerto (un numero)");
        port =sc.nextInt();

        System.out.println("Seleccionar un metodo de lectura");
        System.out.println("(1) lectura de string");
        System.out.println("(2) lectura decimal");
        System.out.println("(2) lectura hexadecimal");
        option = sc.nextInt();
        switch (option){
            case 1:
                PortSerial.readString(port,ports);
                break;
            case 2:
                PortSerial.readStringOfHexadecimal(port,ports);
                break;

        }

    }
}
