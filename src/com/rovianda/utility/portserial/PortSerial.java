package com.rovianda.utility.portserial;

import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.io.InputStream;

public class PortSerial {

    private static int port = -1;

    private static SerialPort [] ports;

    private static SerialPort comPort;


    private  static String readString() throws Exception{
       String message = "";
       byte[] readBuffer = new byte[comPort.bytesAvailable()];
       int bytesRead = comPort.readBytes(readBuffer,Math.min(readBuffer.length, comPort.bytesAvailable()));
       message = new String(readBuffer,0, bytesRead);
       System.out.println(message.replaceAll(" ", ""));
       return message.replaceAll(" ", "");
    }

    public static boolean searchPort () throws Exception {
        ports = null;
        ports = SerialPort.getCommPorts();
        for (int i =0; i < ports.length;i++){
            System.out.println(ports[i].getDescriptivePortName());
            if(ports[i].getDescriptivePortName().indexOf("VSPE_SERIAL1")!=-1){
               port= i;
               comPort = ports[i];
            }
        }
        if(port <0){
            throw  new Exception("Revisar la conexion de la bascula");
        }
        return true;
    }

    public static void openPort (){
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    }

    public static void  closePort(){
        comPort.closePort();
    }


    public static double getWeight() throws Exception {
        String weight = PortSerial.readString().split("KG")[0];
        return Double.parseDouble(weight.replaceAll("KG",""));
    }
}
