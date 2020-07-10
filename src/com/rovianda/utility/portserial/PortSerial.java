package com.rovianda.utility.portserial;

import com.fazecast.jSerialComm.*;

public class PortSerial {
    public static void main(String[] args) {
        byte[] buffer = new byte[256];
        SerialPort [] ports = SerialPort.getCommPorts();
        System.out.println(ports.length);
        String [] result = new String [ports.length];
        for (int i =0; i < ports.length;i++){
            result[i] = ports[i].getSystemPortName();
            ports[i].readBytes(buffer, buffer.length);
        }

        for(String name : result)
            System.out.println(name);

    }
}
