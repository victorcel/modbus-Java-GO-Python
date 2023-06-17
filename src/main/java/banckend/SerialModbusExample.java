package banckend;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class SerialModbusExample {
    public static void main(String[] args) {
        try {

            SerialParameters params = new SerialParameters();
            params.setPortName("/dev/cu.usbserial-14620");
            params.setBaudRate(4800);
            params.setDatabits(8);
            params.setParity("none");
            params.setStopbits(1);

            ModbusSerialMaster modbusMaster = new ModbusSerialMaster(params);
            modbusMaster.connect();

            int slaveId = 2;
            int startAddress = 0;
            int numInputs = 10;
            for (int ix = 0; ix < 50; ix++) {

                Register[] datos = modbusMaster.readMultipleRegisters(slaveId, startAddress, numInputs);
                Thread.sleep(5000);

                for (int i = 0; i <datos.length; i++) {
                    System.out.print(" "+datos[i].toString());
                }
                System.out.println();
            }

            modbusMaster.disconnect(); // Desconectar del puerto serial
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}